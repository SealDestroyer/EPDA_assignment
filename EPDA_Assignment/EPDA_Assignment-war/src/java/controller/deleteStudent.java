/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MyStudentAssessmentFacade;
import model.MyStudentClassEnrollmentFacade;
import model.MyStudentFacade;
import model.MyUsersFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "deleteStudent", urlPatterns = {"/deleteStudent"})
public class deleteStudent extends HttpServlet {

    @EJB
    private MyStudentClassEnrollmentFacade myStudentClassEnrollmentFacade;

    @EJB
    private MyStudentAssessmentFacade myStudentAssessmentFacade;

    @EJB
    private MyStudentFacade myStudentFacade;

    @EJB
    private MyUsersFacade myUsersFacade;

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

            // Get the student ID from the request parameter
            String userID = request.getParameter("id");
            
            if (userID != null && !userID.isEmpty()) {
                try {
                    // Delete records in order to maintain referential integrity
                    // 1. Delete student assessment records
                    myStudentAssessmentFacade.deleteByStudentID(userID);
                    
                    // 2. Delete student class enrollment records
                    myStudentClassEnrollmentFacade.deleteByStudentID(userID);
                    
                    // 3. Delete student record
                    myStudentFacade.deleteByUserID(userID);
                    
                    // 4. Delete user record
                    myUsersFacade.deleteByUserId(userID);
                    
                    // Redirect back to viewStudent page
                    response.sendRedirect("viewStudent");
                } catch (Exception e) {
                    out.println("<h3>Error deleting student: " + e.getMessage() + "</h3>");
                    out.println("<a href='viewStudent'>Back to Student List</a>");
                }
            } else {
                out.println("<h3>Invalid student ID</h3>");
                out.println("<a href='viewStudent'>Back to Student List</a>");
            }

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
