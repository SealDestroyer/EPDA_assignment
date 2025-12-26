<%-- 
    Document   : updateStudent
    Created on : Dec 26, 2025, 2:19:51 AM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Student</title>
    </head>
    <body>
        <h1>Update Student Profile</h1>
        <form action="registerStudent" method="post">
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
                    <td><label for="matricNo">Matric Number:</label></td>
                    <td><input type="text" id="matricNo" name="matricNo" required></td>
                </tr>
                <tr>
                    <td><label for="intakeYear">Intake Year:</label></td>
                    <td><input type="text" id="intakeYear" name="intakeYear" required></td>
                </tr>
                <tr>
                    <td><label for="currentLevel">Current Level:</label></td>
                    <td>
                        <select id="currentLevel" name="currentLevel" required>
                            <option value="">Select Level</option>
                            <option value="year1">Year 1</option>
                            <option value="year2">Year 2</option>
                            <option value="year3">Year 3</option>
                            <option value="year4">Year 4</option>
                        </select>
                    </td>
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
                    <td colspan="2"><input type="submit" value="Register"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
