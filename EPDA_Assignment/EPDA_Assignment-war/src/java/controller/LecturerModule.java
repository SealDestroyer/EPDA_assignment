package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyModule;
import model.MyModuleFacade;
import model.MyUsers;
import model.MyUsersFacade;

@WebServlet(name = "LModule", urlPatterns = {"/LModule"})
public class LecturerModule extends HttpServlet {

    @EJB
    private MyUsersFacade myUsersFacade;

    @EJB
    private MyModuleFacade myModuleFacade;

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

        String lecturerId = loginUser.getUserID();

        try {
            String action = request.getParameter("action");
            if (action == null || action.trim().isEmpty()) {
                action = "list";
            }

            // ===== LIST JSON (AJAX REAL-TIME for lecturer only) =====
            if ("listJson".equals(action)) {

                List<MyModule> moduleList = myModuleFacade.findByAssignedLecturer(lecturerId);

                // Build userNameMap (createdBy + lecturer)
                Set<String> ids = new HashSet<>();
                for (MyModule m : moduleList) {
                    if (m.getCreatedBy() != null && !m.getCreatedBy().trim().isEmpty()) {
                        ids.add(m.getCreatedBy().trim());
                    }
                    if (m.getAssignedLecturerID() != null && !m.getAssignedLecturerID().trim().isEmpty()) {
                        ids.add(m.getAssignedLecturerID().trim());
                    }
                }
                Map<String, String> userNameMap = myUsersFacade.findUserNameMapByIds(new ArrayList<>(ids));

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < moduleList.size(); i++) {
                    MyModule m = moduleList.get(i);

                    String createdByText
                            = (userNameMap.get(m.getCreatedBy()) == null ? "" : userNameMap.get(m.getCreatedBy()))
                            + " (" + m.getCreatedBy() + ")";

                    String lecturerText
                            = (userNameMap.get(m.getAssignedLecturerID()) == null ? "" : userNameMap.get(m.getAssignedLecturerID()))
                            + " (" + m.getAssignedLecturerID() + ")";

                    json.append("{")
                            .append("\"moduleID\":").append(m.getModuleID()).append(",")
                            .append("\"moduleName\":\"").append(escape(m.getModuleName())).append("\",")
                            .append("\"moduleCode\":\"").append(escape(m.getModuleCode())).append("\",")
                            .append("\"description\":\"").append(escape(m.getDescription())).append("\",")
                            .append("\"createdBy\":\"").append(escape(createdByText)).append("\",")
                            .append("\"lecturer\":\"").append(escape(lecturerText)).append("\"")
                            .append("}");

                    if (i < moduleList.size() - 1) {
                        json.append(",");
                    }
                }
                json.append("]");

                response.getWriter().write(json.toString());
                return;
            }

            // ===== LIST (assigned modules only) =====
            if ("list".equals(action)) {

                List<MyModule> moduleList = myModuleFacade.findByAssignedLecturer(lecturerId);

                // Build userNameMap (createdBy + lecturer)
                Map<String, String> userNameMap = buildUserNameMap(moduleList);
                request.setAttribute("userNameMap", userNameMap);

                if (moduleList == null || moduleList.isEmpty()) {
                    request.setAttribute("errorMsg", "No modules have been assigned to you.");
                }

                request.setAttribute("moduleList", moduleList);
                request.getRequestDispatcher("Lmodule.jsp").forward(request, response);
                return;
            }

            // ===== SEARCH (within assigned modules only) =====
            if ("search".equals(action)) {

                String keyword = request.getParameter("keyword");
                if (keyword == null || keyword.trim().isEmpty()) {
                    response.sendRedirect("LModule?action=list");
                    return;
                }

                List<MyModule> moduleList = myModuleFacade.searchModulesByLecturer(keyword, lecturerId);

                if (moduleList == null || moduleList.isEmpty()) {
                    request.setAttribute("errorMsg", "No modules found for: " + keyword);
                }

                Map<String, String> userNameMap = buildUserNameMap(moduleList);
                request.setAttribute("userNameMap", userNameMap);

                request.setAttribute("moduleList", moduleList);
                request.getRequestDispatcher("Lmodule.jsp").forward(request, response);
                return;
            }

            // fallback
            response.sendRedirect("LModule?action=list");

        } catch (Exception e) {
            request.setAttribute("moduleList", java.util.Collections.emptyList());
            request.setAttribute("errorMsg", "Unable to load modules.");
            request.getRequestDispatcher("Lmodule.jsp").forward(request, response);
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

    private Map<String, String> buildUserNameMap(List<MyModule> moduleList) {
        Set<String> ids = new HashSet<>();
        if (moduleList != null) {
            for (MyModule m : moduleList) {
                if (m.getCreatedBy() != null && !m.getCreatedBy().trim().isEmpty()) {
                    ids.add(m.getCreatedBy().trim());
                }
                if (m.getAssignedLecturerID() != null && !m.getAssignedLecturerID().trim().isEmpty()) {
                    ids.add(m.getAssignedLecturerID().trim());
                }
            }
        }
        return myUsersFacade.findUserNameMapByIds(new ArrayList<>(ids));
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
        return "Lecturer module list servlet";
    }
}
