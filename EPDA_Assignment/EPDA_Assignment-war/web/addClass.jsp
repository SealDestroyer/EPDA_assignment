<%-- 
    Document   : addClass
    Created on : Dec 26, 2025, 8:07:09 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Class</title>
    </head>
    <body>
        <h1>Add New Class</h1>
        <% if (request.getAttribute("message") != null) { %>
            <p style="color: green; font-weight: bold;"><%= request.getAttribute("message") %></p>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <p style="color: red; font-weight: bold;"><%= request.getAttribute("error") %></p>
        <% } %>
        <form action="addClass" method="post">
            <table border="1">
                <tr>
                    <td><label for="className">Class Name:</label></td>
                    <td><input type="text" id="className" name="className" required></td>
                </tr>
                <tr>
                    <td><label for="semester">Semester:</label></td>
                    <td><input type="text" id="semester" name="semester" required></td>
                </tr>
                <tr>
                    <td><label for="academicYear">Academic Year:</label></td>
                    <td><input type="text" id="academicYear" name="academicYear" required></td>
                </tr>
                <tr>
                    <td><label for="createdBy">Created By:</label></td>
                    <td><input type="text" id="createdBy" name="createdBy" required></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Add Class"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
