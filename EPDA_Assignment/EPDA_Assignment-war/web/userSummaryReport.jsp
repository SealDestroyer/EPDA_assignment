<%-- 
    Document   : userSummaryReport
    Created on : Dec 30, 2025, 8:56:04 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Summary Report</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            var hasData = <%= request.getAttribute("hasData") %>;
            
            if (!hasData) {
                document.getElementById('piechart').innerHTML = '<p style="text-align: center; padding: 50px;">No user data found for the selected range.</p>';
                return;
            }

            var data = google.visualization.arrayToDataTable([
                ['User Role', 'Count'],
                <%= request.getAttribute("chartData") %>
            ]);

            var options = {
                title: '<%= request.getAttribute("reportName") %> (<%= request.getAttribute("startDate") %> to <%= request.getAttribute("endDate") %>)'
            };

            var chart = new google.visualization.PieChart(document.getElementById('piechart'));
            chart.draw(data, options);
        }
    </script>
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
                <h2><%= request.getAttribute("reportName") %> (<%= request.getAttribute("startDate") %> to <%= request.getAttribute("endDate") %>)</h2>
                <p><strong>Generated on:</strong> <%= request.getAttribute("generatedDateTime") %></p>
                <div id="piechart" style="width: 100%; height: 500px;"></div>
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
