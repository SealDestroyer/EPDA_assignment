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
import model.MyStudentClass;
import model.MyStudentClassFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "generateClassModuleReport", urlPatterns = {"/generateClassModuleReport"})
public class generateClassModuleReport extends HttpServlet {

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
           // Get current date and time
           SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
           String generatedDateTime = dateFormat.format(new Date());
           
           List<MyStudentClass> studentClassList=myStudentClassFacade.findAll();
           
           out.println("<!DOCTYPE html>");
           out.println("<html>");
           out.println("<head>");
           out.println("<title>Class Module Report</title>");
           out.println("<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>");
           out.println("<script type='text/javascript'>");
           out.println("google.charts.load('current', {packages: ['corechart', 'bar']});");
           out.println("google.charts.setOnLoadCallback(drawBasic);");
           out.println("");
           out.println("function drawBasic() {");
           out.println("  var data = google.visualization.arrayToDataTable([");
           out.println("    ['Class', 'Module Count'],");
           
           // Go through every class and count how many modules each class has
           for(MyStudentClass studentClass : studentClassList) {
               int moduleCount = 0;
               if(studentClass.getModules() != null) {
                   moduleCount = studentClass.getModules().size();
               }
               out.println("    ['" + studentClass.getClassName() + "', " + moduleCount + "],");
           }
           
           out.println("  ]);");
           out.println("");
           out.println("  var options = {");
           out.println("    title: 'Module Count by Class',");
           out.println("    chartArea: {width: '50%'},");
           out.println("    hAxis: {");
           out.println("      title: 'Number of Modules',");
           out.println("      minValue: 0");
           out.println("    },");
           out.println("    vAxis: {");
           out.println("      title: 'Class'");
           out.println("    }");
           out.println("  };");
           out.println("");
           out.println("  var chart = new google.visualization.BarChart(document.getElementById('chart_div'));");
           out.println("  chart.draw(data, options);");
           out.println("}");
           out.println("</script>");
           out.println("</head>");
           out.println("<body>");
           out.println("<h1>Class Module Report</h1>");
           out.println("<p><strong>Generated on:</strong> " + generatedDateTime + "</p>");
           out.println("<div id='chart_div' style='width: 900px; height: 500px;'></div>");
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
