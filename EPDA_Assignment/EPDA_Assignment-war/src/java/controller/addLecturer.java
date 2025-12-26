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
import model.MyLecturer;
import model.MyLecturerFacade;
import static model.MyLecturer_.academicLeaderID;
import static model.MyLecturer_.academicRank;
import static model.MyLecturer_.employmentType;
import model.MyUsers;
import model.MyUsersFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "addLecturer", urlPatterns = {"/addLecturer"})
public class addLecturer extends HttpServlet {

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
                String employmentType = request.getParameter("employmentType");
                String academicRank = request.getParameter("academicRank");
                String academicLeaderID = request.getParameter("academicLeaderID");

                //Create New User Record
                MyUsers user = new MyUsers(userID, fullName, password, gender, phone, icNumber, email, address);
                myUsersFacade.create(user);

                //Create New Lecturer Record
                MyLecturer lecturer = new MyLecturer(userID, employmentType, academicRank, academicLeaderID);
                myLecturerFacade.create(lecturer);

                //request.setAttribute("message", "Lecturer added successfully!");
                request.getRequestDispatcher("viewLecturers.jsp").forward(request, response);
                out.println("<br><br><br>Lecturer Added Successfully!");
            } catch (Exception e) {
                request.getRequestDispatcher("addLecturer.jsp").forward(request, response);
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
