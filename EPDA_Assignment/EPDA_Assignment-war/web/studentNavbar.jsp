<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    model.MyUsers u = (model.MyUsers) session.getAttribute("user");
    String userID = (String) session.getAttribute("userID");
%>

<nav class="menu">
    <a class="logo" href="studentPage.jsp">Student Dashboard</a>

    <div class="nav-links">
        <a href="manageStudentProfile.jsp?userId=<%= userID %>">Profile</a>
        <a href="viewResult.jsp">Results</a>
        <a href="Logout">Logout</a>
    </div>
</nav>
        
<script>
    window.addEventListener("scroll", function () {
        const nav = document.querySelector("nav.menu");
        if (!nav)
            return;

        if (window.scrollY > 20) {
            nav.classList.add("scrolled");
        } else {
            nav.classList.remove("scrolled");
        }
    });
</script>
