<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    model.MyUsers u = (model.MyUsers) session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Academic Leader Dashboard</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Your dashboard CSS -->
        <link rel="stylesheet" href="css/al-dashboard.css">
    </head>

    <body>

        <!-- Top Menu (same style like your homepage) -->
        <nav class="menu">
            <a class="logo" href="ALdashboard.jsp">DashBoard</a>

            <div class="nav-links">
                <a href="ALeditProfile.jsp">Profile</a>
                <a href="ALmoduleList.jsp">Modules</a>
                <a href="Logout">Logout</a>
            </div>
        </nav>

        <!-- Center Content -->
        <div class="center-content">
<!--            <h1>Welcome <%= u.getFullName()%>!</h1>

            <p>
                Create new modules now !
            </p>-->

            <div class="btn-container">
                <!-- change this link to your actual create module page if different -->
                <a class="btn" href="ALmoduleCreate.jsp">Create New Module</a>
            </div>
        </div>

    </body>
</html>
