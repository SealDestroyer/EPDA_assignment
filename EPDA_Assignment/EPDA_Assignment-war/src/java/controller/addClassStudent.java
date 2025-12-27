/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MyStudentClassEnrollment;
import model.MyStudentClassEnrollmentFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "addClassStudent", urlPatterns = {"/addClassStudent"})
public class addClassStudent extends HttpServlet {

    @EJB
    private MyStudentClassEnrollmentFacade myStudentClassEnrollmentFacade;

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
        try {
            String studentID = request.getParameter("studentID");
            Integer classID = Integer.parseInt(request.getParameter("classID"));
            String enrollmentDate = request.getParameter("enrollmentDate");
            
            //Create New Enrollment Record
            MyStudentClassEnrollment studentEnrollment = new MyStudentClassEnrollment(studentID, classID, enrollmentDate);
            myStudentClassEnrollmentFacade.create(studentEnrollment);
            
            request.setAttribute("message", "Student added to class successfully!");
            request.getRequestDispatcher("viewClassStudent.jsp?classId=" + classID).forward(request, response);
        } catch (Exception e) {
            String classID = request.getParameter("classID");
            request.getRequestDispatcher("addClassStudent.jsp?classID=" + classID).forward(request, response);
        }
    }
    
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
