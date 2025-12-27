<!-- Sidebar -->
<div class="sidebar">
    <div class="sidebar-header">
        <p>Welcome back, <strong>Administrator</strong></p>
    </div>
    
    <nav class="sidebar-nav">
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
    </nav>
    
    <div class="sidebar-footer">
        <a href="<%= request.getContextPath() %>/Logout" class="logout-btn">
            <i class="fas fa-sign-out-alt"></i>
            <span>Logout</span>
        </a>
    </div>
</div>
