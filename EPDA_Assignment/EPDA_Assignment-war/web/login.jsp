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

<%
    // Load demo data via servlet
    // This will initialize the demo admin users in the database
    try {
        request.getRequestDispatcher("/loadDemoData").include(request, response);
    } catch (Exception e) {
        // Silently catch any errors during demo data loading
    }
%>

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
                    <td>Email:</td>
                    <td>
                        <input type="email" name="email" size="20" required>
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
