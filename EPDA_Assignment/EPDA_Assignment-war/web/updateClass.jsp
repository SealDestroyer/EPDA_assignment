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
    <link rel="stylesheet" href="css/updateClass.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%
        // Get the ID parameter
        String classId = request.getParameter("id");
        MyStudentClass myClass = null;
        
        if (classId != null) {
            try {
                // Lookup the EJB facade
                InitialContext ic = new InitialContext();
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
            <% if (request.getAttribute("message") != null) { %>
                <p style="color: green; font-weight: bold; text-align: center;"><%= request.getAttribute("message") %></p>
            <% } %>
            <form action="updateClass" method="post">
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
                        <td><input type="text" id="className" name="className" value="<%= myClass != null ? myClass.getClassName() : "" %>" required></td>
                    </tr>
                    <tr>
                        <td><label for="semester">Semester:</label></td>
                        <td><input type="text" id="semester" name="semester" value="<%= myClass != null ? myClass.getSemester() : "" %>" required></td>
                    </tr>
                    <tr>
                        <td><label for="academicYear">Academic Year:</label></td>
                        <td><input type="text" id="academicYear" name="academicYear" value="<%= myClass != null ? myClass.getAcademicYear() : "" %>" required></td>
                    </tr>
                    <tr>
                        <td><label for="assignedAcademicLeaderID">Assigned Academic Leader ID:</label></td>
                        <td><input type="text" id="assignedAcademicLeaderID" name="assignedAcademicLeaderID" value="<%= myClass != null ? myClass.getAssignedAcademicLeaderID() : "" %>" required></td>
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
