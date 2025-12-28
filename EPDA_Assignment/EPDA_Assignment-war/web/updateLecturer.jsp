<%-- 
    Document   : updateLecturer
    Created on : Dec 26, 2025, 1:55:16 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.MyUsers"%>
<%@page import="model.MyLecturer"%>
<%@page import="model.MyUsersFacade"%>
<%@page import="model.MyLecturerFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.NamingException"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Lecturer - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/updateLecturer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%
        // Get the ID parameter
        String userId = request.getParameter("id");
        MyUsers user = null;
        MyLecturer lecturer = null;
        
        if (userId != null) {
            try {
                // Lookup the EJB facades
                InitialContext ic = new InitialContext();
                MyUsersFacade myUsersFacade = (MyUsersFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyUsersFacade");
                MyLecturerFacade myLecturerFacade = (MyLecturerFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyLecturerFacade");
                
                // Fetch user and lecturer data from database
                user = myUsersFacade.find(userId);
                lecturer = myLecturerFacade.find(userId);
            } catch (NamingException e) {
                out.println("<p style='color:red;'>Error: Unable to access database. " + e.getMessage() + "</p>");
            }
        }
    %>
    
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
            <form action="updateLecturer" method="post">
                <table class="profile-table">
                    <tr>
                        <td colspan="2">
                            <h2 class="form-title">UPDATE LECTURER</h2>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="userID">User ID:</label></td>
                        <td><input type="text" id="userID" name="userID" value="<%= userId != null ? userId : "" %>" readonly required></td>
                    </tr>
                    <tr>
                        <td><label for="fullName">Full Name:</label></td>
                        <td><input type="text" id="fullName" name="fullName" value="<%= user != null ? user.getFullName() : "" %>" required></td>
                    </tr>
                    <tr>
                        <td><label for="password">Password:</label></td>
                        <td><input type="password" id="password" name="password" value="<%= user != null ? user.getPassword() : "" %>" required></td>
                    </tr>
                    <tr>
                        <td><label for="gender">Gender:</label></td>
                        <td>
                            <select id="gender" name="gender" required>
                                <option value="">Select Gender</option>
                                <option value="Male" <%= (user != null && "Male".equals(user.getGender())) ? "selected" : "" %>>Male</option>
                                <option value="Female" <%= (user != null && "Female".equals(user.getGender())) ? "selected" : "" %>>Female</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="phone">Phone:</label></td>
                        <td><input type="tel" id="phone" name="phone" value="<%= user != null ? user.getPhone() : "" %>" required></td>
                    </tr>
                    <tr>
                        <td><label for="icNumber">IC Number:</label></td>
                        <td><input type="text" id="icNumber" name="icNumber" value="<%= user != null ? user.getIcNumber() : "" %>" required></td>
                    </tr>
                    <tr>
                        <td><label for="email">Email:</label></td>
                        <td><input type="email" id="email" name="email" value="<%= user != null ? user.getEmail() : "" %>" required></td>
                    </tr>
                    <tr>
                        <td><label for="address">Address:</label></td>
                        <td><textarea id="address" name="address" rows="3" required><%= user != null ? user.getAddress() : "" %></textarea></td>
                    </tr>
                    <tr>
                        <td><label for="employmentType">Employment Type:</label></td>
                        <td>
                            <select id="employmentType" name="employmentType" required>
                                <option value="">Select Employment Type</option>
                                <option value="Full-Time" <%= (lecturer != null && "Full-Time".equals(lecturer.getEmploymentType())) ? "selected" : "" %>>Full-Time</option>
                                <option value="Part-Time" <%= (lecturer != null && "Part-Time".equals(lecturer.getEmploymentType())) ? "selected" : "" %>>Part-Time</option>
                                <option value="Contract" <%= (lecturer != null && "Contract".equals(lecturer.getEmploymentType())) ? "selected" : "" %>>Contract</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="academicRank">Academic Rank:</label></td>
                        <td>
                            <select id="academicRank" name="academicRank" required>
                                <option value="">Select Academic Rank</option>
                                <option value="Professor" <%= (lecturer != null && "Professor".equals(lecturer.getAcademicRank())) ? "selected" : "" %>>Professor</option>
                                <option value="Associate Professor" <%= (lecturer != null && "Associate Professor".equals(lecturer.getAcademicRank())) ? "selected" : "" %>>Associate Professor</option>
                                <option value="Assistant Professor" <%= (lecturer != null && "Assistant Professor".equals(lecturer.getAcademicRank())) ? "selected" : "" %>>Assistant Professor</option>
                                <option value="Lecturer" <%= (lecturer != null && "Lecturer".equals(lecturer.getAcademicRank())) ? "selected" : "" %>>Lecturer</option>
                                <option value="Senior Lecturer" <%= (lecturer != null && "Senior Lecturer".equals(lecturer.getAcademicRank())) ? "selected" : "" %>>Senior Lecturer</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="academicLeaderID">Academic Leader ID:</label></td>
                        <td><input type="text" id="academicLeaderID" name="academicLeaderID" value="<%= lecturer != null ? (lecturer.getAcademicLeaderID() != null ? lecturer.getAcademicLeaderID() : "") : "" %>"></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center; padding-top: 20px;">
                            <button type="submit" class="btn-update">Update</button>
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
