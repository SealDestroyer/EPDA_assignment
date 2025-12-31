<%-- 
    Document   : updateClass
    Created on : Dec 26, 2025, 10:38:42 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.MyStudentClass"%>
<%@page import="model.MyStudentClassFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="java.util.List"%>
<%@page import="model.MyUsers"%>
<%@page import="model.MyUsersFacade"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Class - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/classProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%
        // Get the ID parameter
        String classId = request.getParameter("classId");
        MyStudentClass myClass = null;
        
        InitialContext ic = new InitialContext();
        MyUsersFacade userFacade = (MyUsersFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyUsersFacade");
        List<MyUsers> academicLeader = userFacade.findAllAcademicLeaders();
        
        if (classId != null) {
            try {
                // Lookup the EJB facade
                MyStudentClassFacade myClassFacade = (MyStudentClassFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyStudentClassFacade");
                
                // Fetch class data from database
                Integer classIdInt = Integer.parseInt(classId);
                myClass = myClassFacade.find(classIdInt);
            } catch (NamingException e) {
                out.println("<p style='color:red;'>Error: Unable to access database. " + e.getMessage() + "</p>");
            } catch (NumberFormatException e) {
                out.println("<p style='color:red;'>Error: Invalid class ID format. " + e.getMessage() + "</p>");
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
            <form action="updateClass" method="post" onsubmit="return validateForm()">
                <table class="profile-table">
                    <tr>
                        <td colspan="2">
                            <h2 class="form-title">UPDATE CLASS</h2>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="classId">Class ID:</label></td>
                        <td><input type="text" id="classId" name="classId" value="<%= classId != null ? classId : "" %>" readonly required></td>
                    </tr>
                    <tr>
                        <td><label for="className">Class Name:</label></td>
                        <td><input type="text" id="className" name="className" value="<%= myClass != null ? myClass.getClassName() : "" %>" onblur="validateClassName()" required></td>
                    </tr>
                    <tr class="error-row" id="className-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="className-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="semester">Semester:</label></td>
                        <td><input type="text" id="semester" name="semester" value="<%= myClass != null ? myClass.getSemester() : "" %>" onblur="validateSemester()" required></td>
                    </tr>
                    <tr class="error-row" id="semester-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="semester-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="academicYear">Academic Year:</label></td>
                        <td><input type="text" id="academicYear" name="academicYear" value="<%= myClass != null ? myClass.getAcademicYear() : "" %>" onblur="validateAcademicYear()" required></td>
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
    <script src="js/classProfile.js"></script>
                        <td></td>
                        <td><span class="error-message" id="assignedAcademicLeaderID-error-message"></span></td>
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
