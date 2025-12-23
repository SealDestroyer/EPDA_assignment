package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
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
import model.MyGrading;
import model.MyGradingFacade;
import model.MyModule;
import model.MyModuleFacade;
import model.MyStudentAssessment;
import model.MyStudentAssessmentFacade;
import model.MyUsers;
import model.MyUsersFacade;

@WebServlet(name = "StudentAssessment", urlPatterns = {"/StudentAssessment"})
public class StudentAssessment extends HttpServlet {

    @EJB
    private MyAssessmentTypeFacade myAssessmentTypeFacade;

    @EJB
    private MyGradingFacade myGradingFacade;

    @EJB
    private MyStudentAssessmentFacade myStudentAssessmentFacade;

    @EJB
    private MyUsersFacade myUsersFacade;

    @EJB
    private MyModuleFacade myModuleFacade; // ✅ needed for lecturer-module security

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

            // we always expect assessmentID for this module grading flow
            String assessmentIDStr = request.getParameter("assessmentID");
            Integer assessmentID = null;
            if (assessmentIDStr != null && !assessmentIDStr.trim().isEmpty()) {
                assessmentID = Integer.parseInt(assessmentIDStr);
            }

            // ===== GO ADD PAGE =====
            if ("goAdd".equals(action)) {

                String studentID = request.getParameter("studentID");
                if (assessmentID == null || studentID == null || studentID.trim().isEmpty()) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                // 1) assessment must exist
                MyAssessmentType a = myAssessmentTypeFacade.find(assessmentID);
                if (a == null || a.getModuleID() == null) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                // 2) module must belong to logged-in lecturer
                Integer moduleID = a.getModuleID();
                MyModule moduleRow = myModuleFacade.find(moduleID);
                if (moduleRow == null) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                String lecturerID = loginUser.getUserID();
                if (moduleRow.getAssignedLecturerID() == null
                        || !moduleRow.getAssignedLecturerID().equals(lecturerID)) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                // 3) load student display (from MyUsers)
                MyUsers stu = myUsersFacade.find(studentID);
                String studentDisplay = (stu == null)
                        ? studentID
                        : (stu.getUserID() + " - " + stu.getFullName());

                // 4) set attributes for addStudentMark.jsp
                request.setAttribute("assessmentID", assessmentID);
                request.setAttribute("assessmentName", a.getAssessmentName());

                request.setAttribute("studentID", studentID);
                request.setAttribute("studentDisplay", studentDisplay);

                request.setAttribute("lecturerID", lecturerID);
                request.setAttribute("lecturerDisplay", lecturerID + " - " + loginUser.getFullName());

                // auto generated placeholders
                request.setAttribute("gradeVal", "(Auto Generated)");
                request.setAttribute("dateAssessedVal", "(Auto Generated)");

                request.getRequestDispatcher("addStudentMark.jsp").forward(request, response);
                return;
            }

            // ===== ADD (SAVE MARK) =====
            if ("add".equals(action)) {

                String studentID = request.getParameter("studentID");
                String markStr = request.getParameter("mark");
                String feedbackText = request.getParameter("feedbackText");

                studentID = (studentID == null) ? "" : studentID.trim();
                markStr = (markStr == null) ? "" : markStr.trim();
                feedbackText = (feedbackText == null) ? "" : feedbackText.trim();

                Map<String, String> errors = new HashMap<>();

                if (assessmentID == null) {
                    errors.put("mark", "Missing assessment.");
                }
                if (studentID.isEmpty()) {
                    errors.put("mark", "Missing student.");
                }

                // validate mark
                Integer mark = null;
                if (markStr.isEmpty()) {
                    errors.put("mark", "Mark cannot be empty.");
                } else {
                    try {
                        mark = Integer.parseInt(markStr);
                        if (mark < 0 || mark > 100) {
                            errors.put("mark", "Mark must be between 0 and 100.");
                        }
                    } catch (Exception e) {
                        errors.put("mark", "Mark must be a valid number.");
                    }
                }

                // load assessment + security check (same as goAdd)
                MyAssessmentType a = (assessmentID == null) ? null : myAssessmentTypeFacade.find(assessmentID);
                if (a == null || a.getModuleID() == null) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                Integer moduleID = a.getModuleID();
                MyModule moduleRow = myModuleFacade.find(moduleID);
                if (moduleRow == null) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                String lecturerID = loginUser.getUserID();
                if (moduleRow.getAssignedLecturerID() == null
                        || !moduleRow.getAssignedLecturerID().equals(lecturerID)) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                // compute grade (only if mark valid)
                String gradeLetter = null;
                if (mark != null) {
                    MyGrading g = myGradingFacade.findByPercentage(mark);
                    if (g == null) {
                        errors.put("mark", "No grading range found for this mark. Please check grading table.");
                    } else {
                        gradeLetter = g.getGradeLetter();
                    }
                }

                // if errors -> forward back to add page with values
                if (!errors.isEmpty()) {

                    // rebuild display info for UI
                    MyUsers stu = myUsersFacade.find(studentID);
                    String studentDisplay = (stu == null)
                            ? studentID
                            : (stu.getUserID() + " - " + stu.getFullName());

                    request.setAttribute("errors", errors);

                    request.setAttribute("assessmentID", assessmentID);
                    request.setAttribute("assessmentName", a.getAssessmentName());

                    request.setAttribute("studentID", studentID);
                    request.setAttribute("studentDisplay", studentDisplay);

                    request.setAttribute("lecturerID", lecturerID);
                    request.setAttribute("lecturerDisplay", lecturerID + " - " + loginUser.getFullName());

                    request.setAttribute("markVal", markStr);
                    request.setAttribute("feedbackVal", feedbackText);

                    request.setAttribute("gradeVal", "(Auto Generated)");
                    request.setAttribute("dateAssessedVal", "(Auto Generated)");

                    request.getRequestDispatcher("addStudentMark.jsp").forward(request, response);
                    return;
                }

                // create & save MyStudentAssessment
                MyStudentAssessment sa = new MyStudentAssessment();
                sa.setAssessmentID(assessmentID);
                sa.setStudentID(studentID);
                sa.setMark(mark);
                sa.setGrade(gradeLetter);
                sa.setFeedbackText(feedbackText);
                sa.setAssessedBy(lecturerID);
                sa.setDateAssessed(LocalDate.now().toString());

                myStudentAssessmentFacade.create(sa);

                // back to list page (your list is in Assessment servlet studentList)
                // If you already have a StudentAssessment?action=list, you can redirect there instead.
                response.sendRedirect("Assessment?action=studentList&moduleID=" + moduleID + "&assessmentID=" + assessmentID);
                return;
            }

            // fallback: go back
            response.sendRedirect("Lmodule.jsp");

        } catch (Exception e) {
            response.sendRedirect("Lmodule.jsp");
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

    @Override
    public String getServletInfo() {
        return "Student assessment grading servlet";
    }
}
