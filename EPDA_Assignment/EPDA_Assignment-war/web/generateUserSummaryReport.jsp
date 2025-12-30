<%-- 
    Document   : generateUserSummaryReport
    Created on : Dec 30, 2025
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate User Summary Report - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/gradeProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <!-- Include Sidebar -->
    <jsp:include page="sidebar.jsp" />
    
    <!-- Main Content -->
    <div class="main-content">
        <!-- Include Header -->
        <jsp:include page="header.jsp" />
        
        <div class="content-area" id="content-area">
            <form action="generateUserSummaryReport" method="post" onsubmit="return validateForm()">
                <table class="profile-table">
                    <tr>
                        <td colspan="2">
                            <h2 class="form-title">GENERATE USER SUMMARY REPORT</h2>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="reportId">Report ID:</label></td>
                        <td><input type="text" id="reportId" name="reportId" value="<%= request.getParameter("reportId") != null ? request.getParameter("reportId") : "" %>" readonly required></td>
                    </tr>
                    <tr>
                        <td><label for="reportName">Name:</label></td>
                        <td><input type="text" id="reportName" name="reportName" value="<%= request.getParameter("reportName") != null ? request.getParameter("reportName") : "" %>" readonly required></td>
                    </tr>
                    <tr>
                        <td><label for="startDate">Start Date:</label></td>
                        <td><input type="date" id="startDate" name="startDate" required></td>
                    </tr>
                    <tr>
                        <td><label for="endDate">End Date:</label></td>
                        <td><input type="date" id="endDate" name="endDate" required></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center; padding-top: 20px;">
                            <button type="submit" class="btn-update">Generate Report</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        
        <!-- Include Footer -->
        <jsp:include page="footer.jsp" />
    </div>
    
    <script src="js/adminDashboard.js"></script>
    <script src="js/sidebar.js"></script>
    <script src="js/header.js"></script>
    <script src="js/footer.js"></script>
    <script src="js/gradeProfile.js"></script>
</body>
</html>
