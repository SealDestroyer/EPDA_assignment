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
import static model.MyAcademicLeader_.userID;
import model.MyStudent;
import model.MyStudentFacade;
import static model.MyStudent_.currentLevel;
import static model.MyStudent_.intakeYear;
import static model.MyStudent_.matricNo;
import static model.MyStudent_.status;
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
@WebServlet(name = "registerNewStudent", urlPatterns = {"/registerNewStudent"})
public class registerNewStudent extends HttpServlet {

    @EJB
    private MyStudentFacade myStudentFacade;

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

            // Get parameters from JSP form
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String password = request.getParameter("password");
            String gender = request.getParameter("gender");
            String phone = request.getParameter("phone");
            String icNumber = request.getParameter("icNumber");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String matricNo = request.getParameter("matricNo");
            String intakeYear = request.getParameter("intakeYear");
            String currentLevel = request.getParameter("currentLevel");
            String status = request.getParameter("status");

            //Create New User
            MyUsers user = new MyUsers(userID, fullName, password, gender, phone, icNumber, email, address);
            myUsersFacade.create(user);

            //Create New Student
            MyStudent student = new MyStudent(userID, matricNo, intakeYear, currentLevel, status);
            myStudentFacade.create(student);

            request.setAttribute("message", "Register Successfully!");
            request.getRequestDispatcher("registerStudent.jsp").forward(request, response);

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
