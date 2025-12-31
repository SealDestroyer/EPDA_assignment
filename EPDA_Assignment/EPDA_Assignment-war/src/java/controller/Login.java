package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyAcademicLeaderFacade;
import model.MyAdmin;
import model.MyAdminFacade;
import model.MyLecturerFacade;
import model.MyUsers;
import model.MyUsersFacade;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    @EJB
    private MyAdminFacade myAdminFacade;

    @EJB
    private MyUsersFacade myUsersFacade;

    @EJB
    private MyAcademicLeaderFacade myAcademicLeaderFacade;

    @EJB
    private MyLecturerFacade myLecturerFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {
                // Retrieve form parameters from the request
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                // Validate that email and password are not empty or null
                if (email == null || email.trim().isEmpty()) {
                    throw new IllegalArgumentException("Email is required");
                }
                if (password == null || password.trim().isEmpty()) {
                    throw new IllegalArgumentException("Password is required");
                }

                email = email.trim();

                // Validate email format - must be a valid APU email address
                if (!email.matches("^[a-zA-Z0-9._-]+@apu\\.edu\\.my$")) {
                    throw new IllegalArgumentException("Invalid email format. Must be an APU email (@apu.edu.my)");
                }

                // Validate password length - minimum 6 characters for security
                if (password.length() < 6) {
                    throw new IllegalArgumentException("Password must be at least 6 characters long");
                }

                // Validate credentials against database and retrieve user ID
                String userID = myUsersFacade.findUserIdByEmailAndPassword(email, password);
                if (userID == null) {
                    throw new IllegalArgumentException("Invalid email or password");
                }

                // Create session and store user information
                HttpSession s = request.getSession(true);
                s.setAttribute("userID", userID);

                MyUsers user = myUsersFacade.find(userID);
                s.setAttribute("user", user);

                // Route user to appropriate dashboard based on their role prefix
                // AL = Academic Leader, AD = Admin, S = Student, L = Lecturer
                if (userID.toUpperCase().startsWith("AL")) {
                    response.sendRedirect("ALdashboard.jsp");
                } else if (userID.toUpperCase().startsWith("AD")) {
                    MyAdmin admin = myAdminFacade.findByUserId(userID);
                    // Route regular admins to student view, others to admin view
                    if (admin.getPositionTitle().equals("Admin")) {
                        response.sendRedirect("viewStudent.jsp");
                    } else {
                        response.sendRedirect("viewAdmin.jsp");
                    }
                } else if (userID.toUpperCase().startsWith("S")) {
                    response.sendRedirect("studentPage.jsp");
                } else if (userID.toUpperCase().startsWith("L")) {
                    response.sendRedirect("Ldashboard.jsp");
                }
            } catch (Exception e) {
                // Display error message and return to the login form for user correction
                out.println("<script type='text/javascript'>");
                out.println("alert('Error: " + e.getMessage().replace("'", "\\'") + "');");
                out.println("window.history.back();");
                out.println("</script>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
