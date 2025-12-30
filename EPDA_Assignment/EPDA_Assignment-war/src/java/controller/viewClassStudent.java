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
import model.MyStudentClassEnrollment;
import model.MyStudentClassEnrollmentFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "viewClassStudent", urlPatterns = {"/viewClassStudent"})
public class viewClassStudent extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            // Get classId parameter from request
            String classIdParam = request.getParameter("classId");
            
            if (classIdParam == null || classIdParam.isEmpty()) {
                out.println("<h3 style='color: red;'>Error: Class ID is required</h3>");
                out.println("<button type='button' onclick=\"location.href='viewClass'\" style='padding: 10px 20px; background-color: #007BFF; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 14px;'>Back to Classes</button>");
                return;
            }
            
            Integer classID = Integer.parseInt(classIdParam);
            
            // Get search query parameter
            String searchQuery = request.getParameter("search");
            if (searchQuery == null) {
                searchQuery = "";
            }
            searchQuery = searchQuery.trim().toLowerCase();
            
            //Retrieve List of Student Enrollment Records for the specific class
            List<MyStudentClassEnrollment> studentClassEnrollmentList = myStudentClassEnrollmentFacade.findClassStudent(classID);
            
            // Add search bar
            out.println("<div class='search-container'>");
            out.println("<form method='GET' action='viewClassStudent' class='search-form'>");
            out.println("<input type='hidden' name='classId' value='" + classID + "' />");
            out.println("<input type='text' name='search' id='searchInput' placeholder='Search by student ID...' ");
            out.println("value='" + (request.getParameter("search") != null ? request.getParameter("search") : "") + "' ");
            out.println("class='search-input' />");
            out.println("<button type='submit' class='btn-search'>Search</button>");
            out.println("<button type='button' onclick='window.location.href=\"viewClassStudent?classId=" + classID + "\"' class='btn-clear'>Clear</button>");
            out.println("</form>");
            out.println("<button type='button' onclick=\"location.href='addClassStudent.jsp?classID=" + classID + "'\" class='btn-add'>Add New Student</button>");
            out.println("</div>");
            
            // Display enrollment records in table form
            out.println("<table border='1' cellpadding='10' cellspacing='0' style='border-collapse: collapse; width: 100%;'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Enrollment ID</th>");
            out.println("<th>Class ID</th>");
            out.println("<th>Enrollment Date</th>");
            out.println("<th>Student ID</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            if (studentClassEnrollmentList == null || studentClassEnrollmentList.isEmpty()) {
                out.println("<tr>");
                out.println("<td colspan='5' style='text-align: center;'>No students enrolled in this class</td>");
                out.println("</tr>");
            } else {
                for (MyStudentClassEnrollment enrollment : studentClassEnrollmentList) {
                    // Apply search filter
                    if (!searchQuery.isEmpty()) {
                        String studentID = String.valueOf(enrollment.getStudentID()).toLowerCase();
                        
                        // Skip this record if it doesn't match the search query
                        if (!studentID.contains(searchQuery)) {
                            continue;
                        }
                    }
                    
                    out.println("<tr>");
                    out.println("<td>" + enrollment.getEnrollmentID() + "</td>");
                    out.println("<td>" + enrollment.getClassID() + "</td>");
                    out.println("<td>" + enrollment.getEnrollmentDate() + "</td>");
                    out.println("<td>" + enrollment.getStudentID() + "</td>");
                    out.println("<td>");
                    out.println("<button type='button' class='btn-delete' onclick=\"if(confirm('Are you sure you want to remove this student from the class?')) { location.href='removeClassStudent?enrollmentID=" + enrollment.getEnrollmentID() + "&classID=" + enrollment.getClassID() + "'; }\">Remove</button>");
                    out.println("</td>");
                    out.println("</tr>");
                }
            }
            
            out.println("</tbody>");
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
