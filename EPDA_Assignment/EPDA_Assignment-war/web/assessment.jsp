<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Assessment Management</title>

        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-module.css">
    </head>

    <body>

        <!-- NAVBAR (LECTURER) -->
        <jsp:include page="Lnavbar.jsp" />

        <!-- PAGE CONTENT WRAPPER -->
        <div class="page-container">

            <!-- SEARCH BAR -->
            <div class="search-bar-container">
                <form action="Assessment" method="GET" class="search-form">
                    <input type="hidden" name="action" value="search"/>
                    <input type="hidden" name="moduleID" value="${moduleID}"/>

                    <div class="search-box">
                        <input type="text" name="keyword"
                               placeholder="Search Assessment Name"
                               value="${param.keyword != null ? param.keyword : ''}"/>

                        <button type="submit" class="search-btn">Search</button>
                    </div>
                </form>
            </div>

            <!-- TOP BAR -->
            <div class="table-top-bar">

                <form action="Assessment" method="GET" class="top-btn-form">
                    <input type="hidden" name="action" value="goAdd"/>
                    <input type="hidden" name="moduleID" value="${moduleID}"/>

                    <button type="submit" id="addAssessmentBtn"
                            <c:if test="${totalWeightage >= 100}">disabled</c:if>>
                        + Add Assessment
                    </button>
                </form>

                <form action="Assessment" method="GET" class="top-btn-form">
                    <input type="hidden" name="action" value="list"/>
                    <input type="hidden" name="moduleID" value="${moduleID}"/>
                    <button type="submit">Reset</button>
                </form>

                <!-- TOTAL WEIGHTAGE (RIGHT) -->
                <div class="weightage-badge" id="weightageBadge">
                    Total Weightage: ${totalWeightage} / 100
                </div>

            </div>

            <!-- ERROR MESSAGE -->
            <div id="errorBox">
                <c:if test="${not empty errorMsg}">
                    <div class="alert-msg">
                        ${errorMsg}
                    </div>
                </c:if>
            </div>

            <!-- ASSESSMENT TABLE -->
            <div class="table-container">
                <table class="module-table" border="1" width="100%" cellpadding="8" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Assessment ID</th>
                            <th>Assessment Name</th>
                            <th>Weightage (%)</th>
                            <th>Created By</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <!-- MUST HAVE ID FOR JS -->
                    <tbody id="assessmentTableBody">
                        <c:if test="${empty assessmentList}">
                            <tr>
                                <td colspan="5" style="text-align:center;">
                                    No assessments found.
                                </td>
                            </tr>
                        </c:if>

                        <c:forEach items="${assessmentList}" var="a">
                            <tr>
                                <td>${a.assessmentID}</td>
                                <td>${a.assessmentName}</td>
                                <td>${a.weightage}</td>
                                <td>${a.createdBy}</td>

                                <td class="actions-cell">
                                    <form action="Assessment" method="GET" style="display:inline;">
                                        <input type="hidden" name="action" value="edit"/>
                                        <input type="hidden" name="assessmentID" value="${a.assessmentID}"/>
                                        <input type="hidden" name="moduleID" value="${moduleID}"/>
                                        <button type="submit" class="modify-btn">Modify</button>
                                    </form>

                                    <form action="Assessment" method="POST" style="display:inline;">
                                        <input type="hidden" name="action" value="delete"/>
                                        <input type="hidden" name="assessmentID" value="${a.assessmentID}"/>
                                        <input type="hidden" name="moduleID" value="${moduleID}"/>
                                        <button type="submit" class="delete-btn"
                                                onclick="return confirm('Delete this assessment?');">
                                            Delete
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>

        <!-- ===== REAL-TIME AUTO REFRESH ===== -->
        <script>
            (function () {

                const ctx = "<%= request.getContextPath() %>";
                const moduleID = "${moduleID}";

                function escapeHtml(s) {
                    s = (s == null) ? "" : String(s);
                    return s.replace(/&/g, "&amp;")
                            .replace(/</g, "&lt;")
                            .replace(/>/g, "&gt;")
                            .replace(/"/g, "&quot;")
                            .replace(/'/g, "&#39;");
                }

                function updateWeightageUI(data) {
                    let sum = 0;

                    if (data && data.length) {
                        data.forEach(a => {
                            const w = parseInt(a.weightage, 10);
                            if (!isNaN(w)) sum += w;
                        });
                    }

                    const badge = document.getElementById("weightageBadge");
                    if (badge) badge.textContent = "Total Weightage: " + sum + " / 100";

                    const addBtn = document.getElementById("addAssessmentBtn");
                    if (addBtn) addBtn.disabled = (sum >= 100);
                }

                function refreshAssessmentTable() {

                    fetch(ctx + "/Assessment?action=listJson&moduleID=" + moduleID, {cache: "no-store"})
                            .then(res => res.json())
                            .then(data => {

                                const tbody = document.getElementById("assessmentTableBody");
                                const errorBox = document.getElementById("errorBox");

                                if (!tbody) return;

                                // always update badge + button even if empty
                                updateWeightageUI(data);

                                if (!data || data.length === 0) {
                                    tbody.innerHTML =
                                            "<tr><td colspan='5' style='text-align:center;'>No assessments found.</td></tr>";
                                    return;
                                }

                                if (errorBox) errorBox.innerHTML = "";

                                tbody.innerHTML = data.map(a =>
                                    "<tr>" +
                                        "<td>" + a.assessmentID + "</td>" +
                                        "<td>" + escapeHtml(a.assessmentName) + "</td>" +
                                        "<td>" + a.weightage + "</td>" +
                                        "<td>" + escapeHtml(a.createdBy) + "</td>" +
                                        "<td class='actions-cell'>" +
                                            "<form action='" + ctx + "/Assessment' method='GET' style='display:inline;'>" +
                                                "<input type='hidden' name='action' value='edit'/>" +
                                                "<input type='hidden' name='assessmentID' value='" + a.assessmentID + "'/>" +
                                                "<input type='hidden' name='moduleID' value='" + moduleID + "'/>" +
                                                "<button type='submit' class='modify-btn'>Modify</button>" +
                                            "</form>" +
                                            "<form action='" + ctx + "/Assessment' method='POST' style='display:inline;'>" +
                                                "<input type='hidden' name='action' value='delete'/>" +
                                                "<input type='hidden' name='assessmentID' value='" + a.assessmentID + "'/>" +
                                                "<input type='hidden' name='moduleID' value='" + moduleID + "'/>" +
                                                "<button type='submit' class='delete-btn' " +
                                                    "onclick=\"return confirm('Delete this assessment?');\">" +
                                                    "Delete" +
                                                "</button>" +
                                            "</form>" +
                                        "</td>" +
                                    "</tr>"
                                ).join("");
                            })
                            .catch(() => {
                            });
                }

                // stop refresh in search mode
                const params = new URLSearchParams(window.location.search);
                const action = params.get("action");
                const isSearchMode = (action === "search");

                if (!isSearchMode) {
                    refreshAssessmentTable();
                    setInterval(refreshAssessmentTable, 2000);
                }

            })();
        </script>

    </body>
</html>
