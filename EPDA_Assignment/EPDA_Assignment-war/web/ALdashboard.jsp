<%@page contentType="text/html" pageEncoding="UTF-8"%>

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

        <!-- Reusable Navbar -->
        <jsp:include page="ALnavbar.jsp" />

        <!-- Center Content -->
        <div class="center-content">
            <div class="btn-container">
                <a class="btn" href="ALmoduleCreate.jsp">Create New Module</a>
            </div>
        </div>

    </body>
</html>
