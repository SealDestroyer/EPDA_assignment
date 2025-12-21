<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    model.MyUsers u = (model.MyUsers) session.getAttribute("user");
%>

<nav class="menu">
    <a class="logo" href="Ldashboard.jsp">Dashboard</a>

    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/LProfile?action=edit">Profile</a>
        <a href="${pageContext.request.contextPath}/Assessment?action=list">Assessments</a>
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
