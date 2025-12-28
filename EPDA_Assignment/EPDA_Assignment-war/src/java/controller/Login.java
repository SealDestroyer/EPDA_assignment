package controller;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyAcademicLeaderFacade;
import model.MyLecturerFacade;
import model.MyUsers;
import model.MyUsersFacade;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    @EJB
    private MyUsersFacade myUsersFacade;

    @EJB
    private MyAcademicLeaderFacade myAcademicLeaderFacade;

    @EJB
    private MyLecturerFacade myLecturerFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // validate empty
            if (email == null || email.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {
                throw new Exception();
            }

            email = email.trim();

            // validate credentials and get user ID
            String userID = myUsersFacade.findUserIdByEmailAndPassword(email, password);
            if (userID == null) {
                throw new Exception();
            }

            HttpSession s = request.getSession(true);
            s.setAttribute("userID", userID);

            MyUsers user = myUsersFacade.find(userID);
            s.setAttribute("user", user);

            // Route based on userID content
            if (userID.toUpperCase().startsWith("AL")) {
                response.sendRedirect("ALdashboard.jsp");
            } else if (userID.toUpperCase().startsWith("AD")) {
                response.sendRedirect("viewStudent.jsp");
            } else if (userID.toUpperCase().startsWith("S")) {
                response.sendRedirect("StudentAssessmentList.jsp");
            } else if (userID.toUpperCase().startsWith("L")) {
                response.sendRedirect("Ldashboard.jsp");
            }

        } catch (Exception e) {
            request.getRequestDispatcher("login.jsp").include(request, response);
            response.getWriter().println("<br><br><br>Invalid Login Credentials!");
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
