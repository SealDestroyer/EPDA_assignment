<%-- Document : viewReport Created on : Dec 30, 2025, 12:48:02 PM Author : bohch --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
            <link rel="stylesheet" href="css/adminDashboard.css">
            <link rel="stylesheet" href="css/sidebar.css">
            <link rel="stylesheet" href="css/header.css">
            <link rel="stylesheet" href="css/footer.css">
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
                    <div class="welcome-message">
                        <h2>View Reports</h2>
                        
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>Report ID</th>
                                    <th>Report Name</th>
                                    <th>Description</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>User Summary Report</td>
                                    <td>Summary of all system users including students, lecturers, academic leaders, and administrators</td>
                                    <td>
                                        <button class="btn-generate" onclick="window.location.href='generateUserSummaryReport.jsp?reportId=1&reportName=User%20Summary%20Report'">Generate</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>Class & Enrollment Report</td>
                                    <td>Overview of all classes with student enrollment statistics</td>
                                    <td>
                                        <button class="btn-generate" onclick="window.location.href='generateClassEnrollmentReport.jsp?reportId=2&reportName=Class%20%26%20Enrollment%20Report'">Generate</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>Class Module Offering Report</td>
                                    <td>Summary of module offerings for each class</td>
                                    <td>
                                        <button class="btn-generate" onclick="window.location.href='generateClassModuleReport'">Generate</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>4</td>
                                    <td>Grading Scheme Report</td>
                                    <td>Overview of grading schemes</td>
                                    <td>
                                        <button class="btn-generate" onclick="window.location.href='generateGradingSchemeReport'">Generate</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>5</td>
                                    <td>Academic Leader Workload Report</td>
                                    <td>Summary of academic leader workload</td>
                                    <td>
                                        <button class="btn-generate" onclick="window.location.href='generateAcademicLeaderWorkloadReport'">Generate</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Include Footer -->
                <jsp:include page="footer.jsp" />
            </div>

            <script src="js/adminDashboard.js"></script>
            <script src="js/sidebar.js"></script>
            <script src="js/header.js"></script>
            <script src="js/footer.js"></script>
        </body>

        </html>