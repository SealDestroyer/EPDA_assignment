<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Academic Leader Dashboard</title>
</head>
<body>
    <h2>Academic Leader Dashboard</h2>
    <p>Welcome, <%= ((model.MyUsers)session.getAttribute("user")).getUserID() %></p>

    <hr>

    <ul>
        <li><a href="ALeditProfile.jsp">Edit Profile</a></li>
        <li><a href="ALmoduleList.jsp">Manage Modules</a></li>
        <li><a href="Logout">Logout</a></li>
    </ul>
</body>
</html>
