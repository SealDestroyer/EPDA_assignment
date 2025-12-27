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
import model.MyStudentClass;
import model.MyStudentClassFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "viewClass", urlPatterns = {"/viewClass"})
public class viewClass extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            
            // Include CSS and JavaScript files
            out.println("<link rel='stylesheet' type='text/css' href='css/viewClass.css'>");
            out.println("<script src='js/viewClass.js'></script>");
            
            // Get search query parameter
            String searchQuery = request.getParameter("search");
            if (searchQuery == null) {
                searchQuery = "";
            }
            searchQuery = searchQuery.trim().toLowerCase();

            //Retrieve List of Class Records
            List<MyStudentClass> classList = myStudentClassFacade.findAll();
            
            // Add search bar and Add New Class button
            out.println("<div class='search-container'>");
            out.println("<form method='GET' action='viewClass.jsp' class='search-form'>");
            out.println("<input type='text' name='search' id='searchInput' placeholder='Search by class name...' ");
            out.println("value='" + (request.getParameter("search") != null ? request.getParameter("search") : "") + "' ");
            out.println("class='search-input' />");
            out.println("<button type='submit' class='btn-search'>Search</button>");
            out.println("<button type='button' onclick='clearSearch()' class='btn-clear'>Clear</button>");
            out.println("</form>");
            out.println("<button type='button' onclick='addNewClass()' class='btn-add'>Add New Class</button>");
            out.println("</div>");

            // Display every class from classList in table form
            out.println("<table class='class-table'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Class ID</th>");
            out.println("<th>Class Name</th>");
            out.println("<th>Semester</th>");
            out.println("<th>Academic Year</th>");
            out.println("<th>Created By</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            for (MyStudentClass studentClass : classList) {
                // Apply search filter
                if (!searchQuery.isEmpty()) {
                    String className = studentClass.getClassName().toLowerCase();
                    
                    // Skip this record if it doesn't match the search query
                    if (!className.contains(searchQuery)) {
                        continue;
                    }
                }
                
                out.println("<tr>");
                out.println("<td>" + studentClass.getClassID() + "</td>");
                out.println("<td>" + studentClass.getClassName() + "</td>");
                out.println("<td>" + studentClass.getSemester() + "</td>");
                out.println("<td>" + studentClass.getAcademicYear() + "</td>");
                out.println("<td>" + studentClass.getCreatedBy() + "</td>");
                out.println("<td>");
                out.println("<button type='button' onclick='viewStudents(" + studentClass.getClassID() + ")' class='btn-students'>Students</button>");
                out.println("<button type='button' onclick='editClass(" + studentClass.getClassID() + ")' class='btn-edit'>Edit</button>");
                out.println("<button type='button' onclick='deleteClass(" + studentClass.getClassID() + ")' class='btn-delete'>Delete</button>");
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
