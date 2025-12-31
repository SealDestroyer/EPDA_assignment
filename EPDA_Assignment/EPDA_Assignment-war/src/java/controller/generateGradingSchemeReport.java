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
        // Get current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String generatedDateTime = dateFormat.format(new Date());
        
        // Fetch all grading data from database
        List<MyGrading> gradingList = myGradingFacade.findAll();

        // Build chart data as JavaScript array
        StringBuilder chartData = new StringBuilder();
        boolean hasData = false;
        
        if (gradingList != null && !gradingList.isEmpty()) {
            hasData = true;
            for (int i = 0; i < gradingList.size(); i++) {
                MyGrading grade = gradingList.get(i);
                chartData.append("['Grade ").append(grade.getGradeLetter()).append("', ")
                    .append(grade.getMinPercentage()).append(", '")
                    .append(grade.getMinPercentage()).append("%', ")
                    .append(grade.getMaxPercentage()).append(", '")
                    .append(grade.getMaxPercentage()).append("%']");
                if (i < gradingList.size() - 1) {
                    chartData.append(",\n                ");
                }
            }
        } else {
            // Provide placeholder data when no grading schemes exist
            chartData.append("['No Data', 0, '0%', 0, '0%']");
        }
        
        // Set attributes for JSP
        request.setAttribute("generatedDateTime", generatedDateTime);
        request.setAttribute("chartData", chartData.toString());
        request.setAttribute("hasData", hasData);
        
        // Forward to JSP
        request.getRequestDispatcher("gradingSchemeReport.jsp").forward(request, response);
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
