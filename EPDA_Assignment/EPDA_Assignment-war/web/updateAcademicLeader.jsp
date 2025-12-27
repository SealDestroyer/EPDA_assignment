<%-- 
    Document   : updateAcademicLeader
    Created on : Dec 26, 2025, 3:54:34 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.MyUsers"%>
<%@page import="model.MyAcademicLeader"%>
<%@page import="model.MyUsersFacade"%>
<%@page import="model.MyAcademicLeaderFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.NamingException"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Academic Leader</title>
    </head>
    <body>
        <%
            // Get the ID parameter
            String userId = request.getParameter("id");
            MyUsers user = null;
            MyAcademicLeader academicLeader = null;
            
            if (userId != null) {
                try {
                    // Lookup the EJB facades
                    InitialContext ic = new InitialContext();
                    MyUsersFacade myUsersFacade = (MyUsersFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyUsersFacade");
                    MyAcademicLeaderFacade myAcademicLeaderFacade = (MyAcademicLeaderFacade) ic.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyAcademicLeaderFacade");
                    
                    // Fetch user and academic leader data from database
                    user = myUsersFacade.find(userId);
                    academicLeader = myAcademicLeaderFacade.find(userId);
                } catch (NamingException e) {
                    out.println("<p style='color:red;'>Error: Unable to access database. " + e.getMessage() + "</p>");
                }
            }
        %>
        <h1>Update Academic Leader Profile</h1>
        <form action="updateAcademicLeader" method="post">
            <table border="1">
                <tr>
                    <td><label for="userID">User ID:</label></td>
                    <td><input type="text" id="userID" name="userID" value="<%= userId != null ? userId : "" %>" readonly required></td>
                </tr>
                <tr>
                    <td><label for="fullName">Full Name:</label></td>
                    <td><input type="text" id="fullName" name="fullName" value="<%= user != null ? user.getFullName() : "" %>" required></td>
                </tr>
                <tr>
                    <td><label for="password">Password:</label></td>
                    <td><input type="password" id="password" name="password" value="<%= user != null ? user.getPassword() : "" %>" required></td>
                </tr>
                <tr>
                    <td><label for="gender">Gender:</label></td>
                    <td>
                        <select id="gender" name="gender" required>
                            <option value="">Select Gender</option>
                            <option value="Male" <%= (user != null && "Male".equals(user.getGender())) ? "selected" : "" %>>Male</option>
                            <option value="Female" <%= (user != null && "Female".equals(user.getGender())) ? "selected" : "" %>>Female</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="phone">Phone:</label></td>
                    <td><input type="tel" id="phone" name="phone" value="<%= user != null ? user.getPhone() : "" %>" required></td>
                </tr>
                <tr>
                    <td><label for="icNumber">IC Number:</label></td>
                    <td><input type="text" id="icNumber" name="icNumber" value="<%= user != null ? user.getIcNumber() : "" %>" required></td>
                </tr>
                <tr>
                    <td><label for="email">Email:</label></td>
                    <td><input type="email" id="email" name="email" value="<%= user != null ? user.getEmail() : "" %>" required></td>
                </tr>
                <tr>
                    <td><label for="address">Address:</label></td>
                    <td><textarea id="address" name="address" rows="3" required><%= user != null ? user.getAddress() : "" %></textarea></td>
                </tr>
                <tr>
                    <td><label for="leaderRole">Leader Role:</label></td>
                    <td>
                        <select id="leaderRole" name="leaderRole" required>
                            <option value="">Select Leader Role</option>
                            <option value="Dean" <%= (academicLeader != null && "Dean".equals(academicLeader.getLeaderRole())) ? "selected" : "" %>>Dean</option>
                            <option value="Head of Department" <%= (academicLeader != null && "Head of Department".equals(academicLeader.getLeaderRole())) ? "selected" : "" %>>Head of Department</option>
                            <option value="Program Coordinator" <%= (academicLeader != null && "Program Coordinator".equals(academicLeader.getLeaderRole())) ? "selected" : "" %>>Program Coordinator</option>
                            <option value="Deputy Dean" <%= (academicLeader != null && "Deputy Dean".equals(academicLeader.getLeaderRole())) ? "selected" : "" %>>Deputy Dean</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="startDate">Start Date:</label></td>
                    <td><input type="date" id="startDate" name="startDate" value="<%= academicLeader != null ? (academicLeader.getStartDate() != null ? academicLeader.getStartDate() : "") : "" %>" required></td>
                </tr>
                <tr>
                    <td><label for="endDate">End Date:</label></td>
                    <td><input type="date" id="endDate" name="endDate" value="<%= academicLeader != null ? (academicLeader.getEndDate() != null ? academicLeader.getEndDate() : "") : "" %>"></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Update"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
