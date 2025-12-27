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
import model.MyLecturerFacade;
import model.MyUsers;
import model.MyUsersFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "viewLecturers", urlPatterns = {"/viewLecturers"})
public class viewLecturers extends HttpServlet {

    @EJB
    private MyLecturerFacade myLecturerFacade;

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
            
            // Add CSS and JS references
            out.println("<link rel='stylesheet' type='text/css' href='css/viewLecturers.css'>");
            out.println("<script src='js/viewLecturers.js'></script>");

            // Get search query parameter
            String searchQuery = request.getParameter("search");
            if (searchQuery == null) {
                searchQuery = "";
            }
            searchQuery = searchQuery.trim().toLowerCase();

            //Retrieve List of Lecturer Records
            List<MyUsers> usersList = myUsersFacade.findLecturers();
            
            // Add search bar and Add New Lecturer button
            out.println("<div class='search-container'>");
            out.println("<form method='GET' action='viewLecturers' class='search-form'>");
            out.println("<input type='text' name='search' id='searchInput' placeholder='Search by name, email, IC, or phone...' ");
            out.println("value='" + (request.getParameter("search") != null ? request.getParameter("search") : "") + "' ");
            out.println("class='search-input' />");
            out.println("<button type='submit' class='btn-search'>Search</button>");
            out.println("<button type='button' onclick='clearSearch()' class='btn-clear'>Clear</button>");
            out.println("</form>");
            out.println("<button type='button' onclick='addNewLecturer()' class='btn-add'>Add New Lecturer</button>");
            out.println("</div>");

            // Display every user from usersList in table form
            out.println("<table class='lecturers-table'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>User ID</th>");
            out.println("<th>Full Name</th>");
            out.println("<th>Email</th>");
            out.println("<th>Phone</th>");
            out.println("<th>Gender</th>");
            out.println("<th>IC Number</th>");
            out.println("<th>Address</th>");
            out.println("<th>Employment Type</th>");
            out.println("<th>Academic Rank</th>");
            out.println("<th>Academic Leader ID</th>");
            out.println("<th>Password</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            for (MyUsers user : usersList) {
                // Fetch student-specific data
                model.MyLecturer lecturerData = myLecturerFacade.find(user.getUserID());
                
                // Apply search filter
                if (!searchQuery.isEmpty()) {
                    String fullName = user.getFullName().toLowerCase();
                    String email = user.getEmail().toLowerCase();
                    String icNumber = user.getIcNumber().toLowerCase();
                    String phone = user.getPhone().toLowerCase();
                    
                    // Skip this record if it doesn't match the search query
                    if (!fullName.contains(searchQuery) && 
                        !email.contains(searchQuery) && 
                        !icNumber.contains(searchQuery) && 
                        !phone.contains(searchQuery)) {
                        continue;
                    }
                }
                
                out.println("<tr>");
                out.println("<td>" + user.getUserID() + "</td>");
                out.println("<td>" + user.getFullName() + "</td>");
                out.println("<td>" + user.getEmail() + "</td>");
                out.println("<td>" + user.getPhone() + "</td>");
                out.println("<td>" + user.getGender() + "</td>");
                out.println("<td>" + user.getIcNumber() + "</td>");
                out.println("<td>" + user.getAddress() + "</td>");
                out.println("<td>" + (lecturerData != null ? lecturerData.getEmploymentType() : "") + "</td>");
                out.println("<td>" + (lecturerData != null ? lecturerData.getAcademicRank() : "") + "</td>");
                out.println("<td>" + (lecturerData != null ? lecturerData.getAcademicLeaderID() : "") + "</td>");
                out.println("<td>" + user.getPassword() + "</td>");
                out.println("<td>");
                out.println("<button type='button' onclick='editLecturer(" + user.getUserID() + ")' class='btn-edit'>Edit</button>");
                out.println("<button type='button' onclick='deleteLecturer(" + user.getUserID() + ")' class='btn-delete'>Delete</button>");
                out.println("</td>");
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
