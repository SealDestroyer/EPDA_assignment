<%-- 
    Document   : studentPage
    Created on : Dec 30, 2025, 10:24:10 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Dashboard</title>
        <link rel="stylesheet" type="text/css" href="css/studentPage.css">
    </head>
    <body>
        <%
            String userID = (String) session.getAttribute("userID");
        %>
        <!-- Navigation Bar -->
        <nav class="navbar">
            <h1 class="panel-title">Student Dashboard</h1>
            <div class="nav-links">
                <a href="manageStudentProfile.jsp?userId=<%= userID %>" class="nav-link">Profile</a>
                <a href="Logout" class="nav-link">Logout</a>
            </div>
        </nav>

        <!-- Main Content -->
        <div class="main-content">
            <button class="result-button" onclick="window.location.href='viewResult.jsp'">Result</button>
        </div>
    </body>
</html>
