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
import model.MyStudentFacade;
import model.MyUsers;
import model.MyUsersFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "viewStudent", urlPatterns = {"/viewStudent"})
public class viewStudent extends HttpServlet {

    @EJB
    private MyUsersFacade myUsersFacade;

    @EJB
    private MyStudentFacade myStudentFacade;

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

            // Get search query parameter
            String searchQuery = request.getParameter("search");
            if (searchQuery == null) {
                searchQuery = "";
            }
            searchQuery = searchQuery.trim().toLowerCase();

            //Retrieve List of Student Records
            List<MyUsers> usersList = myUsersFacade.findAllStudents();
            
            // Add search bar
            out.println("<div class='search-container'>");
            out.println("<form method='GET' action='viewStudent.jsp' class='search-form'>");
            out.println("<input type='text' name='search' id='searchInput' placeholder='Search by name, matric no, email, or IC...' ");
            out.println("value='" + (request.getParameter("search") != null ? request.getParameter("search") : "") + "' ");
            out.println("class='search-input' />");
            out.println("<button type='submit' class='btn-search'>Search</button>");
            out.println("<button type='button' onclick='clearSearch()' class='btn-clear'>Clear</button>");
            out.println("</form>");
            out.println("<button type='button' onclick='window.location.href=\"registerStudent.jsp\"' class='btn-add'>Add New Student</button>");
            out.println("</div>");
            
            // Display every user from usersList in table form
            out.println("<table class='student-table'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>User ID</th>");
            out.println("<th>Matric No</th>");
            out.println("<th>Full Name</th>");
            out.println("<th>IC Number</th>");
            out.println("<th>Gender</th>");
            out.println("<th>Email</th>");
            out.println("<th>Phone</th>");
            out.println("<th>Address</th>");
            out.println("<th>Intake Year</th>");
            out.println("<th>Current Level</th>");
            out.println("<th>Status</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            for (MyUsers user : usersList) {
                // Fetch student-specific data
                model.MyStudent studentData = myStudentFacade.find(user.getUserID());
                
                // Apply search filter
                if (!searchQuery.isEmpty()) {
                    String matricNo = (studentData != null ? studentData.getMatricNo() : "").toLowerCase();
                    String fullName = user.getFullName().toLowerCase();
                    String icNumber = user.getIcNumber().toLowerCase();
                    String email = user.getEmail().toLowerCase();
                    
                    // Skip this record if it doesn't match the search query
                    if (!matricNo.contains(searchQuery) && 
                        !fullName.contains(searchQuery) && 
                        !icNumber.contains(searchQuery) && 
                        !email.contains(searchQuery)) {
                        continue;
                    }
                }
                
                out.println("<tr>");
                out.println("<td>" + user.getUserID() + "</td>");
                out.println("<td>" + (studentData != null ? studentData.getMatricNo() : "N/A") + "</td>");
                out.println("<td>" + user.getFullName() + "</td>");
                out.println("<td>" + user.getIcNumber() + "</td>");
                out.println("<td>" + user.getGender() + "</td>");
                out.println("<td>" + user.getEmail() + "</td>");
                out.println("<td>" + user.getPhone() + "</td>");
                out.println("<td>" + user.getAddress() + "</td>");
                out.println("<td>" + (studentData != null ? studentData.getIntakeYear() : "N/A") + "</td>");
                out.println("<td>" + (studentData != null ? studentData.getCurrentLevel() : "N/A") + "</td>");
                out.println("<td>" + (studentData != null ? studentData.getStatus() : "N/A") + "</td>");
                out.print("<td><button onclick='editStudent(" + user.getUserID() + ")' class='btn-edit'>Edit</button>");
                out.print("<button onclick='deleteStudent(" + user.getUserID() + ")' class='btn-delete'>Delete</button></td>");
                out.println("</tr>");
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
