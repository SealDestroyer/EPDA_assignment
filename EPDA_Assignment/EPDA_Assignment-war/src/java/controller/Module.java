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
                String createdByID = loginUser.getUserID(); // save FK in MyModule.createdBy

                String moduleName = request.getParameter("moduleName");
                String moduleCode = request.getParameter("moduleCode");
                String description = request.getParameter("description");
                String assignedLecturerID = request.getParameter("assignedLecturerID");

                // temp auto id (we will improve later to M001/M002)
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
