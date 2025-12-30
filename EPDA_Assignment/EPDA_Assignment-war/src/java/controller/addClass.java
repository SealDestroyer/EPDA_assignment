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
import static model.MyStudentClass_.assignedAcademicLeaderID;
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
        try (PrintWriter out = response.getWriter()) {
            try {
                // Retrieve form parameters from the request
                String className = request.getParameter("className");
                String semester = request.getParameter("semester");
                String academicYear = request.getParameter("academicYear");
                String assignedAcademicLeaderID = request.getParameter("assignedAcademicLeaderID");

                // Validate that all required fields are present and not empty
                if (className == null || className.trim().isEmpty()) {
                    throw new IllegalArgumentException("Class name is required");
                }
                if (semester == null || semester.trim().isEmpty()) {
                    throw new IllegalArgumentException("Semester is required");
                }
                if (academicYear == null || academicYear.trim().isEmpty()) {
                    throw new IllegalArgumentException("Academic year is required");
                }
                if (assignedAcademicLeaderID == null || assignedAcademicLeaderID.trim().isEmpty()) {
                    throw new IllegalArgumentException("Assigned Academic Leader ID is required");
                }

                // Validate class name length
                if (className.trim().length() < 2 || className.trim().length() > 50) {
                    throw new IllegalArgumentException("Class name must be between 2 and 50 characters");
                }

                // Validate semester format (must be 1, 2, or 3)
                if (!semester.trim().matches("^[1-3]$")) {
                    throw new IllegalArgumentException("Semester must be 1, 2, or 3");
                }

                // Validate academic year format (must be YYYY/YYYY)
                if (!academicYear.trim().matches("^20\\d{2}/20\\d{2}$")) {
                    throw new IllegalArgumentException("Academic year must be in format YYYY/YYYY (e.g., 2024/2025)");
                }

                // Validate that the second year is exactly one year after the first
                String[] years = academicYear.trim().split("/");
                int firstYear = Integer.parseInt(years[0]);
                int secondYear = Integer.parseInt(years[1]);
                if (secondYear != firstYear + 1) {
                    throw new IllegalArgumentException("Second year must be exactly one year after the first year");
                }

                // Create new student class record with validated data
                model.MyStudentClass studentClass = new model.MyStudentClass();
                studentClass.setClassName(className.trim());
                studentClass.setSemester(semester.trim());
                studentClass.setAcademicYear(academicYear.trim());
                studentClass.setAssignedAcademicLeaderID(assignedAcademicLeaderID.trim());

                // Persist the new class to the database
                myStudentClassFacade.create(studentClass);

                // Display success message and redirect to viewClass.jsp after successful addition
                out.println("<script type='text/javascript'>");
                out.println("alert('Class Added Successfully!');");
                out.println("window.location.href = 'viewClass.jsp';");
                out.println("</script>");
            } catch (Exception e) {
                // Display error message and return to the form for user correction
                out.println("<script type='text/javascript'>");
                out.println("alert('Error: " + e.getMessage().replace("'", "\\'") + "');");
                out.println("window.history.back();");
                out.println("</script>");
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
