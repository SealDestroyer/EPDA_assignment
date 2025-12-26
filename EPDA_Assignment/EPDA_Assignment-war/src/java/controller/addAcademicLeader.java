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
import model.MyAcademicLeader;
import model.MyAcademicLeaderFacade;
import static model.MyAcademicLeader_.endDate;
import static model.MyAcademicLeader_.leaderRole;
import static model.MyAcademicLeader_.startDate;
import static model.MyLecturer_.userID;
import model.MyUsers;
import model.MyUsersFacade;
import static model.MyUsers_.address;
import static model.MyUsers_.email;
import static model.MyUsers_.fullName;
import static model.MyUsers_.gender;
import static model.MyUsers_.icNumber;
import static model.MyUsers_.password;
import static model.MyUsers_.phone;

/**
 *
 * @author bohch
 */
@WebServlet(name = "addAcademicLeader", urlPatterns = {"/addAcademicLeader"})
public class addAcademicLeader extends HttpServlet {

    @EJB
    private MyUsersFacade myUsersFacade;

    @EJB
    private MyAcademicLeaderFacade myAcademicLeaderFacade;

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
            try {
                String userID = request.getParameter("userID");
                String fullName = request.getParameter("fullName");
                String password = request.getParameter("password");
                String gender = request.getParameter("gender");
                String phone = request.getParameter("phone");
                String icNumber = request.getParameter("icNumber");
                String email = request.getParameter("email");
                String address = request.getParameter("address");
                String leaderRole = request.getParameter("leaderRole");
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");

                //Create New User Record
                MyUsers user = new MyUsers(userID, fullName, password, gender, phone, icNumber, email, address);
                myUsersFacade.create(user);

                //Create New Academic Leader Record
                MyAcademicLeader academicLeader = new MyAcademicLeader(userID, leaderRole, startDate, endDate);
                myAcademicLeaderFacade.create(academicLeader);

                //request.setAttribute("message", "Academic Leader added successfully!");
                request.getRequestDispatcher("viewAcademicLeaders.jsp").forward(request, response);
                out.println("<br><br><br>Academic Leader Added Successfully!");
            } catch (Exception e) {
                request.getRequestDispatcher("addAcademicLeader.jsp").forward(request, response);
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
