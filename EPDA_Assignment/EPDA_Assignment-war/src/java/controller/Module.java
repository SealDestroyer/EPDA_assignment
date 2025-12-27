package controller;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.escape;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyModule;
import model.MyModuleFacade;
import model.MyUsers;
import model.MyUsersFacade;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "Module", urlPatterns = {"/Module"})
public class Module extends HttpServlet {

    @EJB
    private MyUsersFacade myUsersFacade;

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

        // ===== AL ONLY VALIDATION =====
        MyUsers loginUser = (MyUsers) session.getAttribute("user");
        if (!isAcademicLeader(loginUser)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            String action = request.getParameter("action");
            if (action == null || action.trim().isEmpty()) {
                action = "list";
            }

            // ===== LIST JSON (AJAX REAL-TIME) =====
            if ("listJson".equals(action)) {

                String alID = loginUser.getUserID();
                List<MyModule> moduleList = myModuleFacade.findByCreatedBy(alID);

                // created by with full name
                Set<String> ids = new HashSet<>();
                for (MyModule m : moduleList) {
                    if (m.getCreatedBy() != null && !m.getCreatedBy().trim().isEmpty()) {
                        ids.add(m.getCreatedBy().trim());
                    }
                    if (m.getAssignedLecturerID() != null && !m.getAssignedLecturerID().trim().isEmpty()) {
                        ids.add(m.getAssignedLecturerID().trim());
                    }
                }
                Map<String, String> userNameMap
                        = myUsersFacade.findUserNameMapByIds(new ArrayList<>(ids));

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < moduleList.size(); i++) {
                    MyModule m = moduleList.get(i);

                    json.append("{")
                            .append("\"moduleID\":").append(m.getModuleID()).append(",")
                            .append("\"moduleName\":\"").append(escape(m.getModuleName())).append("\",")
                            .append("\"moduleCode\":\"").append(escape(m.getModuleCode())).append("\",")
                            .append("\"description\":\"").append(escape(m.getDescription())).append("\",")
                            .append("\"createdBy\":\"")
                            .append(escape(userNameMap.get(m.getCreatedBy())))
                            .append(" (").append(m.getCreatedBy()).append(")\",")
                            .append("\"lecturer\":\"")
                            .append(escape(userNameMap.get(m.getAssignedLecturerID())))
                            .append(" (").append(m.getAssignedLecturerID()).append(")\"")
                            .append("}");

                    if (i < moduleList.size() - 1) {
                        json.append(",");
                    }
                }
                json.append("]");

                response.getWriter().write(json.toString());
                return;
            }

            // ===== LIST =====
            if ("list".equals(action)) {

                String alID = loginUser.getUserID();
                List<MyModule> moduleList = myModuleFacade.findByCreatedBy(alID);

                // created by with full name
                Set<String> ids = new HashSet<>();
                for (MyModule m : moduleList) {
                    if (m.getCreatedBy() != null && !m.getCreatedBy().trim().isEmpty()) {
                        ids.add(m.getCreatedBy().trim());
                    }
                    if (m.getAssignedLecturerID() != null && !m.getAssignedLecturerID().trim().isEmpty()) {
                        ids.add(m.getAssignedLecturerID().trim());
                    }
                }
                Map<String, String> userNameMap = myUsersFacade.findUserNameMapByIds(new ArrayList<>(ids));
                request.setAttribute("userNameMap", userNameMap);

                request.setAttribute("moduleList", moduleList);
                request.getRequestDispatcher("module.jsp").forward(request, response);
                return;
            }

            // ===== SEARCH =====
            if ("search".equals(action)) {

                String keyword = request.getParameter("keyword");

                // if empty keyword then show all modules
                if (keyword == null || keyword.trim().isEmpty()) {
                    response.sendRedirect("Module?action=list");
                    return;
                }

                String alID = loginUser.getUserID();
                List<MyModule> moduleList = myModuleFacade.searchModulesByCreatedBy(keyword, alID);

                if (moduleList.isEmpty()) {
                    request.setAttribute("errorMsg", "No modules found for: " + keyword);
                }

                // user ID with full name
                Set<String> ids = new HashSet<>();
                for (MyModule m : moduleList) {
                    if (m.getCreatedBy() != null && !m.getCreatedBy().trim().isEmpty()) {
                        ids.add(m.getCreatedBy().trim());
                    }
                    if (m.getAssignedLecturerID() != null && !m.getAssignedLecturerID().trim().isEmpty()) {
                        ids.add(m.getAssignedLecturerID().trim());
                    }
                }
                Map<String, String> userNameMap = myUsersFacade.findUserNameMapByIds(new ArrayList<>(ids));
                request.setAttribute("userNameMap", userNameMap);

                request.setAttribute("moduleList", moduleList);
                request.getRequestDispatcher("module.jsp").forward(request, response);
                return;

            }

            // ===== GO ADD PAGE =====
            if ("goAdd".equals(action)) {

                // show full name 
                request.setAttribute("createdByName", loginUser.getFullName());
                request.setAttribute("createdByID", loginUser.getUserID()); // optional if needed

                // lecturer dropdown list
                List<MyUsers> lecturerList = myUsersFacade.findLecturers();
                request.setAttribute("lecturerList", lecturerList);

                request.getRequestDispatcher("addmodule.jsp").forward(request, response);
                return;
            }

            // ===== ADD MODULE =====
            if ("add".equals(action)) {

                String createdByID = loginUser.getUserID();

                String moduleName = request.getParameter("moduleName");
                String moduleCode = request.getParameter("moduleCode");
                String description = request.getParameter("description");
                String assignedLecturerID = request.getParameter("assignedLecturerID");

                moduleName = (moduleName == null) ? "" : moduleName.trim();
                moduleCode = (moduleCode == null) ? "" : moduleCode.trim();
                description = (description == null) ? "" : description.trim();
                assignedLecturerID = (assignedLecturerID == null) ? "" : assignedLecturerID.trim();

                Map<String, String> errors = new HashMap<>();

                // Module Name
                if (moduleName.isEmpty()) {
                    errors.put("moduleName", "Module Name cannot be empty.");
                } else if (moduleName.length() < 2) {
                    errors.put("moduleName", "Module Name must be at least 2 characters.");
                } else if (moduleName.length() > 50) {
                    errors.put("moduleName", "Module Name must not exceed 50 characters.");
                }

                // Module Code
                if (moduleCode.isEmpty()) {
                    errors.put("moduleCode", "Module Code cannot be empty.");
                } else if (moduleCode.length() < 2) {
                    errors.put("moduleCode", "Module Code must be at least 2 characters.");
                } else if (moduleCode.length() > 10) {
                    errors.put("moduleCode", "Module Code must not exceed 10 characters.");
                } else if (myModuleFacade.existsModuleCode(moduleCode)) {
                    errors.put("moduleCode", "Module Code already exists.");
                }

                // Description
                if (description.isEmpty()) {
                    errors.put("description", "Description cannot be empty.");
                } else if (description.length() > 100) {
                    errors.put("description", "Description must not exceed 100 characters.");
                }

                // Lecturer
                if (assignedLecturerID.isEmpty()) {
                    errors.put("assignedLecturerID", "Please select a lecturer.");
                }

                // if got errors then return back to addmodule.jsp
                if (!errors.isEmpty()) {

                    request.setAttribute("errors", errors);

                    // keep old values
                    request.setAttribute("moduleNameVal", moduleName);
                    request.setAttribute("moduleCodeVal", moduleCode);
                    request.setAttribute("descriptionVal", description);
                    request.setAttribute("assignedLecturerIDVal", assignedLecturerID);

                    // reload page data
                    request.setAttribute("createdByName", loginUser.getFullName());
                    request.setAttribute("createdByID", createdByID);

                    List<MyUsers> lecturerList = myUsersFacade.findLecturers();
                    request.setAttribute("lecturerList", lecturerList);

                    request.getRequestDispatcher("addmodule.jsp").forward(request, response);
                    return;
                }

                // PASS then create module
                MyModule m = new MyModule();
                m.setModuleName(moduleName);
                m.setModuleCode(moduleCode);
                m.setDescription(description);
                m.setCreatedBy(createdByID);
                m.setAssignedLecturerID(assignedLecturerID);

                myModuleFacade.create(m);
                response.sendRedirect("Module?action=list");
                return;
            }

            // ===== GO UPDATE PAGE (EDIT) =====
            if ("edit".equals(action)) {

                String moduleIDStr = request.getParameter("moduleID");
                if (moduleIDStr == null || moduleIDStr.trim().isEmpty()) {
                    response.sendRedirect("Module?action=list");
                    return;
                }

                Integer moduleID = Integer.parseInt(moduleIDStr);

                // get module row
                MyModule m = myModuleFacade.find(moduleID);
                if (m == null) {
                    response.sendRedirect("Module?action=list");
                    return;
                }
                // AL can only edit own modules
                if (!loginUser.getUserID().equals(m.getCreatedBy())) {
                    response.sendRedirect("Module?action=list");
                    return;
                }

                // lecturer dropdown list
                List<MyUsers> lecturerList = myUsersFacade.findLecturers();
                request.setAttribute("lecturerList", lecturerList);

                request.setAttribute("moduleIDVal", m.getModuleID());
                request.setAttribute("moduleNameVal", m.getModuleName());
                request.setAttribute("moduleCodeVal", m.getModuleCode());
                request.setAttribute("descriptionVal", m.getDescription());
                request.setAttribute("assignedLecturerIDVal", m.getAssignedLecturerID());

                String createdById = m.getCreatedBy();
                String createdByName = createdById; 
                if (createdById != null && !createdById.trim().isEmpty()) {
                    MyUsers creator = myUsersFacade.find(createdById);
                    if (creator != null) {
                        createdByName = creator.getFullName();
                    }
                }
                request.setAttribute("createdByName", createdByName);

                request.getRequestDispatcher("updatemodule.jsp").forward(request, response);
                return;
            }

            // ===== UPDATE MODULE =====
            if ("update".equals(action)) {

                String moduleIDStr = request.getParameter("moduleID");
                if (moduleIDStr == null || moduleIDStr.trim().isEmpty()) {
                    response.sendRedirect("Module?action=list");
                    return;
                }
                Integer moduleID = Integer.parseInt(moduleIDStr);

                MyModule m = myModuleFacade.find(moduleID);
                if (m == null) {
                    response.sendRedirect("Module?action=list");
                    return;
                }
                // AL can only update own modules
                if (!loginUser.getUserID().equals(m.getCreatedBy())) {
                    response.sendRedirect("Module?action=list");
                    return;
                }

                String moduleName = request.getParameter("moduleName");
                String moduleCode = request.getParameter("moduleCode");
                String description = request.getParameter("description");
                String assignedLecturerID = request.getParameter("assignedLecturerID");

                moduleName = (moduleName == null) ? "" : moduleName.trim();
                moduleCode = (moduleCode == null) ? "" : moduleCode.trim();
                description = (description == null) ? "" : description.trim();
                assignedLecturerID = (assignedLecturerID == null) ? "" : assignedLecturerID.trim();

                Map<String, String> errors = new HashMap<>();

                // Module Name
                if (moduleName.isEmpty()) {
                    errors.put("moduleName", "Module Name cannot be empty.");
                } else if (moduleName.length() < 2) {
                    errors.put("moduleName", "Module Name must be at least 2 characters.");
                } else if (moduleName.length() > 50) {
                    errors.put("moduleName", "Module Name must not exceed 50 characters.");
                }

                // Module Code 
                if (moduleCode.isEmpty()) {
                    errors.put("moduleCode", "Module Code cannot be empty.");
                } else if (moduleCode.length() < 2) {
                    errors.put("moduleCode", "Module Code must be at least 2 characters.");
                } else if (moduleCode.length() > 10) {
                    errors.put("moduleCode", "Module Code must not exceed 10 characters.");
                } else if (myModuleFacade.existsModuleCodeExceptId(moduleCode, moduleID)) {
                    errors.put("moduleCode", "Module Code already exists.");
                }

                // Description
                if (description.isEmpty()) {
                    errors.put("description", "Description cannot be empty.");
                } else if (description.length() > 100) {
                    errors.put("description", "Description must not exceed 100 characters.");
                }

                // Lecturer
                if (assignedLecturerID.isEmpty()) {
                    errors.put("assignedLecturerID", "Please select a lecturer.");
                }

                // if got errors then return back to updatemodule.jsp
                if (!errors.isEmpty()) {

                    request.setAttribute("errors", errors);

                    // keep old values
                    request.setAttribute("moduleIDVal", moduleID);
                    request.setAttribute("moduleNameVal", moduleName);
                    request.setAttribute("moduleCodeVal", moduleCode);
                    request.setAttribute("descriptionVal", description);
                    request.setAttribute("assignedLecturerIDVal", assignedLecturerID);

                    // reload dropdown
                    List<MyUsers> lecturerList = myUsersFacade.findLecturers();
                    request.setAttribute("lecturerList", lecturerList);

                    // created by name from module row
                    String createdById = m.getCreatedBy();
                    String createdByName = createdById;
                    if (createdById != null && !createdById.trim().isEmpty()) {
                        MyUsers creator = myUsersFacade.find(createdById);
                        if (creator != null) {
                            createdByName = creator.getFullName();
                        }
                    }
                    request.setAttribute("createdByName", createdByName);

                    request.getRequestDispatcher("updatemodule.jsp").forward(request, response);
                    return;
                }

                // PASS then update entity
                m.setModuleName(moduleName);
                m.setModuleCode(moduleCode);
                m.setDescription(description);
                m.setAssignedLecturerID(assignedLecturerID);

                myModuleFacade.edit(m);
                response.sendRedirect("Module?action=list");
                return;
            }

            // ===== DELETE MODULE =====
            if ("delete".equals(action)) {

                String moduleIDStr = request.getParameter("moduleID");
                if (moduleIDStr == null || moduleIDStr.trim().isEmpty()) {
                    response.sendRedirect("Module?action=list");
                    return;
                }

                Integer moduleID = Integer.parseInt(moduleIDStr);

                MyModule m = myModuleFacade.find(moduleID);
                if (m != null && loginUser.getUserID().equals(m.getCreatedBy())) {
                    myModuleFacade.remove(m);
                }

                response.sendRedirect("Module?action=list");
                return;
            }

            response.sendRedirect("Module?action=list");

        } catch (Exception e) {
            request.setAttribute("moduleList", java.util.Collections.emptyList());
            request.setAttribute("errorMsg", "Unable to load modules.");
            request.getRequestDispatcher("module.jsp").forward(request, response);
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

    private boolean isAcademicLeader(MyUsers user) {
        return user != null
                && user.getUserID() != null
                && user.getUserID().toUpperCase().startsWith("AL");
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
        return "Module management servlet";
    }
}
