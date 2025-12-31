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
import model.MyAssessmentTypeFacade;
import model.MyGradingFacade;
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           HttpSession session = request.getSession();
           String studentID = session.getAttribute("userID").toString();
           List<MyStudentAssessment> assessments = myStudentAssessmentFacade.findByStudentID(studentID);
           
           out.println("<table border='1'>");
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
           
           for (MyStudentAssessment assessment : assessments) {
               String assessmentName = "";
               String moduleName = "";
               if (assessment.getAssessmentID() != null) {
                   assessmentName = myAssessmentTypeFacade.findByAssessmentID(assessment.getAssessmentID()).getAssessmentName();
                   Integer moduleID = myAssessmentTypeFacade.findByAssessmentID(assessment.getAssessmentID()).getModuleID();
                   if (moduleID != null) {
                       moduleName = myModuleFacade.findByModuleID(moduleID).getModuleName();
                   }
               }
               
               String lecturerName = "";
               if (assessment.getAssessedBy() != null) {
                   MyUsers lecturer = myUsersFacade.findByUserId(assessment.getAssessedBy());
                   if (lecturer != null) {
                       lecturerName = lecturer.getFullName();
                   }
               }
               
               out.println("<tr>");
               out.println("<td>" + assessment.getAssessmentID() + "</td>");
               out.println("<td>" + assessmentName + "</td>");
               out.println("<td>" + moduleName + "</td>");
               out.println("<td>" + assessment.getMark() + "</td>");
               out.println("<td>" + assessment.getDateAssessed() + "</td>");
               out.println("<td>" + assessment.getFeedbackText() + "</td>");
               out.println("<td>" + lecturerName + "</td>");
               out.println("<td>" + assessment.getGrade() + "</td>");
               out.println("</tr>");
           }
           
           out.println("</table>");
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

}
