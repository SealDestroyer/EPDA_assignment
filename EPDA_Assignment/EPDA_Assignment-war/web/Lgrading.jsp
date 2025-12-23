<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Grading System</title>

        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-module.css">
    </head>

    <body>

        <!-- NAVBAR -->
        <jsp:include page="Lnavbar.jsp" />

        <!-- PAGE CONTENT WRAPPER -->
        <div class="page-container">

            <!-- ERROR MESSAGE (OPTIONAL) -->
            <div id="errorBox">
                <c:if test="${not empty errorMsg}">
                    <div class="alert-msg">
                        ${errorMsg}
                    </div>
                </c:if>
            </div>

            <!-- GRADING TABLE -->
            <div class="table-container">

                <table class="module-table" border="1" width="100%" cellpadding="8" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Grade</th>
                            <th>Min Percentage</th>
                            <th>Max Percentage</th>
                        </tr>
                    </thead>

                    <!-- ✅ MUST HAVE ID for JS refresh later -->
                    <tbody id="gradingTableBody">

                        <c:if test="${empty gradingList}">
                            <tr>
                                <td colspan="3" style="text-align:center;">
                                    No grading data found.
                                </td>
                            </tr>
                        </c:if>

                        <c:forEach items="${gradingList}" var="g">
                            <tr>
                                <td>${g.gradeLetter}</td>
                                <td>${g.minPercentage}</td>
                                <td>${g.maxPercentage}</td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>

            </div>

        </div>

        <script>
            (function () {

                // ===== CONTEXT PATH (IMPORTANT) =====
                const ctx = "<%= request.getContextPath()%>";

                function escapeHtml(s) {
                    s = (s == null) ? "" : String(s);
                    return s.replace(/&/g, "&amp;")
                            .replace(/</g, "&lt;")
                            .replace(/>/g, "&gt;")
                            .replace(/"/g, "&quot;")
                            .replace(/'/g, "&#39;");
                }

                function refreshGradingTable() {

                    fetch(ctx + "/LGrading?action=listJson", {cache: "no-store"})
                            .then(res => res.json())
                            .then(data => {

                                const tbody = document.getElementById("gradingTableBody");
                                const errorBox = document.getElementById("errorBox");
                                if (!tbody)
                                    return;

                                // ===== NO DATA =====
                                if (!data || data.length === 0) {
                                    tbody.innerHTML =
                                            "<tr>" +
                                            "<td colspan='3' style='text-align:center;'>No grading data found.</td>" +
                                            "</tr>";

                                    if (errorBox) {
                                        errorBox.innerHTML =
                                                "<div class='alert-msg'>No grading data available.</div>";
                                    }
                                    return;
                                }

                                // ===== DATA FOUND =====
                                if (errorBox)
                                    errorBox.innerHTML = "";

                                tbody.innerHTML = data.map(g =>
                                    "<tr>" +
                                            "<td>" + escapeHtml(g.gradeLetter) + "</td>" +
                                            "<td>" + escapeHtml(g.minPercentage) + "</td>" +
                                            "<td>" + escapeHtml(g.maxPercentage) + "</td>" +
                                            "</tr>"
                                ).join("");
                            })
                            .catch(() => {
                                // silent fail
                            });
                }

                const intervalMs = 2000;
                refreshGradingTable();                 // run once immediately
                setInterval(refreshGradingTable, intervalMs);

            })();
        </script>

    </body>
</html>
