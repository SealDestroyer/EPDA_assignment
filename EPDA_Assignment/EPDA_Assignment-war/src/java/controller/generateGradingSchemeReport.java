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
import model.MyGrading;
import model.MyGradingFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "generateGradingSchemeReport", urlPatterns = {"/generateGradingSchemeReport"})
public class generateGradingSchemeReport extends HttpServlet {

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
            // Get current date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String generatedDateTime = dateFormat.format(new Date());
            
            // Fetch all grading data from database
            List<MyGrading> gradingList = myGradingFacade.findAll();

            out.println("<html>");
            out.println("  <head>");
            out.println("    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>");
            out.println("    <script type=\"text/javascript\">");
            out.println("      google.charts.load('current', {'packages':['corechart', 'bar']});");
            out.println("      google.charts.setOnLoadCallback(drawChart);");
            out.println("");
            out.println("      function drawChart() {");
            out.println("");
            out.println("        var data = google.visualization.arrayToDataTable([");
            out.println("          ['Grade', 'Min Percentage', { role: 'annotation' }, 'Max Percentage', { role: 'annotation' }],");
            
            // Add data points
            if (gradingList != null && !gradingList.isEmpty()) {
                for (int i = 0; i < gradingList.size(); i++) {
                    MyGrading grade = gradingList.get(i);
                    out.print("          ['Grade " + grade.getGradeLetter() + "', " 
                        + grade.getMinPercentage() + ", '" 
                        + grade.getMinPercentage() + "%', " 
                        + grade.getMaxPercentage() + ", '" 
                        + grade.getMaxPercentage() + "%']");
                    if (i < gradingList.size() - 1) {
                        out.println(",");
                    } else {
                        out.println();
                    }
                }
            }
            
            out.println("        ]);");
            out.println("");
            out.println("        var options = {");
            out.println("          title: 'Grading Scheme - Percentage Ranges'");
            out.println("        };");
            out.println("");
            out.println("        var chart = new google.visualization.BarChart(document.getElementById('barchart'));");
            out.println("");
            out.println("        chart.draw(data, options);");
            out.println("      }");
            out.println("    </script>");
            out.println("  </head>");
            out.println("  <body>");
            out.println("    <h2>Grading Scheme Report</h2>");
            out.println("    <p><strong>Generated on:</strong> " + generatedDateTime + "</p>");
            out.println("    <div id=\"barchart\" style=\"width: 900px; height: 500px;\"></div>");
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
