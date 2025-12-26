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
            /* TODO output your page here. You may use following sample code. */
            // Get parameters from JSP form
            try {
                String gradeId = request.getParameter("gradeId");
                String gradeLetter = request.getParameter("gradeLetter");
                String minPercentage = request.getParameter("minPercentage");
                String maxPercentage = request.getParameter("maxPercentage");

                //Find and Update Grade Record
                MyGrading grade = myGradingFacade.find(Integer.parseInt(gradeId));
                grade.setGradeLetter(gradeLetter);
                grade.setMinPercentage(Integer.parseInt(minPercentage));
                grade.setMaxPercentage(Integer.parseInt(maxPercentage));
                myGradingFacade.edit(grade);

                request.setAttribute("message", "Update Successfully!");
                request.getRequestDispatcher("viewGrade.jsp").forward(request, response);
                out.println("<br><br><br>Update Success!");
            } catch (Exception e) {
                request.getRequestDispatcher("viewGrade.jsp").forward(request, response);
                out.println("<br><br><br>Invalid Input!");
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
