<%-- 
    Document   : updateStudent
    Created on : Dec 26, 2025, 2:19:51 AM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.MyUsers"%>
<%@page import="model.MyStudent"%>
<%@page import="model.MyUsersFacade"%>
<%@page import="model.MyStudentFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.NamingException"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Student - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/updateStudent.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%
        // Get the ID parameter
        String userId = request.getParameter("id");
        MyUsers user = null;
        MyStudent student = null;
        
        if (userId != null) {
            try {
                // Lookup the EJB facades
                InitialContext ic = new InitialContext();
                MyUsersFacade myUsersFacade = (MyUsersFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyUsersFacade");
                MyStudentFacade myStudentFacade = (MyStudentFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyStudentFacade");
                
                // Fetch user and student data from database
                user = myUsersFacade.find(userId);
                student = myStudentFacade.find(userId);
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
            <form action="updateStudent" method="post">
                <table class="profile-table">
                    <tr>
                        <td colspan="2">
                            <h2 class="form-title">UPDATE STUDENT</h2>
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
                        <td><label for="intakeYear">Intake Year:</label></td>
                        <td><input type="text" id="intakeYear" name="intakeYear" value="<%= student != null ? student.getIntakeYear() : "" %>" required></td>
                    </tr>
                    <tr>
                        <td><label for="currentLevel">Current Level:</label></td>
                        <td>
                            <select id="currentLevel" name="currentLevel" required>
                                <option value="">Select Level</option>
                                <option value="year1" <%= (student != null && "year1".equals(student.getCurrentLevel())) ? "selected" : "" %>>Year 1</option>
                                <option value="year2" <%= (student != null && "year2".equals(student.getCurrentLevel())) ? "selected" : "" %>>Year 2</option>
                                <option value="year3" <%= (student != null && "year3".equals(student.getCurrentLevel())) ? "selected" : "" %>>Year 3</option>
                                <option value="year4" <%= (student != null && "year4".equals(student.getCurrentLevel())) ? "selected" : "" %>>Year 4</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="status">Status:</label></td>
                        <td>
                            <select id="status" name="status" required>
                                <option value="Active" <%= (student != null && "Active".equals(student.getStatus())) ? "selected" : "" %>>Active</option>
                                <option value="Inactive" <%= (student != null && "Inactive".equals(student.getStatus())) ? "selected" : "" %>>Inactive</option>
                            </select>
                        </td>
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
