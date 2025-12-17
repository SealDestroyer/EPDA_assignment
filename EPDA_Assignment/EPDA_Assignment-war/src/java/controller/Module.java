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
import model.MyModule;
import model.MyModuleFacade;
import model.MyUsers;
import model.MyUsersFacade;
import java.util.Map;
import java.util.HashMap;

@WebServlet(name = "Module", urlPatterns = {"/Module"})
public class Module extends HttpServlet {

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

        try {
            String action = request.getParameter("action");
            if (action == null || action.trim().isEmpty()) {
                action = "list";
            }

            // ===== LIST =====
            if ("list".equals(action)) {

                List<MyModule> moduleList = myModuleFacade.getAllModules();
                request.setAttribute("moduleList", moduleList);
                request.getRequestDispatcher("module.jsp").forward(request, response);
                return;
            }

            // ===== SEARCH =====
            if ("search".equals(action)) {

                String keyword = request.getParameter("keyword");

                // if empty keyword -> show all modules (more user-friendly)
                if (keyword == null || keyword.trim().isEmpty()) {
                    response.sendRedirect("Module?action=list");
                    return;
                }

                List<MyModule> moduleList = myModuleFacade.searchModules(keyword);

                // optional msg if no result
                if (moduleList.isEmpty()) {
                    request.setAttribute("errorMsg", "No modules found for: " + keyword);
                }

                request.setAttribute("moduleList", moduleList);
                request.getRequestDispatcher("module.jsp").forward(request, response);
                return;
            }

            // ===== GO ADD PAGE =====
            if ("goAdd".equals(action)) {

                // get logged in Academic Leader from session
                MyUsers loginUser = (MyUsers) session.getAttribute("user");

                // show full name on UI
                request.setAttribute("createdByName", loginUser.getFullName());
                request.setAttribute("createdByID", loginUser.getUserID()); // optional if needed

                // lecturer dropdown list
                List<MyUsers> lecturerList = myUsersFacade.findLecturers();
                request.setAttribute("lecturerList", lecturerList);

                request.getRequestDispatcher("addmodule.jsp").forward(request, response);
                return;
            }

            // ===== ADD MODULE =====
            if ("add".equals(action)) {

                MyUsers loginUser = (MyUsers) session.getAttribute("user");
                String createdByID = loginUser.getUserID();

                String moduleName = request.getParameter("moduleName");
                String moduleCode = request.getParameter("moduleCode");
                String description = request.getParameter("description");
                String assignedLecturerID = request.getParameter("assignedLecturerID");

                moduleName = (moduleName == null) ? "" : moduleName.trim();
                moduleCode = (moduleCode == null) ? "" : moduleCode.trim();
                description = (description == null) ? "" : description.trim();
                assignedLecturerID = (assignedLecturerID == null) ? "" : assignedLecturerID.trim();

                Map<String, String> errors = new HashMap<>();

                // Module Name
                if (moduleName.isEmpty()) {
                    errors.put("moduleName", "Module Name cannot be empty.");
                } else if (moduleName.length() < 2) {
                    errors.put("moduleName", "Module Name must be at least 2 characters.");
                } else if (moduleName.length() > 50) {
                    errors.put("moduleName", "Module Name must not exceed 50 characters.");
                }

                // Module Code
                if (moduleCode.isEmpty()) {
                    errors.put("moduleCode", "Module Code cannot be empty.");
                } else if (moduleCode.length() < 2) {
                    errors.put("moduleCode", "Module Code must be at least 2 characters.");
                } else if (moduleCode.length() > 10) {
                    errors.put("moduleCode", "Module Code must not exceed 10 characters.");
                } else if (myModuleFacade.existsModuleCode(moduleCode)) {
                    errors.put("moduleCode", "Module Code already exists.");
                }

                // Description
                if (description.isEmpty()) {
                    errors.put("description", "Description cannot be empty.");
                } else if (description.length() > 100) {
                    errors.put("description", "Description must not exceed 100 characters.");
                }

                // Lecturer
                if (assignedLecturerID.isEmpty()) {
                    errors.put("assignedLecturerID", "Please select a lecturer.");
                }

                // if got errors -> return back to addmodule.jsp
                if (!errors.isEmpty()) {

                    request.setAttribute("errors", errors);

                    // keep old values
                    request.setAttribute("moduleNameVal", moduleName);
                    request.setAttribute("moduleCodeVal", moduleCode);
                    request.setAttribute("descriptionVal", description);
                    request.setAttribute("assignedLecturerIDVal", assignedLecturerID);

                    // reload page data
                    request.setAttribute("createdByName", loginUser.getFullName());
                    request.setAttribute("createdByID", createdByID);

                    List<MyUsers> lecturerList = myUsersFacade.findLecturers();
                    request.setAttribute("lecturerList", lecturerList);

                    request.getRequestDispatcher("addmodule.jsp").forward(request, response);
                    return;
                }

                // PASS -> create module
                String moduleID = "M" + System.currentTimeMillis();
                MyModule m = new MyModule(moduleID, moduleName, moduleCode, description, createdByID, assignedLecturerID);
                myModuleFacade.create(m);

                response.sendRedirect("Module?action=list");
                return;
            }

            // fallback
            response.sendRedirect("Module?action=list");

        } catch (Exception e) {
            request.setAttribute("moduleList", java.util.Collections.emptyList());
            request.setAttribute("errorMsg", "Unable to load modules.");
            request.getRequestDispatcher("module.jsp").forward(request, response);
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
        return "Module management servlet";
    }
}
