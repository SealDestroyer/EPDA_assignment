/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "updateGrade", urlPatterns = {"/updateGrade"})
public class updateGrade extends HttpServlet {

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
            try {
                // Retrieve form parameters from the request
                String gradeId = request.getParameter("gradeId");
                String gradeLetter = request.getParameter("gradeLetter");
                String minPercentageStr = request.getParameter("minPercentage");
                String maxPercentageStr = request.getParameter("maxPercentage");
                
                // Validate that all required parameters are present
                if (gradeId == null || gradeId.trim().isEmpty()) {
                    throw new IllegalArgumentException("Grade ID is required!");
                }
                if (gradeLetter == null || gradeLetter.trim().isEmpty()) {
                    throw new IllegalArgumentException("Grade letter is required!");
                }
                if (minPercentageStr == null || minPercentageStr.trim().isEmpty()) {
                    throw new IllegalArgumentException("Minimum percentage is required!");
                }
                if (maxPercentageStr == null || maxPercentageStr.trim().isEmpty()) {
                    throw new IllegalArgumentException("Maximum percentage is required!");
                }
                
                // Validate grade letter format (A-F optionally followed by + or -)
                if (!gradeLetter.trim().matches("^[A-Fa-f][+\\-]?$")) {
                    throw new IllegalArgumentException("Grade letter must be a letter (A-F) optionally followed by + or -!");
                }
                
                // Parse percentage values to integers
                Integer minPercentage;
                Integer maxPercentage;
                try {
                    minPercentage = Integer.parseInt(minPercentageStr);
                    maxPercentage = Integer.parseInt(maxPercentageStr);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Percentages must be valid numbers!");
                }
                
                // Validate percentage ranges (must be between 0 and 100)
                if (minPercentage < 0 || minPercentage > 100) {
                    throw new IllegalArgumentException("Minimum percentage must be between 0 and 100!");
                }
                if (maxPercentage < 0 || maxPercentage > 100) {
                    throw new IllegalArgumentException("Maximum percentage must be between 0 and 100!");
                }
                
                // Validate that maximum is greater than minimum
                if (maxPercentage <= minPercentage) {
                    throw new IllegalArgumentException("Maximum percentage must be greater than minimum percentage!");
                }

                // Find and update grade record with validated data
                MyGrading grade = myGradingFacade.find(Integer.parseInt(gradeId));
                grade.setGradeLetter(gradeLetter.trim());
                grade.setMinPercentage(minPercentage);
                grade.setMaxPercentage(maxPercentage);
                myGradingFacade.edit(grade);

                // Display success message and redirect to grade list page
                out.println("<script type='text/javascript'>");
                out.println("alert('Grade Updated Successfully!');");
                out.println("window.location.href = 'viewGrade.jsp';");
                out.println("</script>");
            } catch (Exception e) {
                // Handle errors by displaying error message and returning to form
                out.println("<script type='text/javascript'>");
                out.println("alert('Error: " + e.getMessage().replace("'", "\\'") + "');");
                out.println("window.history.back();");
                out.println("</script>");
            }
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
