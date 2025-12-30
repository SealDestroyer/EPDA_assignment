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
    
    @EJB
    private model.MyUserIDFacade myUserIDFacade;

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

                // Validate required fields are not null or empty
                if (userID == null || userID.trim().isEmpty() ||
                    fullName == null || fullName.trim().isEmpty() ||
                    password == null || password.trim().isEmpty() ||
                    gender == null || gender.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty() ||
                    icNumber == null || icNumber.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    address == null || address.trim().isEmpty() ||
                    employmentType == null || employmentType.trim().isEmpty() ||
                    academicRank == null || academicRank.trim().isEmpty()) {
                    throw new IllegalArgumentException("All fields are required!");
                }
                
                // Validate full name length (minimum 3 characters)
                if (fullName.trim().length() < 3) {
                    throw new IllegalArgumentException("Full name must be at least 3 characters long!");
                }
                
                // Validate password length (minimum 8 characters)
                if (password.length() < 8) {
                    throw new IllegalArgumentException("Password must be at least 8 characters long!");
                }
                
                // Validate phone number format (must be 10 digits)
                if (!phone.matches("^[0-9]{10}$")) {
                    throw new IllegalArgumentException("Phone number must be exactly 10 digits!");
                }
                
                // Validate IC number format (must be 12 digits, with or without hyphens)
                String icDigits = icNumber.replace("-", "");
                if (!icDigits.matches("^\\d{12}$")) {
                    throw new IllegalArgumentException("IC number must be 12 digits!");
                }
                
                // Validate email format (must be @apu.edu.my domain)
                if (!email.matches("^[a-zA-Z0-9._-]+@apu\\.edu\\.my$")) {
                    throw new IllegalArgumentException("Please enter a valid APU email address (@apu.edu.my)!");
                }
                
                // Validate address length (minimum 10 characters)
                if (address.trim().length() < 10) {
                    throw new IllegalArgumentException("Address must be at least 10 characters long!");
                }

                // Check if email already exists in the database to prevent duplicates
                MyUsers existingUser = myUsersFacade.findByEmail(email);
                if (existingUser != null) {
                    throw new IllegalArgumentException("Email already exists!");
                }
                
                // Create new user record with basic information
                MyUsers user = new MyUsers(userID, fullName, password, gender, phone, icNumber, email, address);
                myUsersFacade.create(user);

                // Create new lecturer record with lecturer-specific details
                MyLecturer lecturer = new MyLecturer(userID, employmentType, academicRank, academicLeaderID);
                myLecturerFacade.create(lecturer);

                // Update the lecturer ID counter for the next lecturer registration
                myUserIDFacade.updateCurrentUserId(userID, "Lecturer");

                // Display success message and redirect to view lecturers page
                out.println("<script type='text/javascript'>");
                out.println("alert('Lecturer Added Successfully!');");
                out.println("window.location.href = 'viewLecturers.jsp';");
                out.println("</script>");
            } catch (Exception e) {
                // Handle any errors and return to form
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
