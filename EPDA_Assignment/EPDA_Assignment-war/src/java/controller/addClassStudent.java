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
import model.MyStudentClassEnrollment;
import model.MyStudentClassEnrollmentFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "addClassStudent", urlPatterns = {"/addClassStudent"})
public class addClassStudent extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private MyStudentClassEnrollmentFacade myStudentClassEnrollmentFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {
                // Get and validate request parameters
                String studentID = request.getParameter("studentID");
                String classIDParam = request.getParameter("classID");

                // Validate studentID is not null or empty
                if (studentID == null || studentID.trim().isEmpty()) {
                    throw new IllegalArgumentException("Student ID is required!");
                }

                // Validate classID
                if (classIDParam == null || classIDParam.trim().isEmpty()) {
                    throw new IllegalArgumentException("Class ID is required!");
                }

                // Parse and validate classID
                int classID;
                try {
                    classID = Integer.parseInt(classIDParam);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid Class ID format. Please enter a valid number.");
                }

                // Create new enrollment record with current date
                MyStudentClassEnrollment studentEnrollment = new MyStudentClassEnrollment(studentID, classID);
                myStudentClassEnrollmentFacade.create(studentEnrollment);

                // Display success message and redirect to view page
                out.println("<script type='text/javascript'>");
                out.println("alert('Student added to class successfully!');");
                out.println("window.location.href = 'viewClassStudent.jsp?classId=" + classID + "';");
                out.println("</script>");
            } catch (Exception e) {
                // Display error message and go back
                out.println("<script type='text/javascript'>");
                out.println("alert('An error occurred while adding student to class: " + e.getMessage().replace("'", "\\'") + "');");
                out.println("window.history.back();");
                out.println("</script>");
            }
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Add student to class";
    }
}
