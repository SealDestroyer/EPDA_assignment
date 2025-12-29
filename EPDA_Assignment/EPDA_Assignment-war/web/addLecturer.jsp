<%-- 
    Document   : addLecturer
    Created on : Dec 26, 2025, 1:41:14 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="model.MyUsersFacade"%>
<%@page import="model.MyUsers"%>
<%@page import="model.MyUserIDFacade"%>
<%@page import="model.MyUserID"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Lecturer - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/lecturerProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%
        InitialContext ic = new InitialContext();
        MyUserIDFacade facade = (MyUserIDFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyUserIDFacade");
        List<MyUserID> lecturerList = facade.findByL();
        String currentLecturerID = lecturerList.get(0).getCurrentUserId();
        Integer lecturerNumber= Integer.parseInt(currentLecturerID.substring(1)) + 1;
        String newLecturerID = "L"+lecturerNumber;

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
            <form action="addLecturer" method="post" onsubmit="return validateForm()">
                <table class="profile-table">
                    <tr>
                        <td colspan="2">
                            <h2 class="form-title">ADD NEW LECTURER</h2>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="userID">User ID:</label></td>
                        <td><input type="text" id="userID" name="userID" value="<%= newLecturerID %>" readonly required></td>
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
                        <td><label for="employmentType">Employment Type:</label></td>
                        <td>
                            <select id="employmentType" name="employmentType" onblur="validateEmploymentType()" required>
                                <option value="">Select Employment Type</option>
                                <option value="Full-Time">Full-Time</option>
                                <option value="Part-Time">Part-Time</option>
                                <option value="Contract">Contract</option>
                            </select>
                        </td>
                    </tr>
                    <tr class="error-row" id="employmentType-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="employmentType-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="academicRank">Academic Rank:</label></td>
                        <td>
                            <select id="academicRank" name="academicRank" onblur="validateAcademicRank()" required>
                                <option value="">Select Academic Rank</option>
                                <option value="Professor">Professor</option>
                                <option value="Associate Professor">Associate Professor</option>
                                <option value="Assistant Professor">Assistant Professor</option>
                                <option value="Lecturer">Lecturer</option>
                                <option value="Senior Lecturer">Senior Lecturer</option>
                            </select>
                        </td>
                    </tr>
                    <tr class="error-row" id="academicRank-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="academicRank-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="academicLeaderID">Academic Leader ID:</label></td>
                        <td>
                            <select id="academicLeaderID" name="academicLeaderID">
                                <option value="">Select Academic Leader</option>
                                <% for (MyUsers leader : academicLeader) { %>
                                    <option value="<%= leader.getUserID() %>"><%= leader.getUserID() %> - <%= leader.getFullName() %></option>
                                <% } %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center; padding-top: 20px;">
                            <button type="submit" class="btn-update">Add Lecturer</button>
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
    <script src="js/lecturerProfile.js"></script>
</body>
</html>