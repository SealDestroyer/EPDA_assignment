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
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MyStudentClassEnrollmentFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "generateClassEnrollmentReport", urlPatterns = {"/generateClassEnrollmentReport"})
public class generateClassEnrollmentReport extends HttpServlet {

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
            List<Object[]> enrollmentData = myStudentClassEnrollmentFacade.countByClassIDAndDateRange(startTimestamp, endTimestamp);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Class Enrollment Report</title>");
            out.println("    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>");
            out.println("    <script type=\"text/javascript\">");
            out.println("      google.charts.load('current', {'packages':['corechart', 'bar']});");
            out.println("      google.charts.setOnLoadCallback(drawBasic);");
            out.println("");
            out.println("      function drawBasic() {");
            out.println("");
            boolean hasData = enrollmentData != null && !enrollmentData.isEmpty();

            out.println("        var data = new google.visualization.DataTable();");
            out.println("        data.addColumn('string', 'Class ID');");
            out.println("        data.addColumn('number', 'Enrollment Count');");
            out.println("        data.addRows([");

            // Iterate through enrollmentData to populate chart
            if (hasData) {
                for (Object[] row : enrollmentData) {
                    String classId = row[0].toString();
                    Number count = (Number) row[1];
                    out.println("          ['" + classId + "', " + count.longValue() + "],");
                }
            }

            out.println("        ]);");
            out.println("        var hasData = " + hasData + ";");
            out.println("        if (!hasData) {");
            out.println("          document.getElementById('chart_div').innerHTML = 'No enrollment data found for the selected range.';");
            out.println("          return;");
            out.println("        }");
            out.println("");
            out.println("        var options = {");
            out.println("          chartArea: {width: '50%'},");
            out.println("          hAxis: {");
            out.println("            title: 'Enrollment Count',");
            out.println("            minValue: 0");
            out.println("          },");
            out.println("          vAxis: {");
            out.println("            title: 'Class ID'");
            out.println("          }");
            out.println("        };");
            out.println("");
            out.println("        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));");
            out.println("");
            out.println("        chart.draw(data, options);");
            out.println("      }");
            out.println("    </script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>" + reportName + " (" + startDate + " to " + endDate + ")</h2>");
            out.println("<p><strong>Generated on:</strong> " + generatedDateTime + "</p>");
            out.println("<div id=\"chart_div\" style=\"width: 900px; height: 500px;\"></div>");
            out.println("</body>");
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
