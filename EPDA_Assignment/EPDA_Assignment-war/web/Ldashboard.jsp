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
        <meta charset="UTF-8">
        <title>Lecturer Dashboard</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/al-dashboard.css">
    </head>

    <body>

        <jsp:include page="Lnavbar.jsp" />

        <div class="center-content">
            <div class="btn-container">
                <a class="btn" href="${pageContext.request.contextPath}/LModule?action=list">
                    My Modules
                </a>
            </div>
        </div>

    </body>
</html>
