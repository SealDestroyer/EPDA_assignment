<%-- 
    Document   : login
    Created on : Dec 2025
    Author     : You
--%>

<%@page import="model.MyUserID"%>
<%@page import="model.MyUserIDFacade"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.persistence.Persistence"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%--
<%
    // Load demo data via servlet
    // This will initialize the demo admin users in the database
    try {
        request.getRequestDispatcher("/loadDemoData").include(request, response);
    } catch (Exception e) {
        // Silently catch any errors during demo data loading
    }
%>
--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>APU Academic Management System - Forget Password</title>
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <script src="js/forgetPassword.js"></script>
    </head>
    <body>
        <form action="forgetPassword" method="POST" onsubmit="return validateForm()">
            <table>
                <tr>
                    <td colspan="2"><h2>Forget Password</h2></td>
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
                    <td>New Password:</td>
                    <td>
                        <input type="password" id="password" name="newPassword" size="20" required onblur="validatePassword()">
                    </td>
                </tr>
                <tr class="error-row" id="password-error" style="display: none;">
                    <td></td>
                    <td><span class="error-message" id="password-error-message"></span></td>
                </tr>
                <tr>
                    <td>Secret Key:</td>
                    <td>
                        <input type="password" id="secretKey" name="secretKey" size="20" required onblur="validateSecretKey()">
                    </td>
                </tr>
                <tr class="error-row" id="secretKey-error" style="display: none;">
                    <td></td>
                    <td><span class="error-message" id="secretKey-error-message"></span></td>
                </tr>

                <tr>
                    <td>
                        <input type="button" value="Sign in" onclick="location.href = 'login.jsp'">
                    </td>
                    <td>
                        <input type="submit" value="Reset Password">
                    </td>
                </tr>
            </table>

        </form>

    </body>
</html>
