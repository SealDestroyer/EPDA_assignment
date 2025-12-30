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
import model.MyUsers;
import model.MyUsersFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "forgetPassword", urlPatterns = {"/forgetPassword"})
public class forgetPassword extends HttpServlet {

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
            try {
                // Retrieve form parameters from the request
                String email = request.getParameter("email");
                String secretKeyStr = request.getParameter("secretKey");
                String newPassword = request.getParameter("newPassword");
                
                // Validate that all required fields are present and not empty
                if (email == null || email.trim().isEmpty()) {
                    throw new IllegalArgumentException("Email is required");
                }
                if (secretKeyStr == null || secretKeyStr.trim().isEmpty()) {
                    throw new IllegalArgumentException("Secret key is required");
                }
                if (newPassword == null || newPassword.trim().isEmpty()) {
                    throw new IllegalArgumentException("New password is required");
                }
                
                // Parse and validate that the secret key is a valid integer
                Integer secretKey;
                try {
                    secretKey = Integer.parseInt(secretKeyStr.trim());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid secret key format");
                }
                
                // Find user by email and secret key
                MyUsers user = myUsersFacade.findByEmailAndSecretKey(email.trim(), secretKey);
                if (user == null) {
                    throw new IllegalArgumentException("Invalid email or secret key. Please try again");
                }
                
                // Generate a new secret key for future password resets that differs from the old one
                Integer newSecretKey;
                do {
                    newSecretKey = (int) (Math.random() * 900000) + 100000;
                } while (newSecretKey.equals(user.getSecretKey()));
                
                // Update the user's password and secret key in the database
                myUsersFacade.updatePasswordAndSecretKeyByEmail(email.trim(), newPassword.trim(), newSecretKey);
                
                // Display success message and redirect to login page after successful password reset
                out.println("<script type='text/javascript'>");
                out.println("alert('Password reset successful! Please login with your new password.');");
                out.println("window.location.href = 'login.jsp';");
                out.println("</script>");
            } catch (Exception e) {
                // Display error message and return to the form for user correction
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
