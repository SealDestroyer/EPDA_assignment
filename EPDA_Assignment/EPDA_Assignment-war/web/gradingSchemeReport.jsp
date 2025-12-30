<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.MyGrading"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Grading Scheme Report</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {packages: ['corechart', 'bar']});
        google.charts.setOnLoadCallback(drawCharts);

        function drawCharts() {
            drawBarChart();
            drawRangeChart();
        }

        function drawBarChart() {
            var data = google.visualization.arrayToDataTable([
                ['Grade', 'Min Percentage', 'Max Percentage', { role: 'annotation' }],
                <%
                    List<MyGrading> gradingList = (List<MyGrading>) request.getAttribute("gradingList");
                    if (gradingList != null && !gradingList.isEmpty()) {
                        for (int i = 0; i < gradingList.size(); i++) {
                            MyGrading grade = gradingList.get(i);
                            out.print("['Grade " + grade.getGradeLetter() + "', " 
                                + grade.getMinPercentage() + ", " 
                                + grade.getMaxPercentage() + ", '" 
                                + grade.getMinPercentage() + "-" 
                                + grade.getMaxPercentage() + "%']");
                            if (i < gradingList.size() - 1) {
                                out.print(",\n                ");
                            }
                        }
                    }
                %>
            ]);

            var options = {
                title: 'Grading Scheme - Percentage Ranges',
                chartArea: {width: '60%'},
                hAxis: {
                    title: 'Percentage',
                    minValue: 0,
                    maxValue: 100
                },
                vAxis: {
                    title: 'Grade Letter'
                },
                legend: { position: 'top', maxLines: 3 },
                bar: { groupWidth: '75%' },
                isStacked: false,
                colors: ['#4CAF50', '#2196F3']
            };

            var chart = new google.visualization.BarChart(document.getElementById('bar_chart_div'));
            chart.draw(data, options);
        }

        function drawRangeChart() {
            var data = google.visualization.arrayToDataTable([
                ['Grade', 'Percentage Range'],
                <%
                    if (gradingList != null && !gradingList.isEmpty()) {
                        for (int i = 0; i < gradingList.size(); i++) {
                            MyGrading grade = gradingList.get(i);
                            // Calculate midpoint for display
                            int midpoint = (grade.getMinPercentage() + grade.getMaxPercentage()) / 2;
                            out.print("['Grade " + grade.getGradeLetter() + "', " + midpoint + "]");
                            if (i < gradingList.size() - 1) {
                                out.print(",\n                ");
                            }
                        }
                    }
                %>
            ]);

            var options = {
                title: 'Grading Distribution',
                chartArea: {width: '50%'},
                hAxis: {
                    title: 'Average Percentage',
                    minValue: 0,
                    maxValue: 100
                },
                vAxis: {
                    title: 'Grade Letter'
                },
                colors: ['#FF9800']
            };

            var chart = new google.visualization.BarChart(document.getElementById('range_chart_div'));
            chart.draw(data, options);
        }

        // Print function
        function printReport() {
            window.print();
        }
    </script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .report-container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        .report-header {
            text-align: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        .report-header h1 {
            color: #333;
            margin: 0 0 10px 0;
        }
        .report-header p {
            color: #666;
            margin: 5px 0;
        }
        .chart-section {
            margin: 30px 0;
        }
        .chart-container {
            margin: 20px 0;
            padding: 20px;
            background-color: #fafafa;
            border-radius: 8px;
        }
        .data-table {
            width: 100%;
            border-collapse: collapse;
            margin: 30px 0;
        }
        .data-table th {
            background-color: #4CAF50;
            color: white;
            padding: 12px;
            text-align: left;
            font-weight: bold;
        }
        .data-table td {
            padding: 10px 12px;
            border-bottom: 1px solid #ddd;
        }
        .data-table tr:hover {
            background-color: #f5f5f5;
        }
        .action-buttons {
            margin: 20px 0;
            text-align: center;
        }
        .btn {
            padding: 10px 20px;
            margin: 0 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }
        .btn-print {
            background-color: #4CAF50;
            color: white;
        }
        .btn-print:hover {
            background-color: #45a049;
        }
        .btn-back {
            background-color: #757575;
            color: white;
        }
        .btn-back:hover {
            background-color: #616161;
        }
        .error-message {
            background-color: #ffebee;
            color: #c62828;
            padding: 15px;
            border-radius: 4px;
            margin: 20px 0;
            border-left: 4px solid #c62828;
        }
        @media print {
            .action-buttons {
                display: none;
            }
            body {
                background-color: white;
            }
            .report-container {
                box-shadow: none;
            }
        }
    </style>
</head>
<body>
    <div class="report-container">
        <div class="report-header">
            <h1><i class="fas fa-chart-bar"></i> Grading Scheme Report</h1>
            <p>Comprehensive Overview of Grading Schemes and Assessment Criteria</p>
            <p><strong>Generated on:</strong> <%= new java.text.SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(new java.util.Date()) %></p>
        </div>

        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
            <div class="error-message">
                <i class="fas fa-exclamation-triangle"></i> <%= errorMessage %>
            </div>
        <% } %>

        <div class="action-buttons">
            <button class="btn btn-print" onclick="printReport()">
                <i class="fas fa-print"></i> Print Report
            </button>
            <button class="btn btn-back" onclick="window.location.href='viewReport.jsp'">
                <i class="fas fa-arrow-left"></i> Back to Reports
            </button>
        </div>

        <!-- Bar Chart Section -->
        <div class="chart-section">
            <h2><i class="fas fa-chart-bar"></i> Percentage Range Visualization</h2>
            <div class="chart-container">
                <div id="bar_chart_div" style="width: 100%; height: 400px;"></div>
            </div>
        </div>

        <!-- Range Chart Section -->
        <div class="chart-section">
            <h2><i class="fas fa-chart-line"></i> Grade Distribution</h2>
            <div class="chart-container">
                <div id="range_chart_div" style="width: 100%; height: 400px;"></div>
            </div>
        </div>

        <!-- Data Table Section -->
        <div class="chart-section">
            <h2><i class="fas fa-table"></i> Grading Scheme Details</h2>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Grade Letter</th>
                        <th>Minimum Percentage</th>
                        <th>Maximum Percentage</th>
                        <th>Range</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (gradingList != null && !gradingList.isEmpty()) {
                            for (MyGrading grade : gradingList) {
                    %>
                    <tr>
                        <td><strong><%= grade.getGradeLetter() %></strong></td>
                        <td><%= grade.getMinPercentage() %>%</td>
                        <td><%= grade.getMaxPercentage() %>%</td>
                        <td><%= grade.getMaxPercentage() - grade.getMinPercentage() %>%</td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="4" style="text-align: center; color: #999;">
                            No grading data available
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>

        <div style="margin-top: 30px; padding-top: 20px; border-top: 1px solid #ddd; text-align: center; color: #666;">
            <p><small>This report was automatically generated by the EPDA Assignment System</small></p>
        </div>
    </div>
</body>
</html>
