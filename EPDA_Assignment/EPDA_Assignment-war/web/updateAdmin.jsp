<%-- 
    Document   : updateAdmin
    Created on : Dec 28, 2025, 10:07:49 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.MyUsers"%>
<%@page import="model.MyUsersFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.NamingException"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Admin - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/adminProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%
        // Get the ID parameter
        String userId = request.getParameter("userId");
        MyUsers user = null;
        
        if (userId != null) {
            try {
                // Lookup the EJB facades
                InitialContext ic = new InitialContext();
                MyUsersFacade myUsersFacade = (MyUsersFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyUsersFacade");
                
                // Fetch user data from database
                user = myUsersFacade.find(userId);
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
            <form action="updateAdmin" method="post" onsubmit="return validateForm()">
                <table class="profile-table">
                    <tr>
                        <td colspan="2">
                            <h2 class="form-title">UPDATE ADMIN</h2>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="userID">User ID:</label></td>
                        <td><input type="text" id="userID" name="userID" value="<%= user != null ? user.getUserID() : "" %>" readonly></td>
                    </tr>
                    <tr>
                        <td><label for="fullName">Full Name:</label></td>
                        <td><input type="text" id="fullName" name="fullName" value="<%= user != null ? user.getFullName() : "" %>" onblur="validateFullName()" required></td>
                    </tr>
                    <tr class="error-row" id="fullName-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="fullName-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="password">Password:</label></td>
                        <td><input type="password" id="password" name="password" value="<%= user != null ? user.getPassword() : "" %>" onblur="validatePassword()" required></td>
                    </tr>
                    <tr class="error-row" id="password-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="password-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="gender">Gender:</label></td>
                        <td>
                            <select id="gender" name="gender" onblur="validateGender()" required>
                                <option value="">Select Gender</option>
                                <option value="Male" <%= user != null && "Male".equals(user.getGender()) ? "selected" : "" %>>Male</option>
                                <option value="Female" <%= user != null && "Female".equals(user.getGender()) ? "selected" : "" %>>Female</option>
                            </select>
                        </td>
                    </tr>
                    <tr class="error-row" id="gender-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="gender-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="phone">Phone:</label></td>
                        <td><input type="tel" id="phone" name="phone" value="<%= user != null ? user.getPhone() : "" %>" placeholder="e.g., 0123456789" onblur="validatePhone()" required></td>
                    </tr>
                    <tr class="error-row" id="phone-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="phone-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="icNumber">IC Number:</label></td>
                        <td><input type="text" id="icNumber" name="icNumber" value="<%= user != null ? user.getIcNumber() : "" %>" placeholder="e.g., 123456-12-3456" onblur="validateICNumber()" required></td>
                    </tr>
                    <tr class="error-row" id="icNumber-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="icNumber-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="email">Email:</label></td>
                        <td><input type="email" id="email" name="email" value="<%= user != null ? user.getEmail() : "" %>" onblur="validateEmail()" required></td>
                    </tr>
                    <tr class="error-row" id="email-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="email-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="address">Address:</label></td>
                        <td><textarea id="address" name="address" rows="3" onblur="validateAddress()" required><%= user != null ? user.getAddress() : "" %></textarea></td>
                    </tr>
                    <tr class="error-row" id="address-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="address-error-message"></span></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center; padding-top: 20px;">
                            <button type="submit" class="btn-update">Update Admin</button>
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
    <script src="js/adminProfile.js"></script>
</body>
</html>
