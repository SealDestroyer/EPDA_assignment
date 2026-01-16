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
    <title>Update Student</title>
    <link rel="stylesheet" href="css/manageStudentProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%
        // Get the ID parameter
        String userId = request.getParameter("userId");
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
    
    <div class="main-content">
        <div class="content-area" id="content-area">
            <form action="updateStudent" method="post" onsubmit="return validateForm()">
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
                        <td><label for="phone">Phone:</label></td>
                        <td><input type="tel" id="phone" name="phone" value="<%= user != null ? user.getPhone() : "" %>" placeholder="e.g., 0123456789" onblur="validatePhone()" required></td>
                    </tr>
                    <tr class="error-row" id="phone-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="phone-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="icNumber">IC Number:</label></td>
                        <td><input type="text" id="icNumber" name="icNumber" value="<%= user != null ? user.getIcNumber() : "" %>" placeholder="e.g., 123456-12-3456" readonly required></td>
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
                        <td><label for="intakeYear">Intake Year:</label></td>
                        <td><input type="text" id="intakeYear" name="intakeYear" value="<%= student != null && student.getIntakeYear() != null ? student.getIntakeYear() : "" %>" readonly required></td>
                    </tr>
                    <tr>
                        <td><label for="currentLevel">Current Level:</label></td>
                        <td>
                            <select id="currentLevel" name="currentLevel" onblur="validateCurrentLevel()" disabled required>
                                <option value="">Select Level</option>
                                <option value="Year 1" <%= (student != null && "Year 1".equals(student.getCurrentLevel())) ? "selected" : "" %>>Year 1</option>
                                <option value="Year 2" <%= (student != null && "Year 2".equals(student.getCurrentLevel())) ? "selected" : "" %>>Year 2</option>
                                <option value="Year 3" <%= (student != null && "Year 3".equals(student.getCurrentLevel())) ? "selected" : "" %>>Year 3</option>
                                <option value="Year 4" <%= (student != null && "Year 4".equals(student.getCurrentLevel())) ? "selected" : "" %>>Year 4</option>
                            </select>
                        </td>
                    </tr>
                    <tr class="error-row" id="currentLevel-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="currentLevel-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="status">Status:</label></td>
                        <td>
                            <select id="status" name="status" disabled required>
                                <option value="Active" <%= (student != null && "Active".equals(student.getStatus())) ? "selected" : "" %>>Active</option>
                                <option value="Inactive" <%= (student != null && "Inactive".equals(student.getStatus())) ? "selected" : "" %>>Inactive</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="secretKey">Secret Key:</label></td>
                        <td><input type="text" id="secretKey" name="secretKey" value="<%= user != null ? user.getSecretKey() : "" %>" readonly></td>
                    </tr>
                    <tr>
                        <td style="text-align: center; padding-top: 20px;">
                            <button type="submit" class="btn-update">Update</button>
                        </td>
                        <td style="text-align: center; padding-top: 20px;">
                            <button type="button" class="btn-cancel" onclick="window.location.href='studentPage.jsp'">Cancel</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    
    <script src="js/studentProfile.js"></script>
</body>
</html>
