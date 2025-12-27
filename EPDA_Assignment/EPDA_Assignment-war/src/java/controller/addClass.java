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
import model.MyStudentClassFacade;
import static model.MyStudentClass_.academicYear;
import static model.MyStudentClass_.className;
import static model.MyStudentClass_.createdBy;
import static model.MyStudentClass_.semester;

/**
 *
 * @author bohch
 */
@WebServlet(name = "addClass", urlPatterns = {"/addClass"})
public class addClass extends HttpServlet {

    @EJB
    private MyStudentClassFacade myStudentClassFacade;
    
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
            String className = request.getParameter("className");
            String semester = request.getParameter("semester");
            String academicYear = request.getParameter("academicYear");
            String createdBy = request.getParameter("createdBy");
            
            //Create New Class Record
            model.MyStudentClass studentClass = new model.MyStudentClass();
            studentClass.setClassName(className);
            studentClass.setSemester(semester);
            studentClass.setAcademicYear(academicYear);
            studentClass.setCreatedBy(createdBy);
            
            myStudentClassFacade.create(studentClass);
            
            //Redirect to viewClass.jsp after successful addition
            response.sendRedirect("viewClass.jsp");
        } catch (Exception e) {
            //Set error message and forward back to the form
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("addClass.jsp").forward(request, response);
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
