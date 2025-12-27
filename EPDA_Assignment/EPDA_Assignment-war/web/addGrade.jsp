<%-- 
    Document   : addGrade
    Created on : Dec 26, 2025, 5:01:39 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Grade</title>
    </head>
    <body>
        <h1>Add New Grade</h1>
        <% if (request.getAttribute("message") != null) { %>
            <p style="color: green; font-weight: bold;"><%= request.getAttribute("message") %></p>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <p style="color: red; font-weight: bold;"><%= request.getAttribute("error") %></p>
        <% } %>
        <form action="addGrade" method="post">
            <table border="1">
                <tr>
                    <td><label for="gradeLetter">Grade Letter:</label></td>
                    <td><input type="text" id="gradeLetter" name="gradeLetter" required></td>
                </tr>
                <tr>
                    <td><label for="minPercentage">Min Percentage:</label></td>
                    <td><input type="number" id="minPercentage" name="minPercentage" min="0" max="100" required></td>
                </tr>
                <tr>
                    <td><label for="maxPercentage">Max Percentage:</label></td>
                    <td><input type="number" id="maxPercentage" name="maxPercentage" min="0" max="100" required></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Add Grade"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
