<%-- 
    Document   : addClassStudent
    Created on : Dec 26, 2025, 11:56:32 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Class Student</title>
    </head>
    <body>
        <h1>Add Class Student</h1>
        <% if (request.getAttribute("message") != null) { %>
            <p style="color: green; font-weight: bold;"><%= request.getAttribute("message") %></p>
        <% } %>
        <form action="addClassStudent" method="post">
            <table border="1">
                <tr>
                    <td><label for="classID">Class ID:</label></td>
                    <td><input type="text" id="classID" name="classID" value="<%= request.getParameter("classID") %>" readonly></td>
                </tr>
                <tr>
                    <td><label for="studentID">Student ID:</label></td>
                    <td><input type="text" id="studentID" name="studentID" required></td>
                </tr>
                <tr>
                    <td><label for="enrollmentDate">Enrollment Date:</label></td>
                    <td><input type="date" id="enrollmentDate" name="enrollmentDate" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" required></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Add Student"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
