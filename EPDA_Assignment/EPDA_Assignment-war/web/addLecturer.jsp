<%-- 
    Document   : addLecturer
    Created on : Dec 26, 2025, 1:41:14 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Lecturer</title>
    </head>
    <body>
        <h1>Add New Lecturer</h1>
        <% if (request.getAttribute("message") != null) { %>
            <p style="color: green; font-weight: bold;"><%= request.getAttribute("message") %></p>
        <% } %>
        <form action="addLecturer" method="post">
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
                    <td><label for="employmentType">Employment Type:</label></td>
                    <td>
                        <select id="employmentType" name="employmentType" required>
                            <option value="">Select Employment Type</option>
                            <option value="Full-Time">Full-Time</option>
                            <option value="Part-Time">Part-Time</option>
                            <option value="Contract">Contract</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="academicRank">Academic Rank:</label></td>
                    <td>
                        <select id="academicRank" name="academicRank" required>
                            <option value="">Select Academic Rank</option>
                            <option value="Professor">Professor</option>
                            <option value="Associate Professor">Associate Professor</option>
                            <option value="Assistant Professor">Assistant Professor</option>
                            <option value="Lecturer">Lecturer</option>
                            <option value="Senior Lecturer">Senior Lecturer</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="academicLeaderID">Academic Leader ID:</label></td>
                    <td><input type="text" id="academicLeaderID" name="academicLeaderID"></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Add Lecturer"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
