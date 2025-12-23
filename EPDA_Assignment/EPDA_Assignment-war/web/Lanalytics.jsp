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
        <title>L Analytics</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Use same theme base -->
        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-analytics.css">

        <!-- Google Charts -->
        <script src="https://www.gstatic.com/charts/loader.js"></script>
    </head>

    <body>

        <!-- L NAVBAR -->
        <jsp:include page="Lnavbar.jsp" />

        <div class="analytics-container">
            <div class="analytics-box">

                <h2 class="analytics-title">Module Performance Analytics</h2>
                <p class="analytics-subtitle">Select a module to view real-time performance.</p>

                <div class="analytics-row">
                    <div class="analytics-field analytics-select-wrap" style="width: 100%;">
                        <label class="analytics-label" for="moduleSelect">Module</label>
                        <select id="moduleSelect" class="analytics-select" required>
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
                const moduleSelect = document.getElementById("moduleSelect");
                const chartDiv = document.getElementById("chart");

                let selectedModuleID = "";
                let chartTimer = null;
                let modulesTimer = null; //always refresh dropdown list

                // ===== UI helpers =====
                function clearChart() {
                    chartDiv.innerHTML =
                            '<div class="analytics-empty">No data yet. Select a module to display the chart.</div>';
                }

                function resetSelection() {
                    selectedModuleID = "";
                    moduleSelect.value = ""; // back to placeholder
                    clearChart();

                    // stop chart polling only
                    if (chartTimer)
                        clearInterval(chartTimer);
                    chartTimer = null;
                }

                // ===== LOAD MODULES (and keep/reset selection) =====
                function loadModules(keepSelection) {
                    fetch(BASE + "/LAnalytics?action=modules", {cache: "no-store"})
                            .then(r => r.json())
                            .then(list => {
                                // rebuild dropdown
                                moduleSelect.innerHTML =
                                        '<option value="" disabled selected>Select Module</option>';

                                // no modules at all
                                if (!list || list.length === 0) {
                                    moduleSelect.disabled = true;
                                    resetSelection(); // clears chart + stops chartTimer
                                    return;
                                }

                                moduleSelect.disabled = false;

                                list.forEach(m => {
                                    const opt = document.createElement("option");
                                    opt.value = m.id;
                                    opt.textContent = m.code + " - " + m.name;
                                    moduleSelect.appendChild(opt);
                                });

                                // keep selection if still exists
                                if (keepSelection && selectedModuleID) {
                                    const stillExists = list.some(m => String(m.id) === String(selectedModuleID));
                                    if (!stillExists) {
                                        resetSelection(); // module removed/reassigned -> clear selection
                                    } else {
                                        moduleSelect.value = selectedModuleID; // keep selection
                                    }
                                }
                            })
                            .catch(() => {
                                // silent fail
                            });
                }

                // ===== REAL TIME CHART =====
                function refreshChart() {
                    if (!selectedModuleID)
                        return;

                    fetch(BASE + "/LAnalytics?action=avgChart&moduleID=" + encodeURIComponent(selectedModuleID),
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
                                // silent fail
                            });
                }

                // ===== MODULE CHANGE =====
                moduleSelect.addEventListener("change", function () {
                    selectedModuleID = this.value;

                    refreshChart(); // immediate

                    if (chartTimer)
                        clearInterval(chartTimer);
                    chartTimer = setInterval(refreshChart, 2000);
                });

                // ===== DRAW CHART =====
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

                // ===== INIT =====
                clearChart();
                loadModules(false);

                // always refresh dropdown list every 2s (even when 0 modules)
                if (modulesTimer)
                    clearInterval(modulesTimer);
                modulesTimer = setInterval(() => loadModules(true), 2000);
            }
        </script>
    </body>
</html>
