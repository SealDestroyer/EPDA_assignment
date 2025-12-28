<%-- 
    Document   : addClassStudent
    Created on : Dec 26, 2025, 11:56:32 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Class Student - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/addClassStudent.css">
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
            <form action="addClassStudent" method="post">
                <table class="profile-table">
                    <tr>
                        <td colspan="2">
                            <h2 class="form-title">ADD CLASS STUDENT</h2>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="classID">Class ID:</label></td>
                        <td><input type="text" id="classID" name="classID" value="<%= request.getParameter("classID") %>" readonly></td>
                    </tr>
                    <tr>
                        <td><label for="studentID">Student ID:</label></td>
                        <td><input type="text" id="studentID" name="studentID" required></td>
                    </tr>
                    <tr>
                        <td><label for="enrollmentDate">Enrollment Date:</label></td>
                        <td><input type="date" id="enrollmentDate" name="enrollmentDate" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" required></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center; padding-top: 20px;">
                            <button type="submit" class="btn-update">Add Student</button>
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
