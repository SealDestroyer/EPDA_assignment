<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    model.MyUsers u = (model.MyUsers) session.getAttribute("user");
%>

<nav class="menu">
    <a class="logo" href="ALdashboard.jsp">Dashboard</a>

    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/ALProfile?action=edit">Profile</a>
        <a href="${pageContext.request.contextPath}/ALViewClass?action=list">Classes</a>
        <a href="${pageContext.request.contextPath}/Module?action=list">Modules</a>
        <a href="${pageContext.request.contextPath}/ALAnalytics">Analytics</a>
        
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
