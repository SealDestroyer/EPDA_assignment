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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Grade - Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/gradeProfile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <!-- Include Sidebar -->
    <jsp:include page="sidebar.jsp" />
    
    <!-- Main Content -->
    <div class="main-content">
        <!-- Include Header -->
        <jsp:include page="header.jsp" />
        
        <div class="content-area" id="content-area">
            <%
                // Get the ID parameter
                String gradeId = request.getParameter("gradingId");
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
            <% if (request.getAttribute("message") != null) { %>
                <p style="color: green; font-weight: bold; text-align: center;"><%= request.getAttribute("message") %></p>
            <% } %>
            <form action="updateGrade" method="post" onsubmit="return validateForm()">
                <table class="profile-table">
                    <tr>
                        <td colspan="2">
                            <h2 class="form-title">UPDATE GRADE</h2>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="gradeId">Grade ID:</label></td>
                        <td><input type="text" id="gradeId" name="gradeId" value="<%= gradeId != null ? gradeId : "" %>" readonly required></td>
                    </tr>
                    <tr>
                        <td><label for="gradeLetter">Grade Letter:</label></td>
                        <td><input type="text" id="gradeLetter" name="gradeLetter" value="<%= grade != null ? grade.getGradeLetter() : "" %>" onblur="validateGradeLetter()" required></td>
                    </tr>
                    <tr class="error-row" id="gradeLetter-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="gradeLetter-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="minPercentage">Min Percentage:</label></td>
                        <td><input type="text" id="minPercentage" name="minPercentage" value="<%= grade != null ? grade.getMinPercentage() : "" %>" onblur="validateMinPercentage()" required></td>
                    </tr>
                    <tr class="error-row" id="minPercentage-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="minPercentage-error-message"></span></td>
                    </tr>
                    <tr>
                        <td><label for="maxPercentage">Max Percentage:</label></td>
                        <td><input type="text" id="maxPercentage" name="maxPercentage" value="<%= grade != null ? grade.getMaxPercentage() : "" %>" onblur="validateMaxPercentage()" required></td>
                    </tr>
                    <tr class="error-row" id="maxPercentage-error" style="display: none;">
                        <td></td>
                        <td><span class="error-message" id="maxPercentage-error-message"></span></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center; padding-top: 20px;">
                            <button type="submit" class="btn-update">Update</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        
        <!-- Include Footer -->
        <jsp:include page="footer.jsp" />
    </div>
    
    <script src="js/adminDashboard.js"></script>
    <script src="js/sidebar.js"></script>
    <script src="js/header.js"></script>
    <script src="js/footer.js"></script>
    <script src="js/gradeProfile.js"></script>
</body>
</html>
