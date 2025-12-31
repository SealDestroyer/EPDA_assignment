<%-- 
    Document   : gradingSchemeReport
    Created on : Dec 30, 2025, 8:56:04 PM
    Author     : bohch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Grading Scheme Report</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages':['corechart', 'bar']});
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['Grade', 'Min Percentage', { role: 'annotation' }, 'Max Percentage', { role: 'annotation' }],
                <%= request.getAttribute("chartData") %>
            ]);

            var options = {
                title: 'Grading Scheme - Percentage Ranges'
            };

            var chart = new google.visualization.BarChart(document.getElementById('barchart'));
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
                <h2>Grading Scheme Report</h2>
                <p><strong>Generated on:</strong> <%= request.getAttribute("generatedDateTime") %></p>
                <div id="barchart" style="width: 100%; height: 500px;"></div>
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
