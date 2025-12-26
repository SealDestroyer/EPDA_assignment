<%-- 
    Document   : updateGrade
    Created on : Dec 26, 2025, 5:23:07 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.MyGrading"%>
<%@page import="model.MyGradingFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.NamingException"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Grade</title>
    </head>
    <body>
        <%
            // Get the ID parameter
            String gradeId = request.getParameter("id");
            MyGrading grade = null;
            
            if (gradeId != null) {
                try {
                    // Lookup the EJB facade
                    InitialContext ic = new InitialContext();
                    MyGradingFacade myGradingFacade = (MyGradingFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyGradingFacade");
                    
                    // Fetch grade data from database
                    Integer gradeIdInt = Integer.parseInt(gradeId);
                    grade = myGradingFacade.find(gradeIdInt);
                } catch (NamingException e) {
                    out.println("<p style='color:red;'>Error: Unable to access database. " + e.getMessage() + "</p>");
                } catch (NumberFormatException e) {
                    out.println("<p style='color:red;'>Error: Invalid grade ID format. " + e.getMessage() + "</p>");
                }
            }
        %>
        <h1>Update Grade</h1>
        <form action="updateGrade" method="post">
            <table border="1">
                <tr>
                    <td><label for="gradeId">Grade ID:</label></td>
                    <td><input type="text" id="gradeId" name="gradeId" value="<%= gradeId != null ? gradeId : "" %>" readonly required></td>
                </tr>
                <tr>
                    <td><label for="gradeLetter">Grade Letter:</label></td>
                    <td><input type="text" id="gradeLetter" name="gradeLetter" value="<%= grade != null ? grade.getGradeLetter() : "" %>" required></td>
                </tr>
                <tr>
                    <td><label for="minPercentage">Min Percentage:</label></td>
                    <td><input type="number" id="minPercentage" name="minPercentage" min="0" max="100" value="<%= grade != null ? grade.getMinPercentage() : "" %>" required></td>
                </tr>
                <tr>
                    <td><label for="maxPercentage">Max Percentage:</label></td>
                    <td><input type="number" id="maxPercentage" name="maxPercentage" min="0" max="100" value="<%= grade != null ? grade.getMaxPercentage() : "" %>" required></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Update"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
