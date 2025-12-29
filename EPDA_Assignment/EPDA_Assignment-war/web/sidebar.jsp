<%@ page import="model.MyUsers" %>
<%@ page import="model.MyAdminFacade" %>
<%@ page import="model.MyAdmin" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<!-- Sidebar -->
<div class="sidebar">
    <div class="sidebar-header">
        <%
            MyUsers user = (MyUsers) session.getAttribute("user");
            String userName = "Guest";
            String positionTitle = "";
            
            
            if (user != null) {
                userName = user.getFullName();
                try {
                    InitialContext ctx = new InitialContext();
                    MyAdminFacade myAdminFacade = (MyAdminFacade) ctx.lookup("java:global/EPDA_Assignment/EPDA_Assignment-ejb/MyAdminFacade");
                    if (myAdminFacade != null) {
                        MyAdmin admin = myAdminFacade.findByUserId(user.getUserID());
                        if (admin != null) {
                            positionTitle = admin.getPositionTitle();
                        }
                    }
                } catch (NamingException e) {
                    userName = "EJB lookup failed: " + e.getMessage();
                }
            }
            
        %>
        <p>Welcome back, <strong><%= userName %></strong></p>
    </div>
    
    <nav class="sidebar-nav">
        <% if ("SuperAdmin".equals(positionTitle)) { %>
        <a href="viewAdmin.jsp" class="nav-item">
            <i class="fas fa-user-shield"></i>
            <span>Admin</span>
        </a>
        <% } %>
        <% if ("Admin".equals(positionTitle)) { %>
        <a href="viewStudent.jsp" class="nav-item">
            <i class="fas fa-user-graduate"></i>
            <span>Students</span>
        </a>
        <a href="viewAcademicLeaders.jsp" class="nav-item">
            <i class="fas fa-user-tie"></i>
            <span>Academic Leaders</span>
        </a>
        <a href="viewLecturers.jsp" class="nav-item">
            <i class="fas fa-chalkboard-teacher"></i>
            <span>Lecturers</span>
        </a>
        <a href="viewClass.jsp" class="nav-item">
            <i class="fas fa-book"></i>
            <span>Classes</span>
        </a>
        <a href="viewGrade.jsp" class="nav-item">
            <i class="fas fa-clipboard-check"></i>
            <span>Grading System</span>
        </a>
        <a href="viewReport.jsp" class="nav-item">
            <i class="fas fa-file-alt"></i>
            <span>Reports</span>
        </a>
        <% } %>
    </nav>
    
    <div class="sidebar-footer">
        <a href="<%= request.getContextPath() %>/Logout" class="logout-btn">
            <i class="fas fa-sign-out-alt"></i>
            <span>Logout</span>
        </a>
    </div>
</div>
