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
import model.MyUsers;
import model.MyUsersFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "updateAcademicLeader", urlPatterns = {"/updateAcademicLeader"})
public class updateAcademicLeader extends HttpServlet {

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
            try {
                // Retrieve form parameters from request
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

                // Validate required fields are not null or empty
                if (userID == null || userID.trim().isEmpty() ||
                    fullName == null || fullName.trim().isEmpty() ||
                    password == null || password.trim().isEmpty() ||
                    gender == null || gender.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty() ||
                    icNumber == null || icNumber.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    address == null || address.trim().isEmpty() ||
                    leaderRole == null || leaderRole.trim().isEmpty() ||
                    startDate == null || startDate.trim().isEmpty()) {
                    throw new IllegalArgumentException("All required fields must be filled!");
                }

                // Validate full name (minimum 3 characters, only letters and spaces)
                if (fullName.trim().length() < 3 || !fullName.trim().matches("^[a-zA-Z\\s]+$")) {
                    throw new IllegalArgumentException("Full name must be at least 3 characters and contain only letters!");
                }

                // Validate password strength (min 8 chars, must contain uppercase, lowercase, and digit)
                if (password.length() < 8
                        || !password.matches(".*[a-z].*")
                        || !password.matches(".*[A-Z].*")
                        || !password.matches(".*\\d.*")) {
                    throw new IllegalArgumentException("Password must be at least 8 characters with uppercase, lowercase, and number!");
                }

                // Validate phone number (must be exactly 10 digits)
                if (!phone.matches("^[0-9]{10}$")) {
                    throw new IllegalArgumentException("Phone number must be exactly 10 digits!");
                }

                // Validate IC number format (12 digits, Malaysian format)
                String icDigits = icNumber.replace("-", "");
                if (!icDigits.matches("^\\d{12}$")) {
                    throw new IllegalArgumentException("IC number must be 12 digits!");
                }

                // Validate IC date portion (YYMMDD)
                int month = Integer.parseInt(icDigits.substring(2, 4));
                int day = Integer.parseInt(icDigits.substring(4, 6));
                if (month < 1 || month > 12 || day < 1 || day > 31) {
                    throw new IllegalArgumentException("Invalid date in IC number!");
                }

                // Validate email format (must be APU email)
                if (!email.trim().matches("^[a-zA-Z0-9._-]+@apu\\.edu\\.my$")) {
                    throw new IllegalArgumentException("Email must end with @apu.edu.my!");
                }

                // Validate address (minimum 10 characters)
                if (address.trim().length() < 10) {
                    throw new IllegalArgumentException("Address must be at least 10 characters!");
                }

                // Validate end date is after start date (if end date is provided)
                if (endDate != null && !endDate.trim().isEmpty()) {
                    if (endDate.compareTo(startDate) <= 0) {
                        throw new IllegalArgumentException("End date must be after start date!");
                    }
                }

                // Check if email already exists for a different user
                MyUsers existingUser = myUsersFacade.findByEmail(email);
                if (existingUser != null && !existingUser.getUserID().equals(userID)) {
                    throw new IllegalArgumentException("Email already exists for another user!");
                }

                // Find and update user record with validated data
                MyUsers user = myUsersFacade.find(userID);
                user.setFullName(fullName);
                user.setPassword(password);
                user.setGender(gender);
                user.setPhone(phone);
                user.setIcNumber(icNumber);
                user.setEmail(email);
                user.setAddress(address);
                myUsersFacade.edit(user);

                // Find and update academic leader record
                MyAcademicLeader academicLeader = myAcademicLeaderFacade.find(userID);
                academicLeader.setLeaderRole(leaderRole);
                academicLeader.setStartDate(startDate);
                academicLeader.setEndDate(endDate);
                myAcademicLeaderFacade.edit(academicLeader);

                // Display success message and redirect to view page
                out.println("<script type='text/javascript'>");
                out.println("alert('Academic Leader Updated Successfully!');");
                out.println("window.location.href = 'viewAcademicLeaders.jsp';");
                out.println("</script>");
            } catch (Exception e) {
                // Handle any unexpected errors and return to form
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
