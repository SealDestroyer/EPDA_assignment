<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>AL Analytics</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-analytics.css">
        <script src="https://www.gstatic.com/charts/loader.js"></script>
    </head>

    <body>

        <!-- AL NAVBAR -->
        <jsp:include page="ALnavbar.jsp" />

        <div class="analytics-container">
            <div class="analytics-box">

                <h2 class="analytics-title">Module Performance Analytics</h2>
                <p class="analytics-subtitle">Select a lecturer and module to view real-time performance.</p>

                <div class="analytics-row">
                    <div class="analytics-field analytics-select-wrap">
                        <label class="analytics-label" for="lecturerSelect">Lecturer</label>
                        <select id="lecturerSelect" class="analytics-select" required>
                            <option value="" disabled selected>Select Lecturer</option>
                        </select>
                    </div>

                    <div class="analytics-field analytics-select-wrap">
                        <label class="analytics-label" for="moduleSelect">Module</label>
                        <select id="moduleSelect" class="analytics-select" disabled required>
                            <option value="" disabled selected>Select Module</option>
                        </select>
                    </div>
                </div>

                <div id="chart" class="analytics-chart"></div>
            </div>
        </div>

        <script>
            google.charts.load('current', {'packages': ['corechart']});
            google.charts.setOnLoadCallback(initAnalytics);

            function initAnalytics() {
                const BASE = "${pageContext.request.contextPath}";
                const lecturerSelect = document.getElementById("lecturerSelect");
                const moduleSelect = document.getElementById("moduleSelect");
                const chartDiv = document.getElementById("chart");

                let selectedModuleID = "";
                let chartTimer = null;

                function resetModules() {
                    moduleSelect.innerHTML = '<option value="" disabled selected>Select Module</option>';
                    moduleSelect.disabled = true;
                }

                function clearChart() {
                    chartDiv.innerHTML = "";
                }

                // ================== LOAD LECTURERS ==================
                function loadLecturers() {
                    fetch(BASE + "/ALAnalytics?action=lecturers", {cache: "no-store"})
                            .then(r => r.json())
                            .then(list => {
                                lecturerSelect.innerHTML =
                                        '<option value="" disabled selected>Select Lecturer</option>';

                                if (!list || list.length === 0) {
                                    return;
                                }

                                list.forEach(x => {
                                    const opt = document.createElement("option");
                                    opt.value = x.id;
                                    opt.textContent = (x.name || x.id) + " (" + x.id + ")";
                                    lecturerSelect.appendChild(opt);
                                });
                            })
                            .catch(() => {
                            });
                }

                // ================== LECTURER CHANGE ==================
                lecturerSelect.addEventListener("change", function () {
                    selectedModuleID = "";
                    if (chartTimer)
                        clearInterval(chartTimer);
                    chartTimer = null;

                    resetModules();
                    clearChart();

                    fetch(BASE + "/ALAnalytics?action=modules&lecturerID=" + encodeURIComponent(this.value),
                            {cache: "no-store"})
                            .then(r => r.json())
                            .then(list => {
                                if (!list || list.length === 0) {
                                    return;
                                }

                                list.forEach(m => {
                                    const opt = document.createElement("option");
                                    opt.value = m.id;
                                    opt.textContent = m.code + " - " + m.name;
                                    moduleSelect.appendChild(opt);
                                });

                                moduleSelect.disabled = false;
                            })
                            .catch(() => {
                            });
                });

                // ================== REAL-TIME CHART ==================
                function refreshChart() {
                    if (!selectedModuleID)
                        return;

                    fetch(BASE + "/ALAnalytics?action=avgChart&moduleID=" + encodeURIComponent(selectedModuleID),
                            {cache: "no-store"})
                            .then(r => r.json())
                            .then(dataArr => {
                                if (!dataArr || dataArr.length <= 1) {
                                    clearChart();
                                    return;
                                }
                                drawChart(dataArr);
                            })
                            .catch(() => {
                            });
                }

                moduleSelect.addEventListener("change", function () {
                    selectedModuleID = this.value;

                    refreshChart();

                    if (chartTimer)
                        clearInterval(chartTimer);
                    chartTimer = setInterval(refreshChart, 2000); 
                });

                // ================== DRAW CHART (DARK THEME) ==================
                function drawChart(dataArr) {
                    const data = google.visualization.arrayToDataTable(dataArr);

                    const options = {
                        title: "Average Mark per Assessment",
                        backgroundColor: "transparent",
                        legend: {position: "none"},
                        colors: ["#7a5436"],

                        titleTextStyle: {
                            color: "#ffffff",
                            fontSize: 18,
                            bold: true
                        },

                        chartArea: {
                            left: 70,
                            top: 60,
                            right: 20,
                            bottom: 70, 
                            width: "100%",
                            height: "100%"
                        },

                        vAxis: {
                            minValue: 0,
                            maxValue: 100,
                            textStyle: {color: "#ffffff", fontSize: 12, bold: true}, 
                            gridlines: {color: "rgba(255,255,255,0.18)"},
                            baselineColor: "rgba(255,255,255,0.25)",
                            titleTextStyle: {color: "#ffffff"}
                        },

                        hAxis: {
                            textStyle: {color: "#ffffff", fontSize: 12, bold: true},
                            slantedText: false,
                            gridlines: {color: "transparent"},
                            baselineColor: "rgba(255,255,255,0.25)"
                        },

                        tooltip: {
                            textStyle: {color: "#000"}, 
                            isHtml: false
                        },

                        bar: {groupWidth: "55%"}
                    };

                    new google.visualization.ColumnChart(chartDiv).draw(data, options);
                }

                // ================== INIT ==================
                loadLecturers();
            }
        </script>
    </body>
</html>
