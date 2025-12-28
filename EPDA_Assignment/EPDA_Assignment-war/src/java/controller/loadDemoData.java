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
import model.MyAdmin;
import model.MyAdminFacade;
import model.MyStudentFacade;
import model.MyUserIDFacade;
import model.MyUsers;
import model.MyUsersFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "loadDemoData", urlPatterns = {"/loadDemoData"})
public class loadDemoData extends HttpServlet {

    @EJB
    private MyStudentFacade myStudentFacade;

    @EJB
    private MyAdminFacade myAdminFacade;

    @EJB
    private MyUserIDFacade myUserIDFacade;

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

            //Create a superadmin demo data
            MyAdmin admin = new MyAdmin("AL1", "SuperAdmin");
            MyUsers user = new MyUsers("AL1", "Arlen", "12345", "Male",
                    "0162242788", "020401101728", "arlen@gmail.com", "Bukit Jalil");

            //Create 2 normal admin data
            MyAdmin admin2 = new MyAdmin("AL2", "Admin");
            MyUsers user2 = new MyUsers("AL2", "Jason Lim", "12345", "Male",
                    "0178899001", "990312145678", "jason.lim@gmail.com", "Cheras");

            MyAdmin admin3 = new MyAdmin("AL3", "Admin");
            MyUsers user3 = new MyUsers("AL3", "Aina Syafiqah", "12345", "Female",
                    "0123456789", "010820085432", "aina.syafiqah@gmail.com", "Shah Alam");

            // Persist the entities using facades
            myUsersFacade.create(user);
            myAdminFacade.create(admin);

            myUsersFacade.create(user2);
            myAdminFacade.create(admin2);

            myUsersFacade.create(user3);
            myAdminFacade.create(admin3);

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
