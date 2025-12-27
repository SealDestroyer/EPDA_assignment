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
import model.MyGradingFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "addGrade", urlPatterns = {"/addGrade"})
public class addGrade extends HttpServlet {

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
        
        try {
            String gradeLetter = request.getParameter("gradeLetter");
            String minPercentageStr = request.getParameter("minPercentage");
            String maxPercentageStr = request.getParameter("maxPercentage");
            
            Integer minPercentage = Integer.parseInt(minPercentageStr);
            Integer maxPercentage = Integer.parseInt(maxPercentageStr);
            
            //Create New Grade Record
            model.MyGrading grade = new model.MyGrading(gradeLetter, minPercentage, maxPercentage);
            
            myGradingFacade.create(grade);
            
            //Redirect to viewGrade.jsp after successful addition
            response.sendRedirect("viewGrade.jsp");
        } catch (Exception e) {
            //Set error message and forward back to the form
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("addGrade.jsp").forward(request, response);
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
