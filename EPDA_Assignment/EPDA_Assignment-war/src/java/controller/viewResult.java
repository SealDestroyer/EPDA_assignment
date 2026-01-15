/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyAssessmentType;
import model.MyAssessmentTypeFacade;
import model.MyGradingFacade;
import model.MyModule;
import model.MyModuleFacade;
import model.MyStudentAssessment;
import model.MyStudentAssessmentFacade;
import model.MyUsersFacade;
import model.MyUsers;

/**
 *
 * @author bohch
 */
@WebServlet(name = "viewResult", urlPatterns = {"/viewResult"})
public class viewResult extends HttpServlet {

    @EJB
    private MyUsersFacade myUsersFacade;

    @EJB
    private MyModuleFacade myModuleFacade;

    @EJB
    private MyAssessmentTypeFacade myAssessmentTypeFacade;

    @EJB
    private MyGradingFacade myGradingFacade;

    @EJB
    private MyStudentAssessmentFacade myStudentAssessmentFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        try (PrintWriter out = response.getWriter()) {
            String studentID = session.getAttribute("userID").toString();
            List<MyStudentAssessment> assessments = myStudentAssessmentFacade.findByStudentID(studentID);

            String searchParam = request.getParameter("search");
            String searchQuery = searchParam != null ? searchParam.trim().toLowerCase() : "";
            String searchValue = searchParam != null ? searchParam.trim() : "";

                out.println("<div class='page-container'>");

                out.println("<div class='search-area'>");
                out.println("<p class='eyebrow'>Student Dashboard</p>");
                out.println("<h1 class='page-title'>Result Overview</h1>");
                out.println("<p class='subtext'>Review your assessments, marks and feedback in one place.</p>");
                out.println("<form method='GET' action='viewResult.jsp' class='search-form'>");
                out.println("<input type='text' name='search' id='searchInput' placeholder='Search by module, lecturer, feedback...' "
                    + "value='" + escapeHtml(searchValue) + "' class='search-input' />");
                out.println("<div class='search-actions'>");
                out.println("<button type='submit' class='btn-search'>Search</button>");
                out.println("<button type='button' class='btn-clear' onclick=\"window.location.href='viewResult.jsp'\">Clear</button>");
                out.println("</div>");
                out.println("</form>");
                out.println("</div>");

            out.println("<div class='content-shell'>");
            out.println("<div class='card'>");
            out.println("<div class='card-header'>");
            out.println("<div>");
            out.println("<p class='eyebrow'>Assessments</p>");
            out.println("<h2>My Result List</h2>");
            out.println("</div>");
            out.println("</div>");

            out.println("<div class='table-container'>");
            out.println("<table class='result-table'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Assessment ID</th>");
            out.println("<th>Assessment Name</th>");
            out.println("<th>Module</th>");
            out.println("<th>Mark</th>");
            out.println("<th>Date Assessed</th>");
            out.println("<th>Feedback</th>");
            out.println("<th>Assessed by</th>");
            out.println("<th>Grade</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            for (MyStudentAssessment assessment : assessments) {
                MyAssessmentType assessmentType = null;
                if (assessment.getAssessmentID() != null) {
                    assessmentType = myAssessmentTypeFacade.findByAssessmentID(assessment.getAssessmentID());
                }

                // Skip orphaned student assessments that point to a deleted assessment type
                if (assessmentType == null) {
                    continue;
                }

                String assessmentName = assessmentType.getAssessmentName() != null
                        ? assessmentType.getAssessmentName()
                        : "-";

                String moduleName = "-";
                Integer moduleID = assessmentType.getModuleID();
                if (moduleID != null) {
                    MyModule module = myModuleFacade.findByModuleID(moduleID);
                    if (module != null && module.getModuleName() != null) {
                        moduleName = module.getModuleName();
                    }
                }

                String lecturerName = "-";
                if (assessment.getAssessedBy() != null) {
                    MyUsers lecturer = myUsersFacade.findByUserId(assessment.getAssessedBy());
                    if (lecturer != null) {
                        lecturerName = lecturer.getFullName();
                    }
                }

                String feedback = assessment.getFeedbackText() != null ? assessment.getFeedbackText() : "-";
                String mark = assessment.getMark() != null ? assessment.getMark().toString() : "-";
                String grade = assessment.getGrade() != null ? assessment.getGrade() : "-";
                String assessmentId = assessment.getAssessmentID() != null ? assessment.getAssessmentID().toString() : "-";
                String dateAssessed = assessment.getDateAssessed() != null ? assessment.getDateAssessed().toString() : "-";

                if (!searchQuery.isEmpty()) {
                    boolean matches = assessmentName.toLowerCase().contains(searchQuery)
                            || moduleName.toLowerCase().contains(searchQuery)
                            || lecturerName.toLowerCase().contains(searchQuery)
                            || feedback.toLowerCase().contains(searchQuery)
                            || mark.toLowerCase().contains(searchQuery)
                            || grade.toLowerCase().contains(searchQuery);

                    if (!matches) {
                        continue;
                    }
                }

                out.println("<tr>");
                out.println("<td>" + escapeHtml(assessmentId) + "</td>");
                out.println("<td>" + escapeHtml(assessmentName) + "</td>");
                out.println("<td>" + escapeHtml(moduleName) + "</td>");
                out.println("<td>" + escapeHtml(mark) + "</td>");
                out.println("<td>" + escapeHtml(dateAssessed) + "</td>");
                out.println("<td>" + escapeHtml(feedback) + "</td>");
                out.println("<td>" + escapeHtml(lecturerName) + "</td>");
                out.println("<td class='grade-cell'>" + escapeHtml(grade) + "</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

}
