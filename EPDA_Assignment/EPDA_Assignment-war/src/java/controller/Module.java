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

@WebServlet(name = "Module", urlPatterns = {"/Module"})
public class Module extends HttpServlet {

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
            // default action = list modules
            String action = request.getParameter("action");
            if (action == null || action.trim().isEmpty()) {
                action = "list";
            }

            if ("list".equals(action)) {

                // ===== LOAD ALL MODULES =====
                List<MyModule> moduleList = myModuleFacade.getAllModules();
                // getAllModules() uses NamedQuery MyModule.findAll

                request.setAttribute("moduleList", moduleList);
                request.getRequestDispatcher("module.jsp")
                        .forward(request, response);
                return;
            }

            // fallback
            response.sendRedirect("Module?action=list");

        } catch (Exception e) {
            // ===== ERROR HANDLING =====
            request.setAttribute("moduleList", java.util.Collections.emptyList());
            request.setAttribute("errorMsg", "Unable to load modules.");
            request.getRequestDispatcher("module.jsp")
                    .forward(request, response);
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
