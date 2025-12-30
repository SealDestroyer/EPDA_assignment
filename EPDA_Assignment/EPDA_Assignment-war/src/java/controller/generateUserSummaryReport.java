/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
        // Get current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String generatedDateTime = dateFormat.format(new Date());
        
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

        // Check if no data found
        long totalCount = lecturersCount + studentsCount + academicsLeaderCount + adminsCount;
        boolean hasData = totalCount > 0;
        
        // Build chart data as JavaScript array
        StringBuilder chartData = new StringBuilder();
        if (hasData) {
            chartData.append("['Lecturers', ").append(lecturersCount).append("],\n                ");
            chartData.append("['Students', ").append(studentsCount).append("],\n                ");
            chartData.append("['Academic Leaders', ").append(academicsLeaderCount).append("],\n                ");
            chartData.append("['Admins', ").append(adminsCount).append("]");
        }
        
        // Set attributes for JSP
        request.setAttribute("generatedDateTime", generatedDateTime);
        request.setAttribute("reportName", reportName);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("hasData", hasData);
        request.setAttribute("chartData", chartData.toString());
        
        // Forward to JSP
        request.getRequestDispatcher("userSummaryReport.jsp").forward(request, response);
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
