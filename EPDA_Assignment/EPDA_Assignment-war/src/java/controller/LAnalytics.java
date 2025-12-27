package controller;

import java.io.IOException;
import java.util.*;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.*;

@WebServlet(name = "LAnalytics", urlPatterns = {"/LAnalytics"})
public class LAnalytics extends HttpServlet {

    @EJB
    private MyModuleFacade myModuleFacade;

    @EJB
    private MyStudentAssessmentFacade myStudentAssessmentFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            request.getRequestDispatcher("Lanalytics.jsp").forward(request, response);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String lecturerID = loginUser.getUserID();

        // =====  module list (ONLY for this lecturer) =====
        if ("modules".equals(action)) {
            List<MyModule> modules = myModuleFacade.findByLecturerID(lecturerID);

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < modules.size(); i++) {
                MyModule m = modules.get(i);

                json.append("{")
                        .append("\"id\":").append(m.getModuleID()).append(",")
                        .append("\"code\":\"").append(escape(m.getModuleCode())).append("\",")
                        .append("\"name\":\"").append(escape(m.getModuleName())).append("\"")
                        .append("}");

                if (i < modules.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");
            response.getWriter().write(json.toString());
            return;
        }

        // ===== Chart data (ONLY if module belongs to this lecturer) =====
        if ("avgChart".equals(action)) {
            String moduleIDStr = request.getParameter("moduleID");
            Integer moduleID;

            try {
                moduleID = Integer.parseInt(moduleIDStr);
            } catch (Exception e) {
                response.getWriter().write("[]");
                return;
            }

            // make sure this lecturer owns this module
            boolean owned = myModuleFacade.isModuleOwnedByLecturer(moduleID, lecturerID);
            if (!owned) {
                response.getWriter().write("[]");
                return;
            }

            List<Object[]> rows = myStudentAssessmentFacade.avgMarkByAssessmentName(moduleID);

            StringBuilder json = new StringBuilder("[");
            json.append("[\"Assessment\",\"Average Mark\"]");

            for (Object[] r : rows) {
                String assessmentName = (String) r[0];
                Double avg = (r[1] == null) ? null : ((Number) r[1]).doubleValue();
                double value = (avg == null) ? 0.0 : avg;

                json.append(",")
                        .append("[\"").append(escape(assessmentName)).append("\",")
                        .append(String.format(java.util.Locale.US, "%.2f", value))
                        .append("]");
            }

            json.append("]");
            response.getWriter().write(json.toString());
            return;
        }

        response.getWriter().write("[]");
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
}
