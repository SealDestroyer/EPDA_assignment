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
import model.MyGrading;
import model.MyGradingFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "viewGrade", urlPatterns = {"/viewGrade"})
public class viewGrade extends HttpServlet {

    @EJB
    private MyGradingFacade myGradingFacade;

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
            
            // Include CSS and JavaScript
            out.println("<link rel='stylesheet' type='text/css' href='css/viewGrade.css'>");
            out.println("<script src='js/viewGrade.js'></script>");
            
            // Get search query parameter
            String searchQuery = request.getParameter("search");
            if (searchQuery == null) {
                searchQuery = "";
            }
            searchQuery = searchQuery.trim().toLowerCase();

            //Retrieve List of Grading Records
            List<MyGrading> gradingList = myGradingFacade.findAll();
            
            // Add search bar and Add New Grade button
            out.println("<div class='search-container'>");
            out.println("<form method='GET' action='viewGrade' class='grade-search-form'>");
            out.println("<input type='text' name='search' id='searchInput' placeholder='Search by grade letter...' ");
            out.println("value='" + (request.getParameter("search") != null ? request.getParameter("search") : "") + "' ");
            out.println("class='grade-search-input' />");
            out.println("<button type='submit' class='btn-search'>Search</button>");
            out.println("<button type='button' onclick='clearSearch()' class='btn-clear'>Clear</button>");
            out.println("</form>");
            out.println("<button type='button' onclick='addNewGrade()' class='btn-add'>Add New Grade</button>");
            out.println("</div>");

            // Display every grading from gradingList in table form
            out.println("<table class='grade-table'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Grading ID</th>");
            out.println("<th>Grade Letter</th>");
            out.println("<th>Min Percentage</th>");
            out.println("<th>Max Percentage</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            for (MyGrading grading : gradingList) {
                // Apply search filter
                if (!searchQuery.isEmpty()) {
                    String gradeLetter = grading.getGradeLetter().toLowerCase();
                    
                    // Skip this record if it doesn't match the search query
                    if (!gradeLetter.contains(searchQuery)) {
                        continue;
                    }
                }
                
                out.println("<tr>");
                out.println("<td>" + grading.getGradingID() + "</td>");
                out.println("<td>" + grading.getGradeLetter() + "</td>");
                out.println("<td>" + grading.getMinPercentage() + "%</td>");
                out.println("<td>" + grading.getMaxPercentage() + "%</td>");
                out.println("<td>");
                out.println("<button type='button' onclick='editGrade(" + grading.getGradingID() + ")' class='btn-edit'>Edit</button>");
                out.println("<button type='button' onclick='deleteGrade(" + grading.getGradingID() + ")' class='btn-delete'>Delete</button>");
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
