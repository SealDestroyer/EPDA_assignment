<%-- 
    Document   : addClass
    Created on : Dec 26, 2025, 8:07:09 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.List"%>
<%@page import="model.MyUsers"%>
<%@page import="model.MyUsersFacade"%>
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
    <link rel="stylesheet" href="css/classProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%
        InitialContext ic = new InitialContext();
        MyUsersFacade userFacade = (MyUsersFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyUsersFacade");
        List<MyUsers> academicLeader = userFacade.findAllAcademicLeaders();
    %>
    <!-- Include Sidebar -->
    <jsp:include page="sidebar.jsp" />
    
    <!-- Main Content -->
    <div class="main-content">
        <!-- Include Header -->
        <jsp:include page="header.jsp" />
        
        <div class="content-area" id="content-area">
            <form action="addClass" method="post" onsubmit="return validateForm()">
                <table class="profile-table">
                    <tr>
                        <td colspan="2">
                            <h2 class="form-title">ADD NEW CLASS</h2>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="className">Class Name:</label></td>
                        <td><input type="text" id="className" name="className" onblur="validateClassName()" required></td>
                    </tr>
                    <tr class="error-row" id="className-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="className-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="semester">Semester:</label></td>
                        <td><input type="text" id="semester" name="semester" onblur="validateSemester()" required></td>
                    </tr>
                    <tr class="error-row" id="semester-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="semester-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="academicYear">Academic Year:</label></td>
                        <td><input type="text" id="academicYear" name="academicYear" onblur="validateAcademicYear()" required></td>
                    </tr>
                    <tr class="error-row" id="academicYear-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="academicYear-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="assignedAcademicLeaderID">Assigned Academic Leader ID:</label></td>
                        <td>
                            <select id="assignedAcademicLeaderID" name="assignedAcademicLeaderID" onblur="validateAcademicLeader()" required>
                                <option value="">Select Academic Leader</option>
                                <% for (MyUsers leader : academicLeader) { %>
                                    <option value="<%= leader.getUserID() %>"><%= leader.getUserID() %> - <%= leader.getFullName() %></option>
                                <% } %>
                            </select>
                        </td>
                    </tr>
                    <tr class="error-row" id="assignedAcademicLeaderID-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="assignedAcademicLeaderID-error-message"></span></td>
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
    <script src="js/classProfile.js"></script>
</body>
</html>
