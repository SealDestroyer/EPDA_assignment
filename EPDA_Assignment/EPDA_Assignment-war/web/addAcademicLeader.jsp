<%-- 
    Document   : addAcademicLeader
    Created on : Dec 26, 2025, 3:09:16 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Academic Leader</title>
    </head>
    <body>
        <h1>Add New Academic Leader</h1>
        <% if (request.getAttribute("message") != null) { %>
            <p style="color: green; font-weight: bold;"><%= request.getAttribute("message") %></p>
        <% } %>
        <form action="addAcademicLeader" method="post">
            <table border="1">
                <tr>
                    <td><label for="userID">User ID:</label></td>
                    <td><input type="text" id="userID" name="userID" required></td>
                </tr>
                <tr>
                    <td><label for="fullName">Full Name:</label></td>
                    <td><input type="text" id="fullName" name="fullName" required></td>
                </tr>
                <tr>
                    <td><label for="password">Password:</label></td>
                    <td><input type="password" id="password" name="password" required></td>
                </tr>
                <tr>
                    <td><label for="gender">Gender:</label></td>
                    <td>
                        <select id="gender" name="gender" required>
                            <option value="">Select Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="phone">Phone:</label></td>
                    <td><input type="tel" id="phone" name="phone" required></td>
                </tr>
                <tr>
                    <td><label for="icNumber">IC Number:</label></td>
                    <td><input type="text" id="icNumber" name="icNumber" required></td>
                </tr>
                <tr>
                    <td><label for="email">Email:</label></td>
                    <td><input type="email" id="email" name="email" required></td>
                </tr>
                <tr>
                    <td><label for="address">Address:</label></td>
                    <td><textarea id="address" name="address" rows="3" required></textarea></td>
                </tr>
                <tr>
                    <td><label for="leaderRole">Leader Role:</label></td>
                    <td>
                        <select id="leaderRole" name="leaderRole" required>
                            <option value="">Select Leader Role</option>
                            <option value="Dean">Dean</option>
                            <option value="Head of Department">Head of Department</option>
                            <option value="Program Coordinator">Program Coordinator</option>
                            <option value="Deputy Dean">Deputy Dean</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="startDate">Start Date:</label></td>
                    <td><input type="date" id="startDate" name="startDate" required></td>
                </tr>
                <tr>
                    <td><label for="endDate">End Date:</label></td>
                    <td><input type="date" id="endDate" name="endDate"></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Add Academic Leader"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
