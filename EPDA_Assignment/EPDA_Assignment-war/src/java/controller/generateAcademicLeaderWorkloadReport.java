/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MyLecturerFacade;
import model.MyUsersFacade;
import model.MyUsers;

/**
 *
 * @author bohch
 */
@WebServlet(name = "generateAcademicLeaderWorkloadReport", urlPatterns = {"/generateAcademicLeaderWorkloadReport"})
public class generateAcademicLeaderWorkloadReport extends HttpServlet {

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
            // Get current date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String generatedDateTime = dateFormat.format(new Date());
            
            // Get workload data from database
            List<Object[]> workloadData = myLecturerFacade.countByAcademicLeaderID();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Academic Leader Workload Report</title>");
            out.println("<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>");
            out.println("<script type=\"text/javascript\">");
            out.println("google.charts.load('current', {packages: ['corechart', 'bar']});");
            out.println("google.charts.setOnLoadCallback(drawBasic);");
            out.println("");
            out.println("function drawBasic() {");
            out.println("  var data = new google.visualization.DataTable();");
            out.println("  data.addColumn('string', 'Academic Leader');");
            out.println("  data.addColumn('number', 'Number of Lecturers');");
            out.println("");
            out.println("  data.addRows([");
            
            // Generate data rows from database
            for (int i = 0; i < workloadData.size(); i++) {
                Object[] row = workloadData.get(i);
                String academicLeaderID = (String) row[0];
                Long count = (Long) row[1];
                
                // Get academic leader name
                String leaderName = "Unknown";
                if (academicLeaderID != null) {
                    MyUsers leader = myUsersFacade.find(academicLeaderID);
                    if (leader != null) {
                        leaderName = leader.getFullName() + " (" + academicLeaderID + ")";
                    }
                } else {
                    leaderName = "Unassigned";
                }
                
                out.print("    ['" + leaderName + "', " + count + "]");
                if (i < workloadData.size() - 1) {
                    out.println(",");
                } else {
                    out.println("");
                }
            }
            
            out.println("  ]);");
            out.println("");
            out.println("  var options = {");
            out.println("    chartArea: {width: '50%'},");
            out.println("    hAxis: {");
            out.println("      title: 'Academic Leader'");
            out.println("    },");
            out.println("    vAxis: {");
            out.println("      title: 'Number of Lecturers',");
            out.println("      minValue: 0");
            out.println("    }");
            out.println("  };");
            out.println("");
            out.println("  var chart = new google.visualization.ColumnChart(");
            out.println("    document.getElementById('chart_div'));");
            out.println("");
            out.println("  chart.draw(data, options);");
            out.println("}");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Academic Leader Workload Report</h2>");
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
