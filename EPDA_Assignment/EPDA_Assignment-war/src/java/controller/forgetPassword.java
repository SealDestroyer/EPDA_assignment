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
            /* TODO output your page here. You may use following sample code. */
           MyUsers user = myUsersFacade.findByEmailAndSecretKey(request.getParameter("email"), Integer.parseInt(request.getParameter("secretKey")));
           if(user != null){
                // Generate a new secret key for future password resets that differs from the old one
                Integer newSecretKey;
                do {
                    newSecretKey = (int) (Math.random() * 900000) + 100000;
                } while (newSecretKey.equals(user.getSecretKey()));
                
                myUsersFacade.updatePasswordAndSecretKeyByEmail(request.getParameter("email"), request.getParameter("newPassword"), newSecretKey); 
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Password reset successful! Please login with your new password.');");
                out.println("location='login.jsp';");
                out.println("</script>");
              } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Invalid email or secret key. Please try again.');");
                out.println("location='forgetPassword.jsp';");
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
