<%-- 
    Document   : addClass
    Created on : Dec 26, 2025, 8:07:09 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Class - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/addClass.css">
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
            <% if (request.getAttribute("message") != null) { %>
                <p style="color: green; font-weight: bold; text-align: center;"><%= request.getAttribute("message") %></p>
            <% } %>
            <% if (request.getAttribute("error") != null) { %>
                <p style="color: red; font-weight: bold; text-align: center;"><%= request.getAttribute("error") %></p>
            <% } %>
            <form action="addClass" method="post">
                <table class="profile-table">
                    <tr>
                        <td colspan="2">
                            <h2 class="form-title">ADD NEW CLASS</h2>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="className">Class Name:</label></td>
                        <td><input type="text" id="className" name="className" required></td>
                    </tr>
                    <tr>
                        <td><label for="semester">Semester:</label></td>
                        <td><input type="text" id="semester" name="semester" required></td>
                    </tr>
                    <tr>
                        <td><label for="academicYear">Academic Year:</label></td>
                        <td><input type="text" id="academicYear" name="academicYear" required></td>
                    </tr>
                    <tr>
                        <td><label for="createdBy">Created By:</label></td>
                        <td><input type="text" id="createdBy" name="createdBy" required></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center; padding-top: 20px;">
                            <button type="submit" class="btn-update">Add Class</button>
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
</body>
</html>
