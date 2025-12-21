package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyAssessmentType;
import model.MyAssessmentTypeFacade;
import model.MyModule;
import model.MyModuleFacade;
import model.MyUsers;

@WebServlet(name = "Assessment", urlPatterns = {"/Assessment"})
public class Assessment extends HttpServlet {

    @EJB
    private MyAssessmentTypeFacade myAssessmentTypeFacade;

    @EJB
    private MyModuleFacade myModuleFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== LOGIN VALIDATION =====
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ===== LECTURER ONLY VALIDATION =====
        MyUsers loginUser = (MyUsers) session.getAttribute("user");
        if (!isLecturer(loginUser)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            String action = request.getParameter("action");
            if (action == null || action.trim().isEmpty()) {
                action = "list";
            }

            // moduleID is REQUIRED for assessment page
            String moduleIDStr = request.getParameter("moduleID");
            if (moduleIDStr == null || moduleIDStr.trim().isEmpty()) {
                response.sendRedirect("Lmodule.jsp"); // or lecturer dashboard
                return;
            }

            Integer moduleID = Integer.parseInt(moduleIDStr);

            // ===== SECURITY: module must belong to this lecturer =====
            MyModule moduleRow = myModuleFacade.find(moduleID);
            if (moduleRow == null) {
                response.sendRedirect("Lmodule.jsp");
                return;
            }

            System.out.println("DEBUG action=" + action + ", moduleIDStr=[" + moduleIDStr + "]");
            System.out.println("DEBUG loginLecturer=[" + loginUser.getUserID() + "]");
            System.out.println("DEBUG dbAssigned=[" + moduleRow.getAssignedLecturerID() + "]");

            // must be assigned to the logged-in lecturer
            String lecturerID = loginUser.getUserID();
            if (moduleRow.getAssignedLecturerID() == null
                    || !moduleRow.getAssignedLecturerID().equals(lecturerID)) {
                response.sendRedirect("Lmodule.jsp");
                return;
            }

            // ===== LIST JSON (AJAX REAL-TIME) =====
            if ("listJson".equals(action)) {

                List<MyAssessmentType> list = myAssessmentTypeFacade.findByModule(moduleID);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < list.size(); i++) {
                    MyAssessmentType a = list.get(i);

                    json.append("{")
                            .append("\"assessmentID\":").append(a.getAssessmentID()).append(",")
                            .append("\"assessmentName\":\"").append(escape(a.getAssessmentName())).append("\",")
                            .append("\"weightage\":").append(a.getWeightage() == null ? 0 : a.getWeightage()).append(",")
                            .append("\"createdBy\":\"").append(escape(a.getCreatedBy())).append("\"")
                            .append("}");

                    if (i < list.size() - 1) {
                        json.append(",");
                    }
                }
                json.append("]");

                response.getWriter().write(json.toString());
                return;
            }

            // ===== LIST =====
            if ("list".equals(action)) {

                List<MyAssessmentType> list = myAssessmentTypeFacade.findByModule(moduleID);

                request.setAttribute("moduleRow", moduleRow);
                request.setAttribute("moduleID", moduleID);
                request.setAttribute("assessmentList", list);

                // show total weightage info (useful UI)
                int total = myAssessmentTypeFacade.sumWeightageByModule(moduleID);
                request.setAttribute("totalWeightage", total);

                request.getRequestDispatcher("assessment.jsp").forward(request, response);
                return;
            }

            // ===== SEARCH =====
            if ("search".equals(action)) {

                String keyword = request.getParameter("keyword");
                if (keyword == null || keyword.trim().isEmpty()) {
                    response.sendRedirect("Assessment?action=list&moduleID=" + moduleID);
                    return;
                }

                keyword = keyword.trim().toLowerCase();

                // simple filter (no new NamedQuery needed)
                List<MyAssessmentType> all = myAssessmentTypeFacade.findByModule(moduleID);
                List<MyAssessmentType> filtered = new ArrayList<>();
                for (MyAssessmentType a : all) {
                    String name = (a.getAssessmentName() == null) ? "" : a.getAssessmentName().toLowerCase();
                    if (name.contains(keyword)) {
                        filtered.add(a);
                    }
                }

                if (filtered.isEmpty()) {
                    request.setAttribute("errorMsg", "No assessments found for: " + keyword);
                }

                request.setAttribute("moduleRow", moduleRow);
                request.setAttribute("moduleID", moduleID);
                request.setAttribute("assessmentList", filtered);

                int total = myAssessmentTypeFacade.sumWeightageByModule(moduleID);
                request.setAttribute("totalWeightage", total);

                request.getRequestDispatcher("assessment.jsp").forward(request, response);
                return;
            }

            // ===== GO ADD PAGE =====
            if ("goAdd".equals(action)) {

                request.setAttribute("moduleRow", moduleRow);
                request.setAttribute("moduleID", moduleID);

                // creator display
                request.setAttribute("createdByName", loginUser.getFullName());
                request.setAttribute("createdByID", loginUser.getUserID());

                request.getRequestDispatcher("addAssessment.jsp").forward(request, response);
                return;
            }

            // ===== ADD =====
            if ("add".equals(action)) {

                String assessmentName = request.getParameter("assessmentName");
                String weightageStr = request.getParameter("weightage");

                assessmentName = (assessmentName == null) ? "" : assessmentName.trim();
                weightageStr = (weightageStr == null) ? "" : weightageStr.trim();

                Map<String, String> errors = new HashMap<>();

                // name
                if (assessmentName.isEmpty()) {
                    errors.put("assessmentName", "Assessment Name cannot be empty.");
                } else if (assessmentName.length() < 2) {
                    errors.put("assessmentName", "Assessment Name must be at least 2 characters.");
                } else if (assessmentName.length() > 50) {
                    errors.put("assessmentName", "Assessment Name must not exceed 50 characters.");
                } else if (myAssessmentTypeFacade.existsNameInModule(moduleID, assessmentName)) {
                    errors.put("assessmentName", "Assessment Name already exists for this module.");
                }

                // weightage
                Integer weightage = null;
                if (weightageStr.isEmpty()) {
                    errors.put("weightage", "Weightage cannot be empty.");
                } else {
                    try {
                        weightage = Integer.parseInt(weightageStr);
                        if (weightage <= 0) {
                            errors.put("weightage", "Weightage must be more than 0.");
                        } else if (weightage > 100) {
                            errors.put("weightage", "Weightage must not exceed 100.");
                        }
                    } catch (Exception ex) {
                        errors.put("weightage", "Weightage must be a valid number.");
                    }
                }

                // weightage total rule (<= 100)
                if (weightage != null) {
                    int currentTotal = myAssessmentTypeFacade.sumWeightageByModule(moduleID);
                    if (currentTotal + weightage > 100) {
                        errors.put("weightage", "Total weightage cannot exceed 100. Current total: "
                                + currentTotal + ".");
                    }
                }

                if (!errors.isEmpty()) {

                    request.setAttribute("errors", errors);

                    request.setAttribute("assessmentNameVal", assessmentName);
                    request.setAttribute("weightageVal", weightageStr);

                    request.setAttribute("moduleRow", moduleRow);
                    request.setAttribute("moduleID", moduleID);
                    request.setAttribute("createdByName", loginUser.getFullName());
                    request.setAttribute("createdByID", loginUser.getUserID());

                    request.getRequestDispatcher("addAssessment.jsp").forward(request, response);
                    return;
                }

                MyAssessmentType a = new MyAssessmentType();
                a.setModuleID(moduleID);
                a.setAssessmentName(assessmentName);
                a.setWeightage(weightage);
                a.setCreatedBy(loginUser.getUserID());

                myAssessmentTypeFacade.create(a);

                response.sendRedirect("Assessment?action=list&moduleID=" + moduleID);
                return;
            }

            // ===== GO EDIT PAGE =====
            if ("edit".equals(action)) {

                String assessmentIDStr = request.getParameter("assessmentID");
                if (assessmentIDStr == null || assessmentIDStr.trim().isEmpty()) {
                    response.sendRedirect("Assessment?action=list&moduleID=" + moduleID);
                    return;
                }

                Integer assessmentID = Integer.parseInt(assessmentIDStr);
                MyAssessmentType a = myAssessmentTypeFacade.find(assessmentID);

                if (a == null || a.getModuleID() == null || !a.getModuleID().equals(moduleID)) {
                    response.sendRedirect("Assessment?action=list&moduleID=" + moduleID);
                    return;
                }

                request.setAttribute("moduleRow", moduleRow);
                request.setAttribute("moduleID", moduleID);

                request.setAttribute("assessmentIDVal", a.getAssessmentID());
                request.setAttribute("assessmentNameVal", a.getAssessmentName());
                request.setAttribute("weightageVal", a.getWeightage());

                request.getRequestDispatcher("updateassessment.jsp").forward(request, response);
                return;
            }

            // ===== UPDATE =====
            if ("update".equals(action)) {

                String assessmentIDStr = request.getParameter("assessmentID");
                if (assessmentIDStr == null || assessmentIDStr.trim().isEmpty()) {
                    response.sendRedirect("Assessment?action=list&moduleID=" + moduleID);
                    return;
                }

                Integer assessmentID = Integer.parseInt(assessmentIDStr);
                MyAssessmentType a = myAssessmentTypeFacade.find(assessmentID);

                if (a == null || a.getModuleID() == null || !a.getModuleID().equals(moduleID)) {
                    response.sendRedirect("Assessment?action=list&moduleID=" + moduleID);
                    return;
                }

                String assessmentName = request.getParameter("assessmentName");
                String weightageStr = request.getParameter("weightage");

                assessmentName = (assessmentName == null) ? "" : assessmentName.trim();
                weightageStr = (weightageStr == null) ? "" : weightageStr.trim();

                Map<String, String> errors = new HashMap<>();

                // name
                if (assessmentName.isEmpty()) {
                    errors.put("assessmentName", "Assessment Name cannot be empty.");
                } else if (assessmentName.length() < 2) {
                    errors.put("assessmentName", "Assessment Name must be at least 2 characters.");
                } else if (assessmentName.length() > 50) {
                    errors.put("assessmentName", "Assessment Name must not exceed 50 characters.");
                } else {
                    // only block duplicate if name changed
                    String oldName = (a.getAssessmentName() == null) ? "" : a.getAssessmentName().trim();
                    if (!oldName.equalsIgnoreCase(assessmentName)
                            && myAssessmentTypeFacade.existsNameInModule(moduleID, assessmentName)) {
                        errors.put("assessmentName", "Assessment Name already exists for this module.");
                    }
                }

                // weightage
                Integer weightage = null;
                if (weightageStr.isEmpty()) {
                    errors.put("weightage", "Weightage cannot be empty.");
                } else {
                    try {
                        weightage = Integer.parseInt(weightageStr);
                        if (weightage <= 0) {
                            errors.put("weightage", "Weightage must be more than 0.");
                        } else if (weightage > 100) {
                            errors.put("weightage", "Weightage must not exceed 100.");
                        }
                    } catch (Exception ex) {
                        errors.put("weightage", "Weightage must be a valid number.");
                    }
                }

                // total rule (exclude this row)
                if (weightage != null) {
                    int totalExcept = myAssessmentTypeFacade.sumWeightageByModuleExcept(moduleID, assessmentID);
                    if (totalExcept + weightage > 100) {
                        errors.put("weightage", "Total weightage cannot exceed 100. Current total (excluding this): "
                                + totalExcept + ".");
                    }
                }

                if (!errors.isEmpty()) {

                    request.setAttribute("errors", errors);

                    request.setAttribute("moduleRow", moduleRow);
                    request.setAttribute("moduleID", moduleID);

                    request.setAttribute("assessmentIDVal", assessmentID);
                    request.setAttribute("assessmentNameVal", assessmentName);
                    request.setAttribute("weightageVal", weightageStr);

                    request.getRequestDispatcher("updateassessment.jsp").forward(request, response);
                    return;
                }

                a.setAssessmentName(assessmentName);
                a.setWeightage(weightage);

                myAssessmentTypeFacade.edit(a);

                response.sendRedirect("Assessment?action=list&moduleID=" + moduleID);
                return;
            }

            // ===== DELETE =====
            if ("delete".equals(action)) {

                String assessmentIDStr = request.getParameter("assessmentID");
                if (assessmentIDStr == null || assessmentIDStr.trim().isEmpty()) {
                    response.sendRedirect("Assessment?action=list&moduleID=" + moduleID);
                    return;
                }

                Integer assessmentID = Integer.parseInt(assessmentIDStr);
                MyAssessmentType a = myAssessmentTypeFacade.find(assessmentID);

                if (a != null && a.getModuleID() != null && a.getModuleID().equals(moduleID)) {
                    myAssessmentTypeFacade.remove(a);
                }

                response.sendRedirect("Assessment?action=list&moduleID=" + moduleID);
                return;
            }

            // fallback
            response.sendRedirect("Assessment?action=list&moduleID=" + moduleID);

        } catch (Exception e) {
            request.setAttribute("assessmentList", java.util.Collections.emptyList());
            request.setAttribute("errorMsg", "No assessments have been created for this module yet.");
            request.getRequestDispatcher("assessment.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private boolean isLecturer(MyUsers user) {
        return user != null
                && user.getUserID() != null
                && user.getUserID().toUpperCase().startsWith("L");
    }

    private String escape(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "")
                .replace("\r", "");
    }

    @Override
    public String getServletInfo() {
        return "Assessment management servlet";
    }
}
