<%-- 
    Document   : login
    Created on : Dec 2025
    Author     : You
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>APU Academic Management System - Login</title>
    </head>
    <body>

        <h2>Login</h2>

        <form action="Login" method="POST">
            <table>
                <tr>
                    <td>User ID:</td>
                    <td>
                        <input type="text" name="userID" size="20" required>
                    </td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td>
                        <input type="password" name="password" size="20" required>
                    </td>
                </tr>
            </table>

            <br>
            <input type="submit" value="Login">
            <input type="reset" value="Reset">

        </form>

    </body>
</html>
