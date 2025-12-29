<%-- 
    Document   : registerStudent
    Created on : Dec 25, 2025, 11:47:01 PM
    Author     : bohch
--%>

<%@page import="javax.naming.InitialContext"%>
<%@page import="model.MyUserIDFacade"%>
<%@page import="model.MyUserID"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register New Student - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/studentProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%
        InitialContext ic = new InitialContext();
        MyUserIDFacade facade = (MyUserIDFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyUserIDFacade");
        List<MyUserID> studentList = facade.findByS();
        String currentStudentID = studentList.get(0).getCurrentUserId();
        Integer studentNumber = Integer.parseInt(currentStudentID.substring(1)) + 1;
        String newStudentID = "S" + studentNumber;
    %>
    <!-- Include Sidebar -->
    <jsp:include page="sidebar.jsp" />
    
    <!-- Main Content -->
    <div class="main-content">
        <!-- Include Header -->
        <jsp:include page="header.jsp" />
        
        <div class="content-area" id="content-area">
           
            <form action="registerStudent" method="post" onsubmit="return validateForm()">
                <table class="profile-table">
                    <tr>
                        <td colspan="2">
                            <h2 class="form-title">REGISTER STUDENT</h2>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="userID">User ID:</label></td>
                        <td><input type="text" id="userID" name="userID" value="<%= newStudentID %>" readonly required></td>
                    </tr>
                    <tr>
                        <td><label for="fullName">Full Name:</label></td>
                        <td><input type="text" id="fullName" name="fullName" onblur="validateFullName()" required></td>
                    </tr>
                    <tr class="error-row" id="fullName-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="fullName-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="password">Password:</label></td>
                        <td><input type="password" id="password" name="password" onblur="validatePassword()" required></td>
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
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                            </select>
                        </td>
                    </tr>
                    <tr class="error-row" id="gender-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="gender-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="phone">Phone:</label></td>
                        <td><input type="tel" id="phone" name="phone" placeholder="e.g., 0123456789" onblur="validatePhone()" required></td>
                    </tr>
                    <tr class="error-row" id="phone-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="phone-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="icNumber">IC Number:</label></td>
                        <td><input type="text" id="icNumber" name="icNumber" placeholder="e.g., 123456-12-3456" onblur="validateICNumber()" required></td>
                    </tr>
                    <tr class="error-row" id="icNumber-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="icNumber-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="email">Email:</label></td>
                        <td><input type="email" id="email" name="email" onblur="validateEmail()" required></td>
                    </tr>
                    <tr class="error-row" id="email-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="email-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="address">Address:</label></td>
                        <td><textarea id="address" name="address" rows="3" onblur="validateAddress()" required></textarea></td>
                    </tr>
                    <tr class="error-row" id="address-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="address-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="intakeYear">Intake Year:</label></td>
                        <td>
                            <% 
                                int currentYear = java.time.Year.now().getValue();
                            %>
                            <input type="text" id="intakeYear" name="intakeYear" value="<%= currentYear %>" onblur="validateIntakeYear()" required>
                        </td>
                    </tr>
                    <tr class="error-row" id="intakeYear-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="intakeYear-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="currentLevel">Current Level:</label></td>
                        <td>
                            <select id="currentLevel" name="currentLevel" onblur="validateCurrentLevel()" required>
                                <option value="">Select Level</option>
                                <option value="Year 1">Year 1</option>
                                <option value="Year 2">Year 2</option>
                                <option value="Year 3">Year 3</option>
                                <option value="Year 4">Year 4</option>
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
                            <select id="status" name="status" required>
                                <option value="Active" selected>Active</option>
                                <option value="Inactive">Inactive</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center; padding-top: 20px;">
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
    <script src="js/studentProfile.js"></script>
</body>
</html>
