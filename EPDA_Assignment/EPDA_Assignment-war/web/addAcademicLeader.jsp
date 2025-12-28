<%-- 
    Document   : addAcademicLeader
    Created on : Dec 26, 2025, 3:09:16 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Academic Leader - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/addNewAcademicLeader.css">
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
                <div class="success-message">
                    <%= request.getAttribute("message") %>
                </div>
            <% } %>
            
            <form action="addAcademicLeader" method="post">
                    <table class="profile-table">
                        <tr>
                            <td colspan="2">
                                <h1 class="form-title">REGISTER ACADEMIC LEADER</h1>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>User ID:</td>
                            <td><input type="text" id="userID" name="userID" required></td>
                        </tr>
                            
                            <tr>
                                <td>Full Name:</td>
                                <td><input type="text" id="fullName" name="fullName" required></td>
                            </tr>
                            
                            <tr>
                                <td>Password:</td>
                                <td><input type="password" id="password" name="password" required></td>
                            </tr>
                            
                            <tr>
                                <td>Gender:</td>
                                <td>
                                    <select id="gender" name="gender" required>
                                        <option value="">Select Gender</option>
                                        <option value="Male">Male</option>
                                        <option value="Female">Female</option>
                                    </select>
                                </td>
                            </tr>
                            
                            <tr>
                                <td>Phone:</td>
                                <td><input type="tel" id="phone" name="phone" required></td>
                            </tr>
                            
                            <tr>
                                <td>IC Number:</td>
                                <td><input type="text" id="icNumber" name="icNumber" required></td>
                            </tr>
                            
                            <tr>
                                <td>Email:</td>
                                <td><input type="email" id="email" name="email" required></td>
                            </tr>
                            
                            <tr>
                                <td>Address:</td>
                                <td><textarea id="address" name="address" rows="3" required></textarea></td>
                            </tr>
                            
                            <tr>
                                <td>Leader Role:</td>
                                <td>
                                    <select id="leaderRole" name="leaderRole" required>
                                        <option value="">Select Leader Role</option>
                                        <option value="Dean">Dean</option>
                                        <option value="Head of Department">Head of Department</option>
                                        <option value="Program Coordinator">Program Coordinator</option>
                                        <option value="Deputy Dean">Deputy Dean</option>
                                    </select>
                                </td>
                            </tr>
                            
                            <tr>
                                <td>Start Date:</td>
                                <td><input type="date" id="startDate" name="startDate" required></td>
                            </tr>
                            
                            <tr>
                                <td>End Date:</td>
                                <td><input type="date" id="endDate" name="endDate"></td>
                            </tr>
                            
                            <tr>
                                <td colspan="2" class="button-container">
                                    <button type="submit" class="btn-update">Register</button>
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