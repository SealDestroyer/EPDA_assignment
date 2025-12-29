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
import model.MyUserIDFacade;
import model.MyUsers;
import model.MyUsersFacade;

/**
 *
 * @author bohch
 */
@WebServlet(name = "addAdmin", urlPatterns = {"/addAdmin"})
public class addAdmin extends HttpServlet {

    @EJB
    private MyUserIDFacade myUserIDFacade;

    @EJB
    private MyAdminFacade myAdminFacade;

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
                // Get parameters from JSP form
                String userID = request.getParameter("userID");
                String fullName = request.getParameter("fullName");
                String password = request.getParameter("password");
                String gender = request.getParameter("gender");
                String phone = request.getParameter("phone");
                String icNumber = request.getParameter("icNumber");
                String email = request.getParameter("email");
                String address = request.getParameter("address");

                // Validate that all required fields are not null or empty
                if (userID == null || userID.trim().isEmpty()
                        || fullName == null || fullName.trim().isEmpty()
                        || password == null || password.trim().isEmpty()
                        || gender == null || gender.trim().isEmpty()
                        || phone == null || phone.trim().isEmpty()
                        || icNumber == null || icNumber.trim().isEmpty()
                        || email == null || email.trim().isEmpty()
                        || address == null || address.trim().isEmpty()) {
                    throw new Exception("All fields are required!");
                }

                // Validate full name (minimum 3 characters, only letters and spaces)
                if (fullName.trim().length() < 3 || !fullName.trim().matches("^[a-zA-Z\\s]+$")) {
                    throw new Exception("Full name must be at least 3 characters and contain only letters!");
                }

                // Validate password (minimum 8 characters, must contain uppercase, lowercase, and digit)
                if (password.length() < 8
                        || !password.matches(".*[a-z].*")
                        || !password.matches(".*[A-Z].*")
                        || !password.matches(".*\\d.*")) {
                    throw new Exception("Password must be at least 8 characters with uppercase, lowercase, and number!");
                }

                // Validate gender (must be Male or Female)
                if (!gender.equals("Male") && !gender.equals("Female")) {
                    throw new Exception("Invalid gender selection!");
                }

                // Validate phone number (must be exactly 10 digits)
                if (!phone.matches("^[0-9]{10}$")) {
                    throw new Exception("Phone number must be exactly 10 digits!");
                }

                // Validate IC number format (12 digits, with or without hyphens)
                String icDigits = icNumber.replace("-", "");
                if (!icDigits.matches("^\\d{12}$")) {
                    throw new Exception("IC number must be 12 digits!");
                }

                // Validate IC date portion (YYMMDD)
                int month = Integer.parseInt(icDigits.substring(2, 4));
                int day = Integer.parseInt(icDigits.substring(4, 6));
                if (month < 1 || month > 12 || day < 1 || day > 31) {
                    throw new Exception("Invalid date in IC number!");
                }

                // Validate email format
                if (!email.trim().matches("^[a-zA-Z0-9._-]+@apu\\.edu\\.my$")) {
                    throw new Exception("Email must end with @apu.edu.my!");
                }

                // Validate address (minimum 10 characters)
                if (address.trim().length() < 10) {
                    throw new Exception("Address must be at least 10 characters!");
                }

                // Check if email already exists in the database
                MyUsers existingUser = myUsersFacade.findByEmail(email);
                if (existingUser != null) {
                    throw new Exception("Email already exists!");
                }

                // Create and persist new user record in MyUsers table
                MyUsers user = new MyUsers(userID, fullName, password, gender, phone, icNumber, email, address);
                myUsersFacade.create(user);

                // Create and persist new admin record in MyAdmin table
                MyAdmin admin = new MyAdmin(userID, "Admin");
                myAdminFacade.create(admin);

                // Increase the Admin ID
                myUserIDFacade.updateCurrentUserId(userID, "Admin");

                // Display success message and redirect to view admin page
                out.println("<script type='text/javascript'>");
                out.println("alert('Admin Added Successfully!');");
                out.println("window.location.href = 'viewAdmin.jsp';");
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
