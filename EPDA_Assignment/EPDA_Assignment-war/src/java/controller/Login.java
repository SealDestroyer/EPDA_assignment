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
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");

            // validate empty
            if (userID == null || userID.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {
                throw new Exception();
            }

            userID = userID.trim();

            // find user
            MyUsers found = myUsersFacade.find(userID);
            if (found == null) {
                throw new Exception();
            }

            // check password
            if (!password.equals(found.getPassword())) {
                throw new Exception();
            }

            boolean isAL = (myAcademicLeaderFacade.find(userID) != null);
            boolean isLecturer = (myLecturerFacade.find(userID) != null);

            if (!isAL && !isLecturer) {
                // not an AL or Lecturer
                throw new Exception();
            }

            HttpSession s = request.getSession(true);
            s.setAttribute("user", found);
            s.setAttribute("userID", found.getUserID());

            if (isAL) {
                response.sendRedirect("ALdashboard.jsp");
            } else {
                response.sendRedirect("Ldashboard.jsp");
            }

        } catch (Exception e) {
            request.getRequestDispatcher("login.jsp").include(request, response);
            response.getWriter().println("<br><br><br>Invalid input!");
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
