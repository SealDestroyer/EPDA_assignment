/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MyUsersFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "generateUserSummaryReport", urlPatterns = {"/generateUserSummaryReport"})
public class generateUserSummaryReport extends HttpServlet {

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
            // Get parameters from JSP form
            String reportId = request.getParameter("reportId");
            String reportName = request.getParameter("reportName");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            // Convert string dates to Timestamp
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime startDateTime = LocalDate.parse(startDate, formatter).atStartOfDay();
            LocalDateTime endDateTime = LocalDate.parse(endDate, formatter).atTime(23, 59, 59);
            
            Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
            Timestamp endTimestamp = Timestamp.valueOf(endDateTime);

            // Find users from database
            long lecturersCount = myUsersFacade.findAllLecturersWithDateRange(startTimestamp, endTimestamp);
            long studentsCount = myUsersFacade.findAllStudentsWithDateRange(startTimestamp, endTimestamp);
            long academicsLeaderCount = myUsersFacade.findAllAcademicsLeaderWithDateRange(startTimestamp, endTimestamp);
            long adminsCount = myUsersFacade.findAllAdminsWithDateRange(startTimestamp, endTimestamp);

            out.println("<html>");
            out.println("  <head>");
            out.println("    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>");
            out.println("    <script type=\"text/javascript\">");
            out.println("      google.charts.load('current', {'packages':['corechart']});");
            out.println("      google.charts.setOnLoadCallback(drawChart);");
            out.println("");
            out.println("      function drawChart() {");
            out.println("");
            out.println("        var data = google.visualization.arrayToDataTable([");
            out.println("          ['User Role', 'Count'],");
            out.println("          ['Lecturers',     " + lecturersCount + "],");
            out.println("          ['Students',      " + studentsCount + "],");
            out.println("          ['Academic Leaders',  " + academicsLeaderCount + "],");
            out.println("          ['Admins',    " + adminsCount + "]");
            out.println("        ]);");
            out.println("");
            out.println("        var options = {");
            out.println("          title: '" + reportName + " (" + startDate + " to " + endDate + ")'");
            out.println("        };");
            out.println("");
            out.println("        var chart = new google.visualization.PieChart(document.getElementById('piechart'));");
            out.println("");
            out.println("        chart.draw(data, options);");
            out.println("      }");
            out.println("    </script>");
            out.println("  </head>");
            out.println("  <body>");
            out.println("    <div id=\"piechart\" style=\"width: 900px; height: 500px;\"></div>");
            out.println("  </body>");
            out.println("</html>");
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
