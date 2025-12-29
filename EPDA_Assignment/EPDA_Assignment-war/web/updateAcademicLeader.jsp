<%-- 
    Document   : updateAcademicLeader
    Created on : Dec 26, 2025, 3:54:34 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.MyUsers"%>
<%@page import="model.MyAcademicLeader"%>
<%@page import="model.MyUsersFacade"%>
<%@page import="model.MyAcademicLeaderFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.NamingException"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Academic Leader - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/academicLeaderProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%
        // Get the ID parameter
        String userId = request.getParameter("userId");
        MyUsers user = null;
        MyAcademicLeader academicLeader = null;
        
        if (userId != null) {
            try {
                // Lookup the EJB facades
                InitialContext ic = new InitialContext();
                MyUsersFacade myUsersFacade = (MyUsersFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyUsersFacade");
                MyAcademicLeaderFacade myAcademicLeaderFacade = (MyAcademicLeaderFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyAcademicLeaderFacade");
                
                // Fetch user and academic leader data from database
                user = myUsersFacade.find(userId);
                academicLeader = myAcademicLeaderFacade.find(userId);
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
            <form action="updateAcademicLeader" method="post" onsubmit="return validateForm()">
                    <table class="profile-table">
                        <tr>
                            <td colspan="2">
                                <h1 class="form-title">UPDATE ACADEMIC LEADER</h1>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>User ID:</td>
                            <td><input type="text" id="userID" name="userID" value="<%= userId != null ? userId : "" %>" readonly required></td>
                        </tr>
                            
                            <tr>
                                <td>Full Name:</td>
                                <td><input type="text" id="fullName" name="fullName" value="<%= user != null ? user.getFullName() : "" %>" onblur="validateFullName()" required></td>
                            </tr>
                            <tr class="error-row" id="fullName-error" style="display: none;">
                                <td></td>
                                <td><span class="error-message" id="fullName-error-message"></span></td>
                            </tr>
                            
                            <tr>
                                <td>Password:</td>
                                <td><input type="password" id="password" name="password" value="<%= user != null ? user.getPassword() : "" %>" onblur="validatePassword()" required></td>
                            </tr>
                            <tr class="error-row" id="password-error" style="display: none;">
                                <td></td>
                                <td><span class="error-message" id="password-error-message"></span></td>
                            </tr>
                            
                            <tr>
                                <td>Gender:</td>
                                <td>
                                    <select id="gender" name="gender" onblur="validateGender()" required>
                                        <option value="">Select Gender</option>
                                        <option value="Male" <%= (user != null && "Male".equals(user.getGender())) ? "selected" : "" %>>Male</option>
                                        <option value="Female" <%= (user != null && "Female".equals(user.getGender())) ? "selected" : "" %>>Female</option>
                                    </select>
                                </td>
                            </tr>
                            <tr class="error-row" id="gender-error" style="display: none;">
                                <td></td>
                                <td><span class="error-message" id="gender-error-message"></span></td>
                            </tr>
                            
                            <tr>
                                <td>Phone:</td>
                                <td><input type="tel" id="phone" name="phone" value="<%= user != null ? user.getPhone() : "" %>" placeholder="e.g., 0123456789" onblur="validatePhone()" required></td>
                            </tr>
                            <tr class="error-row" id="phone-error" style="display: none;">
                                <td></td>
                                <td><span class="error-message" id="phone-error-message"></span></td>
                            </tr>
                            
                            <tr>
                                <td>IC Number:</td>
                                <td><input type="text" id="icNumber" name="icNumber" value="<%= user != null ? user.getIcNumber() : "" %>" placeholder="e.g., 123456-12-3456" onblur="validateICNumber()" required></td>
                            </tr>
                            <tr class="error-row" id="icNumber-error" style="display: none;">
                                <td></td>
                                <td><span class="error-message" id="icNumber-error-message"></span></td>
                            </tr>
                            
                            <tr>
                                <td>Email:</td>
                                <td><input type="email" id="email" name="email" value="<%= user != null ? user.getEmail() : "" %>" onblur="validateEmail()" required></td>
                            </tr>
                            <tr class="error-row" id="email-error" style="display: none;">
                                <td></td>
                                <td><span class="error-message" id="email-error-message"></span></td>
                            </tr>
                            
                            <tr>
                                <td>Address:</td>
                                <td><textarea id="address" name="address" rows="3" onblur="validateAddress()" required><%= user != null ? user.getAddress() : "" %></textarea></td>
                            </tr>
                            <tr class="error-row" id="address-error" style="display: none;">
                                <td></td>
                                <td><span class="error-message" id="address-error-message"></span></td>
                            </tr>
                            
                            <tr>
                                <td>Leader Role:</td>
                                <td>
                                    <select id="leaderRole" name="leaderRole" onblur="validateLeaderRole()" required>
                                        <option value="">Select Leader Role</option>
                                        <option value="Dean" <%= (academicLeader != null && "Dean".equals(academicLeader.getLeaderRole())) ? "selected" : "" %>>Dean</option>
                                        <option value="Head of Department" <%= (academicLeader != null && "Head of Department".equals(academicLeader.getLeaderRole())) ? "selected" : "" %>>Head of Department</option>
                                        <option value="Program Coordinator" <%= (academicLeader != null && "Program Coordinator".equals(academicLeader.getLeaderRole())) ? "selected" : "" %>>Program Coordinator</option>
                                        <option value="Deputy Dean" <%= (academicLeader != null && "Deputy Dean".equals(academicLeader.getLeaderRole())) ? "selected" : "" %>>Deputy Dean</option>
                                    </select>
                                </td>
                            </tr>
                            <tr class="error-row" id="leaderRole-error" style="display: none;">
                                <td></td>
                                <td><span class="error-message" id="leaderRole-error-message"></span></td>
                            </tr>
                            
                            <tr>
                                <td>Start Date:</td>
                                <td><input type="date" id="startDate" name="startDate" value="<%= academicLeader != null ? (academicLeader.getStartDate() != null ? academicLeader.getStartDate() : "") : "" %>" onblur="validateStartDate()" required></td>
                            </tr>
                            <tr class="error-row" id="startDate-error" style="display: none;">
                                <td></td>
                                <td><span class="error-message" id="startDate-error-message"></span></td>
                            </tr>
                            
                            <tr>
                                <td>End Date:</td>
                                <td><input type="date" id="endDate" name="endDate" value="<%= academicLeader != null ? (academicLeader.getEndDate() != null ? academicLeader.getEndDate() : "") : "" %>" onblur="validateEndDate()"></td>
                            </tr>
                            <tr class="error-row" id="endDate-error" style="display: none;">
                                <td></td>
                                <td><span class="error-message" id="endDate-error-message"></span></td>
                            </tr>
                            
                            <tr>
                                <td colspan="2" class="button-container">
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
    <script src="js/academicLeaderProfile.js"></script>
</body>
</html>
