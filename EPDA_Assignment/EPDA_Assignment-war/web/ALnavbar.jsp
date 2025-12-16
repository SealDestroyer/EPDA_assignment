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
        <a href="ALeditProfile.jsp">Profile</a>
        <a href="ALmoduleList.jsp">Modules</a>
        <a href="Logout">Logout</a>
    </div>
</nav>
