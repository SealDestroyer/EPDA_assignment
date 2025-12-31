<%-- 
    Document   : login
    Created on : Dec 2025
    Author     : You
--%>

<%@page import="model.MyUserID"%>
<%@page import="model.MyUserIDFacade"%>
<%@page import="model.MyUsers"%>
<%@page import="model.MyUsersFacade"%>
<%@page import="model.MyAdmin"%>
<%@page import="model.MyAdminFacade"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.persistence.Persistence"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>APU Academic Management System - Login</title>
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <script src="js/login.js"></script>
    </head>
    <body>
        <form action="Login" method="POST">
            <table>
                <tr>
                    <td colspan="2"><h2>Login Form</h2></td>
                </tr>
                <tr>
                    <td>Email Address:</td>
                    <td>
                        <input type="email" id="email" name="email" size="20" required onblur="validateEmail()">
                    </td>
                </tr>
                <tr class="error-row" id="email-error" style="display: none;">
                    <td></td>
                    <td><span class="error-message" id="email-error-message"></span></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td>
                        <input type="password" id="password" name="password" size="20" required onblur="validatePassword()">
                    </td>
                </tr>
                <tr class="error-row" id="password-error" style="display: none;">
                    <td></td>
                    <td><span class="error-message" id="password-error-message"></span></td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Sign in">
                    </td>
                    <td>
                        <input type="button" value="Forget Password" onclick="location.href = 'forgetPassword.jsp'">
                    </td>
                </tr>
            </table>

        </form>

    </body>
</html>
