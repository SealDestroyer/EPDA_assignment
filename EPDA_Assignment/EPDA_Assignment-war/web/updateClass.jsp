<%-- 
    Document   : updateClass
    Created on : Dec 26, 2025, 10:38:42 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.MyStudentClass"%>
<%@page import="model.MyStudentClassFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.NamingException"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Class</title>
    </head>
    <body>
        <%
            // Get the ID parameter
            String classId = request.getParameter("id");
            MyStudentClass myClass = null;
            
            if (classId != null) {
                try {
                    // Lookup the EJB facade
                    InitialContext ic = new InitialContext();
                    MyStudentClassFacade myClassFacade = (MyStudentClassFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyStudentClassFacade");
                    
                    // Fetch class data from database
                    Integer classIdInt = Integer.parseInt(classId);
                    myClass = myClassFacade.find(classIdInt);
                } catch (NamingException e) {
                    out.println("<p style='color:red;'>Error: Unable to access database. " + e.getMessage() + "</p>");
                } catch (NumberFormatException e) {
                    out.println("<p style='color:red;'>Error: Invalid class ID format. " + e.getMessage() + "</p>");
                }
            }
        %>
        <h1>Update Class</h1>
        <form action="updateClass" method="post">
            <table border="1">
                <tr>
                    <td><label for="classId">Class ID:</label></td>
                    <td><input type="text" id="classId" name="classId" value="<%= classId != null ? classId : "" %>" readonly required></td>
                </tr>
                <tr>
                    <td><label for="className">Class Name:</label></td>
                    <td><input type="text" id="className" name="className" value="<%= myClass != null ? myClass.getClassName() : "" %>" required></td>
                </tr>
                <tr>
                    <td><label for="semester">Semester:</label></td>
                    <td><input type="text" id="semester" name="semester" value="<%= myClass != null ? myClass.getSemester() : "" %>" required></td>
                </tr>
                <tr>
                    <td><label for="academicYear">Academic Year:</label></td>
                    <td><input type="text" id="academicYear" name="academicYear" value="<%= myClass != null ? myClass.getAcademicYear() : "" %>" required></td>
                </tr>
                <tr>
                    <td><label for="createdBy">Created By:</label></td>
                    <td><input type="text" id="createdBy" name="createdBy" value="<%= myClass != null ? myClass.getCreatedBy() : "" %>" required></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Update"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
