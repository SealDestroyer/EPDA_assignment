package controller;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyGrading;
import model.MyGradingFacade;
import model.MyUsers;

@WebServlet(name = "LGrading", urlPatterns = {"/LGrading"})
public class LGrading extends HttpServlet {

    @EJB
    private MyGradingFacade myGradingFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== LOGIN VALIDATION =====
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ===== LECTURER ONLY VALIDATION =====
        MyUsers loginUser = (MyUsers) session.getAttribute("user");
        if (!isLecturer(loginUser)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            String action = request.getParameter("action");
            if (action == null || action.trim().isEmpty()) {
                action = "list";
            }

            // ===== LIST JSON (AJAX) =====
            if ("listJson".equals(action)) {

                List<MyGrading> list = myGradingFacade.findAll();

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < list.size(); i++) {
                    MyGrading g = list.get(i);

                    json.append("{")
                            .append("\"gradeLetter\":\"").append(escape(g.getGradeLetter())).append("\",")
                            .append("\"minPercentage\":").append(g.getMinPercentage() == null ? 0 : g.getMinPercentage()).append(",")
                            .append("\"maxPercentage\":").append(g.getMaxPercentage() == null ? 0 : g.getMaxPercentage())
                            .append("}");

                    if (i < list.size() - 1) {
                        json.append(",");
                    }
                }
                json.append("]");

                response.getWriter().write(json.toString());
                return;
            }

            // ===== LIST (NORMAL PAGE) =====
            if ("list".equals(action)) {

                List<MyGrading> list = myGradingFacade.findAll();
                request.setAttribute("gradingList", list);

                request.getRequestDispatcher("Lgrading.jsp").forward(request, response);
                return;
            }

            response.sendRedirect("LGrading?action=list");

        } catch (Exception e) {
            request.setAttribute("gradingList", java.util.Collections.emptyList());
            request.setAttribute("errorMsg", "Unable to load grading data.");
            request.getRequestDispatcher("Lgrading.jsp").forward(request, response);
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

    private boolean isLecturer(MyUsers user) {
        return user != null
                && user.getUserID() != null
                && user.getUserID().toUpperCase().startsWith("L");
    }

    private String escape(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "")
                .replace("\r", "");
    }

    @Override
    public String getServletInfo() {
        return "Lecturer grading view servlet";
    }
}
