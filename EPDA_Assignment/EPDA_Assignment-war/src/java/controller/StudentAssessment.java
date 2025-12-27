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

            // we always expect assessmentID for this module grading flow
            String assessmentIDStr = request.getParameter("assessmentID");
            Integer assessmentID = null;
            if (assessmentIDStr != null && !assessmentIDStr.trim().isEmpty()) {
                assessmentID = Integer.parseInt(assessmentIDStr);
            }

            // ===== GO EDIT PAGE =====
            if ("edit".equals(action)) {

                String saIDStr = request.getParameter("studentAssessmentID");
                if (saIDStr == null || saIDStr.trim().isEmpty() || assessmentID == null) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                Integer studentAssessmentID = Integer.parseInt(saIDStr);

                MyStudentAssessment sa = myStudentAssessmentFacade.find(studentAssessmentID);
                if (sa == null || !sa.getAssessmentID().equals(assessmentID)) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                // load assessment
                MyAssessmentType a = myAssessmentTypeFacade.find(assessmentID);
                if (a == null || a.getModuleID() == null) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                Integer moduleID = a.getModuleID();
                MyModule moduleRow = myModuleFacade.find(moduleID);

                String lecturerID = loginUser.getUserID();
                if (moduleRow == null
                        || moduleRow.getAssignedLecturerID() == null
                        || !moduleRow.getAssignedLecturerID().equals(lecturerID)) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                // student display
                MyUsers stu = myUsersFacade.find(sa.getStudentID());
                String studentDisplay = (stu == null)
                        ? sa.getStudentID()
                        : (stu.getUserID() + " - " + stu.getFullName());

                request.setAttribute("moduleID", moduleID);
                request.setAttribute("assessmentID", assessmentID);
                request.setAttribute("assessmentName", a.getAssessmentName());

                request.setAttribute("studentAssessmentIDVal", sa.getStudentAssessmentID());
                request.setAttribute("studentDisplay", studentDisplay);

                request.setAttribute("markVal", sa.getMark());
                request.setAttribute("gradeVal", sa.getGrade());
                request.setAttribute("feedbackVal", sa.getFeedbackText());
                request.setAttribute("dateAssessedVal", sa.getDateAssessed());

                request.setAttribute("lecturerDisplay",
                        lecturerID + " - " + loginUser.getFullName());

                request.getRequestDispatcher("modifyStudentMark.jsp").forward(request, response);
                return;
            }

            // ===== GO ADD PAGE =====
            if ("goAdd".equals(action)) {

                String studentID = request.getParameter("studentID");
                if (assessmentID == null || studentID == null || studentID.trim().isEmpty()) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                // assessment must exist
                MyAssessmentType a = myAssessmentTypeFacade.find(assessmentID);
                if (a == null || a.getModuleID() == null) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                // module must belong to logged-in lecturer
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

                // load student display (from MyUsers)
                MyUsers stu = myUsersFacade.find(studentID);
                String studentDisplay = (stu == null)
                        ? studentID
                        : (stu.getUserID() + " - " + stu.getFullName());

                // set attributes for addStudentMark.jsp
                request.setAttribute("moduleID", moduleID);
                request.setAttribute("assessmentID", assessmentID);
                request.setAttribute("assessmentName", a.getAssessmentName());

                request.setAttribute("studentID", studentID);
                request.setAttribute("studentDisplay", studentDisplay);

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

                // Feedback
                if (feedbackText.isEmpty()) {
                    errors.put("feedbackText", "Feedback cannot be empty.");
                } else if (feedbackText.length() > 100) {
                    errors.put("feedbackText", "Feedback must not exceed 100 characters.");
                }

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

                // if errors then forward back to add page with values
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

                    request.setAttribute("lecturerDisplay", lecturerID + " - " + loginUser.getFullName());

                    request.setAttribute("markVal", markStr);
                    request.setAttribute("feedbackVal", feedbackText);

                    request.setAttribute("gradeVal", "(Auto Generated)");
                    request.setAttribute("dateAssessedVal", "(Auto Generated)");
                    request.setAttribute("moduleID", moduleID);

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

                response.sendRedirect("Assessment?action=studentList&moduleID=" + moduleID + "&assessmentID=" + assessmentID);
                return;
            }

            // ===== UPDATE (SAVE MODIFIED MARK) =====
            if ("update".equals(action)) {

                String saIDStr = request.getParameter("studentAssessmentID");
                String markStr = request.getParameter("mark");
                String feedbackText = request.getParameter("feedbackText");
                feedbackText = (feedbackText == null) ? "" : feedbackText.trim();

                if (saIDStr == null || saIDStr.trim().isEmpty() || assessmentID == null) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                Integer studentAssessmentID = Integer.parseInt(saIDStr);
                MyStudentAssessment sa = myStudentAssessmentFacade.find(studentAssessmentID);

                if (sa == null || !sa.getAssessmentID().equals(assessmentID)) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                MyAssessmentType a = myAssessmentTypeFacade.find(assessmentID);
                Integer moduleID = a.getModuleID();
                MyModule moduleRow = myModuleFacade.find(moduleID);

                String lecturerID = loginUser.getUserID();
                if (moduleRow == null
                        || moduleRow.getAssignedLecturerID() == null
                        || !moduleRow.getAssignedLecturerID().equals(lecturerID)) {
                    response.sendRedirect("Lmodule.jsp");
                    return;
                }

                Map<String, String> errors = new HashMap<>();

                // Feedback
                if (feedbackText.isEmpty()) {
                    errors.put("feedbackText", "Feedback cannot be empty.");
                } else if (feedbackText.length() > 100) {
                    errors.put("feedbackText", "Feedback must not exceed 100 characters.");
                }

                Integer mark = null;
                try {
                    mark = Integer.parseInt(markStr);
                    if (mark < 0 || mark > 100) {
                        errors.put("mark", "Mark must be between 0 and 100.");
                    }
                } catch (Exception e) {
                    errors.put("mark", "Mark must be a valid number.");
                }

                String gradeLetter = null;
                if (mark != null) {
                    MyGrading g = myGradingFacade.findByPercentage(mark);
                    if (g == null) {
                        errors.put("mark", "No grading range found.");
                    } else {
                        gradeLetter = g.getGradeLetter();
                    }
                }

                if (!errors.isEmpty()) {

                    MyUsers stu = myUsersFacade.find(sa.getStudentID());
                    String studentDisplay = (stu == null)
                            ? sa.getStudentID()
                            : (stu.getUserID() + " - " + stu.getFullName());

                    request.setAttribute("errors", errors);
                    request.setAttribute("moduleID", moduleID);
                    request.setAttribute("assessmentID", assessmentID);
                    request.setAttribute("assessmentName", a.getAssessmentName());

                    request.setAttribute("studentAssessmentIDVal", sa.getStudentAssessmentID());
                    request.setAttribute("studentDisplay", studentDisplay);

                    request.setAttribute("markVal", markStr);
                    request.setAttribute("gradeVal", (gradeLetter != null ? gradeLetter : sa.getGrade()));
                    request.setAttribute("feedbackVal", feedbackText);
                    request.setAttribute("dateAssessedVal", sa.getDateAssessed());
                    request.setAttribute("lecturerDisplay",
                            sa.getAssessedBy() + " - " + loginUser.getFullName());

                    request.getRequestDispatcher("modifyStudentMark.jsp").forward(request, response);
                    return;
                }

                // update
                sa.setMark(mark);
                sa.setGrade(gradeLetter);
                sa.setFeedbackText(feedbackText);
                sa.setAssessedBy(lecturerID);
                sa.setDateAssessed(LocalDate.now().toString());

                myStudentAssessmentFacade.edit(sa);

                response.sendRedirect(
                        "Assessment?action=studentList&moduleID=" + moduleID
                        + "&assessmentID=" + assessmentID);
                return;
            }

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
