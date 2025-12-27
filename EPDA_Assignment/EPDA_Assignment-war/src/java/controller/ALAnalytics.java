package controller;

import java.io.IOException;
import java.util.*;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.*;

@WebServlet(name = "ALAnalytics", urlPatterns = {"/ALAnalytics"})
public class ALAnalytics extends HttpServlet {

    @EJB
    private MyModuleFacade myModuleFacade;

    @EJB
    private MyUsersFacade myUsersFacade;

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

        // ===== AL ONLY VALIDATION =====
        MyUsers loginUser = (MyUsers) session.getAttribute("user");
        if (!isAcademicLeader(loginUser)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            // open page
            request.getRequestDispatcher("ALanalytics.jsp").forward(request, response);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String alID = loginUser.getUserID();

        // ===== lecturers list =====
        if ("lecturers".equals(action)) {
            List<String> lecturerIds = myModuleFacade.findDistinctLecturerIdsByAL(alID);

            Map<String, String> nameMap = myUsersFacade.findUserNameMapByIds(new ArrayList<>(lecturerIds));

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lecturerIds.size(); i++) {
                String id = lecturerIds.get(i);
                String name = nameMap.get(id);
                if (name == null) {
                    name = id;
                }

                json.append("{")
                        .append("\"id\":\"").append(escape(id)).append("\",")
                        .append("\"name\":\"").append(escape(name)).append("\"")
                        .append("}");

                if (i < lecturerIds.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");
            response.getWriter().write(json.toString());
            return;
        }

        // ===== modules list for selected lecturer =====
        if ("modules".equals(action)) {
            String lecturerID = request.getParameter("lecturerID");

            List<MyModule> modules = myModuleFacade.findByALAndLecturer(alID, lecturerID);

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

        // ===== chart data for selected module =====
        if ("avgChart".equals(action)) {
            String moduleIDStr = request.getParameter("moduleID");
            Integer moduleID = null;
            try {
                moduleID = Integer.parseInt(moduleIDStr);
            } catch (Exception e) {
                response.getWriter().write("[]");
                return;
            }

            List<Object[]> rows = myStudentAssessmentFacade.avgMarkByAssessmentName(moduleID);

            // return in Google Charts format
            StringBuilder json = new StringBuilder("[");
            json.append("[\"Assessment\",\"Average Mark\"]");

            for (Object[] r : rows) {
                String assessmentName = (String) r[0];
                Double avg = (r[1] == null) ? null : ((Number) r[1]).doubleValue();

                // if no marks yet, wil show 0
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

        // fallback
        response.getWriter().write("[]");
    }

    private boolean isAcademicLeader(MyUsers user) {
        return user != null
                && user.getUserID() != null
                && user.getUserID().toUpperCase().startsWith("AL");
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
