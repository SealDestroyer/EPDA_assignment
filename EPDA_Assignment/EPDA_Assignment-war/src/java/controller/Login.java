/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyAcademicLeaderFacade;
import model.MyUsers;
import model.MyUsersFacade;

/**
 *
 * @author User
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    @EJB
    private MyUsersFacade myUsersFacade;

    @EJB
    private MyAcademicLeaderFacade myAcademicLeaderFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");

            // validate empty
            if (userID == null || userID.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {
                throw new Exception();
            }

            userID = userID.trim();

            // find user
            MyUsers found = myUsersFacade.find(userID);
            if (found == null) {
                throw new Exception();
            }

            // check password
            if (!password.equals(found.getPassword())) {
                throw new Exception();
            }

            // check Academic Leader table
            if (myAcademicLeaderFacade.find(userID) == null) {
                throw new Exception();
            }

            HttpSession s = request.getSession(true);
            s.setAttribute("user", found);
            s.setAttribute("userID", found.getUserID()); // optional but useful

            response.sendRedirect("ALdashboard.jsp");

        } catch (Exception e) {

            request.getRequestDispatcher("login.jsp").include(request, response);
            response.getWriter().println("<br><br><br>Invalid input!");
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
