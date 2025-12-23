package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyLecturer;
import model.MyLecturerFacade;
import model.MyUsers;
import model.MyUsersFacade;

@WebServlet(name = "LProfile", urlPatterns = {"/LProfile"})
public class LProfile extends HttpServlet {

    @EJB
    private MyUsersFacade myUsersFacade;

    @EJB
    private MyLecturerFacade myLecturerFacade;

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
                action = "edit";
            }

            // ===== GO EDIT PAGE =====
            if ("edit".equals(action)) {

                String userID = loginUser.getUserID();

                MyUsers u = myUsersFacade.find(userID);
                MyLecturer l = myLecturerFacade.find(userID);

                if (u == null) {
                    response.sendRedirect("login.jsp");
                    return;
                }

                // prefill values (same naming style)
                request.setAttribute("userIDVal", u.getUserID());
                request.setAttribute("fullNameVal", safe(u.getFullName()));
                request.setAttribute("genderVal", safe(u.getGender()));
                request.setAttribute("phoneVal", safe(u.getPhone()));
                request.setAttribute("icNumberVal", safe(u.getIcNumber()));
                request.setAttribute("emailVal", safe(u.getEmail()));
                request.setAttribute("addressVal", safe(u.getAddress()));

                // lecturer readonly fields
                request.setAttribute("employmentTypeVal", (l != null) ? safe(l.getEmploymentType()) : "");
                request.setAttribute("academicRankVal", (l != null) ? safe(l.getAcademicRank()) : "");
                request.setAttribute("academicLeaderIDVal", (l != null) ? safe(l.getAcademicLeaderID()) : "");

                request.getRequestDispatcher("LeditProfile.jsp").forward(request, response);
                return;
            }

            // ===== UPDATE PROFILE =====
            if ("update".equals(action)) {

                String userID = loginUser.getUserID();

                MyUsers u = myUsersFacade.find(userID);
                MyLecturer l = myLecturerFacade.find(userID);

                if (u == null) {
                    response.sendRedirect("login.jsp");
                    return;
                }

                // read inputs
                String fullName = trim(request.getParameter("fullName"));
                String password = trim(request.getParameter("password")); // optional
                String gender = trim(request.getParameter("gender"));
                String phone = trim(request.getParameter("phone"));
                String icNumber = trim(request.getParameter("icNumber"));
                String email = trim(request.getParameter("email"));
                String address = trim(request.getParameter("address"));

                Map<String, String> errors = new HashMap<>();

                // ==== VALIDATIONS (same as AL) ====
                if (fullName.isEmpty()) {
                    errors.put("fullName", "Full Name cannot be empty.");
                } else if (fullName.length() < 2) {
                    errors.put("fullName", "Full Name must be at least 2 characters.");
                } else if (fullName.length() > 50) {
                    errors.put("fullName", "Full Name must not exceed 50 characters.");
                }

                if (gender.isEmpty()) {
                    errors.put("gender", "Please select a gender.");
                }

                if (phone.isEmpty()) {
                    errors.put("phone", "Phone cannot be empty.");
                } else if (!phone.matches("\\d+")) {
                    errors.put("phone", "Phone must contain numbers only.");
                } else if (phone.length() != 10) {
                    errors.put("phone", "Phone number must be exactly 10 digits.");
                }

                if (icNumber.isEmpty()) {
                    errors.put("icNumber", "IC Number cannot be empty.");
                } else if (!icNumber.matches("\\d+")) {
                    errors.put("icNumber", "IC Number must contain numbers only.");
                } else if (icNumber.length() != 12) {
                    errors.put("icNumber", "IC Number must be exactly 12 digits.");
                }

                if (email.isEmpty()) {
                    errors.put("email", "Email cannot be empty.");
                } else if (!email.contains("@") || !email.contains(".")) {
                    errors.put("email", "Email format is invalid.");
                }

                if (address.isEmpty()) {
                    errors.put("address", "Address cannot be empty.");
                } else if (address.length() > 200) {
                    errors.put("address", "Address must not exceed 200 characters.");
                }

                // password optional: only validate if user typed something
                if (!password.isEmpty() && password.length() < 4) {
                    errors.put("password", "Password must be at least 4 characters.");
                }

                // if got errors -> return back to LeditProfile.jsp
                if (!errors.isEmpty()) {

                    request.setAttribute("errors", errors);

                    // keep old values
                    request.setAttribute("userIDVal", userID);
                    request.setAttribute("fullNameVal", fullName);
                    request.setAttribute("genderVal", gender);
                    request.setAttribute("phoneVal", phone);
                    request.setAttribute("icNumberVal", icNumber);
                    request.setAttribute("emailVal", email);
                    request.setAttribute("addressVal", address);

                    // lecturer readonly fields
                    request.setAttribute("employmentTypeVal", (l != null) ? safe(l.getEmploymentType()) : "");
                    request.setAttribute("academicRankVal", (l != null) ? safe(l.getAcademicRank()) : "");
                    request.setAttribute("academicLeaderIDVal", (l != null) ? safe(l.getAcademicLeaderID()) : "");

                    request.getRequestDispatcher("LeditProfile.jsp").forward(request, response);
                    return;
                }

                // PASS -> update entity (MyUsers only)
                u.setFullName(fullName);
                u.setGender(gender);
                u.setPhone(phone);
                u.setIcNumber(icNumber);
                u.setEmail(email);
                u.setAddress(address);

                if (!password.isEmpty()) {
                    u.setPassword(password);
                }

                myUsersFacade.edit(u);

                // update session user so navbar/profile updates immediately
                session.setAttribute("user", u);

                response.sendRedirect("Ldashboard.jsp");
                return;
            }

            // fallback
            response.sendRedirect("LProfile?action=edit");

        } catch (Exception e) {
            response.sendRedirect("Ldashboard.jsp");
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

    private String trim(String s) {
        return (s == null) ? "" : s.trim();
    }

    private String safe(String s) {
        return (s == null) ? "" : s;
    }

    @Override
    public String getServletInfo() {
        return "Lecturer Profile servlet";
    }
}
