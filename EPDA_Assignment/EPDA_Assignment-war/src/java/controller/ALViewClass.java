package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyStudentClass;
import model.MyStudentClassFacade;
import model.MyUsers;

@WebServlet(name = "ALViewClass", urlPatterns = {"/ALViewClass"})
public class ALViewClass extends HttpServlet {

    @EJB
    private MyStudentClassFacade myStudentClassFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== LOGIN VALIDATION =====
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ===== AL ONLY VALIDATION =====
        MyUsers loginUser = (MyUsers) session.getAttribute("user");
        if (!isAcademicLeader(loginUser)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            String action = request.getParameter("action");
            if (action == null || action.trim().isEmpty()) {
                action = "list";
            }

            String alID = loginUser.getUserID();

            // ===== LIST =====
            if ("list".equals(action)) {
                List<MyStudentClass> classList = myStudentClassFacade.findByAssignedAcademicLeaderID(alID);

                if (classList == null || classList.isEmpty()) {
                    request.setAttribute("errorMsg", "No classes assigned to you yet.");
                }

                request.setAttribute("classList", classList);
                request.getRequestDispatcher("ALviewClass.jsp").forward(request, response);
                return;
            }

            // ===== SEARCH =====
            if ("search".equals(action)) {
                String keyword = request.getParameter("keyword");
                if (keyword == null || keyword.trim().isEmpty()) {
                    response.sendRedirect("ALViewClass?action=list");
                    return;
                }

                List<MyStudentClass> classList = myStudentClassFacade.searchByClassNameForAL(keyword, alID);

                if (classList == null || classList.isEmpty()) {
                    request.setAttribute("errorMsg", "No classes found for: " + keyword);
                }

                request.setAttribute("classList", classList);
                request.getRequestDispatcher("ALviewClass.jsp").forward(request, response);
                return;
            }

            // fallback
            response.sendRedirect("ALViewClass?action=list");

        } catch (Exception e) {
            request.setAttribute("classList", java.util.Collections.emptyList());
            request.setAttribute("errorMsg", "Unable to load classes.");
            request.getRequestDispatcher("ALviewClass.jsp").forward(request, response);
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

    private boolean isAcademicLeader(MyUsers user) {
        return user != null
                && user.getUserID() != null
                && user.getUserID().toUpperCase().startsWith("AL");
    }

    @Override
    public String getServletInfo() {
        return "AL view class servlet";
    }
}
