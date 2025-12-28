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
    // Comment out the initialization logic for now to avoid the error
    // This should be done in a servlet or through proper dependency injection
    /*
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourPersistenceUnitName");
    MyUserIDFacade myUserIDFacade = new MyUserIDFacade(emf.createEntityManager());
    
    // Initialize default user IDs if the database is empty
    long count = myUserIDFacade.countRecord();
    if (count <= 0) {
        myUserIDFacade.create(new MyUserID("S0", "student"));
        myUserIDFacade.create(new MyUserID("AL0", "academicLeader"));
        myUserIDFacade.create(new MyUserID("AD0", "admin"));
        myUserIDFacade.create(new MyUserID("L0", "lecture"));
    }
    */
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
