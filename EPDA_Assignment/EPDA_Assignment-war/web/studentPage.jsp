<%-- 
    Document   : studentPage
    Created on : Dec 30, 2025, 10:24:10 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Student Dashboard</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/studentPage.css">
    </head>

    <body>

        <jsp:include page="studentNavbar.jsp" />

        <div class="center-content">
            <div class="btn-container">
                <a class="btn" href="viewResult.jsp">View Results</a>
            </div>
        </div>
    </body>
</html>
