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
        // Get current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String generatedDateTime = dateFormat.format(new Date());
        
        // Get workload data from database
        List<Object[]> workloadData = myLecturerFacade.countByAcademicLeaderID();
        
        // Build chart data as JavaScript array
        StringBuilder chartData = new StringBuilder();
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
            
            chartData.append("['").append(leaderName).append("', ").append(count).append("]");
            if (i < workloadData.size() - 1) {
                chartData.append(",\n                ");
            }
        }
        
        // Set attributes for JSP
        request.setAttribute("generatedDateTime", generatedDateTime);
        request.setAttribute("chartData", chartData.toString());
        
        // Forward to JSP
        request.getRequestDispatcher("academicLeaderWorkloadReport.jsp").forward(request, response);
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
