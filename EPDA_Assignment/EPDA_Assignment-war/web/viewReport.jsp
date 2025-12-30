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
                        
                        <!-- Add search bar and clear button -->
                        <div class='search-container'>
                            <form method='GET' action='viewReport.jsp' class='search-form'>
                                <input type='text' name='search' id='searchInput' placeholder='Search by report name or description...' 
                                value='<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>' 
                                class='search-input' />
                                <button type='submit' class='btn-search'>Search</button>
                                <button type='button' onclick='window.location.href="viewReport.jsp"' class='btn-clear'>Clear</button>
                            </form>
                        </div>
                        
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
                                    <td>Summary of all users in the system including students, lecturers, and administrators</td>
                                    <td>
                                        <button class="btn-generate" onclick="window.location.href='generateUserSummaryReport.jsp?reportId=1&reportName=User%20Summary%20Report'">Generate</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>Class & Enrollment Report</td>
                                    <td>Overview of all classes and student enrollment statistics</td>
                                    <td>
                                        <button class="btn-generate" onclick="window.location.href='generateUserSummaryReport.jsp?reportId=2&reportName=Class%20%26%20Enrollment%20Report'">Generate</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>Module Offering Report</td>
                                    <td>Details of all available modules and their offerings</td>
                                    <td>
                                        <button class="btn-generate" onclick="window.location.href='generateUserSummaryReport.jsp?reportId=3&reportName=Module%20Offering%20Report'">Generate</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>4</td>
                                    <td>Grading Scheme Report</td>
                                    <td>Comprehensive overview of grading schemes and assessment criteria</td>
                                    <td>
                                        <button class="btn-generate" onclick="window.location.href='generateUserSummaryReport.jsp?reportId=4&reportName=Grading%20Scheme%20Report'">Generate</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>5</td>
                                    <td>Lecturer Workload Report</td>
                                    <td>Analysis of lecturer assignments and workload distribution</td>
                                    <td>
                                        <button class="btn-generate" onclick="window.location.href='generateUserSummaryReport.jsp?reportId=5&reportName=Lecturer%20Workload%20Report'">Generate</button>
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