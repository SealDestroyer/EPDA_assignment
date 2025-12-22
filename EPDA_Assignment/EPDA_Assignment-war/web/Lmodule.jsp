<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>My Modules</title>

        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-module.css">
    </head>

    <body>

        <!-- NAVBAR -->
        <jsp:include page="Lnavbar.jsp" />

        <!-- PAGE CONTENT WRAPPER -->
        <div class="page-container">

            <!-- SEARCH BAR (TOP MIDDLE) -->
            <div class="search-bar-container">
                <form action="LModule" method="GET" class="search-form">
                    <input type="hidden" name="action" value="search"/>

                    <div class="search-box">
                        <input type="text" name="keyword"
                               placeholder="Search by Module Code / Name"
                               value="${param.keyword != null ? param.keyword : ''}"/>

                        <button type="submit" class="search-btn">Search</button>
                    </div>
                </form>
            </div>

            <!-- TOP BAR (ONLY RESET for lecturer) -->
            <div class="table-top-bar">
                <form action="LModule" method="GET" class="top-btn-form">
                    <input type="hidden" name="action" value="list"/>
                    <button type="submit">Reset</button>
                </form>
            </div>

            <!-- ERROR MESSAGE (OPTIONAL) -->
            <div id="errorBox">
                <c:if test="${not empty errorMsg}">
                    <div class="alert-msg">
                        ${errorMsg}
                    </div>
                </c:if>
            </div>

            <!-- MODULE TABLE -->
            <div class="table-container">

                <table class="module-table" border="1" width="100%" cellpadding="8" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Module ID</th>
                            <th>Module Name</th>
                            <th>Module Code</th>
                            <th>Description</th>
                            <th>Created By</th>
                            <th>Assigned Lecturer ID</th>
                        </tr>
                    </thead>

                    <!-- ✅ MUST HAVE ID for JS refresh -->
                    <tbody id="moduleTableBody">
                        <c:if test="${empty moduleList}">
                            <tr>
                                <td colspan="6" style="text-align:center;">
                                    No modules found.
                                </td>
                            </tr>
                        </c:if>

                        <c:forEach items="${moduleList}" var="m">
                            <tr>
                                <td>${m.moduleID}</td>

                                <td>
                                    <a class="assessment-link"
                                       href="${pageContext.request.contextPath}/Assessment?action=list&moduleID=${m.moduleID}">
                                        ${m.moduleName}
                                    </a>

                                </td>

                                <td>${m.moduleCode}</td>
                                <td>${m.description}</td>
                                <td>${userNameMap[m.createdBy]} (${m.createdBy})</td>
                                <td>${userNameMap[m.assignedLecturerID]} (${m.assignedLecturerID})</td>
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

                // ====== AJAX AUTO REFRESH (LECTURER) ======
                function escapeHtml(s) {
                    s = (s == null) ? "" : String(s);
                    return s.replace(/&/g, "&amp;")
                            .replace(/</g, "&lt;")
                            .replace(/>/g, "&gt;")
                            .replace(/"/g, "&quot;")
                            .replace(/'/g, "&#39;");
                }

                function refreshModuleTable() {

                    fetch(ctx + "/LModule?action=listJson", {cache: "no-store"})
                            .then(res => res.json())
                            .then(data => {

                                const tbody = document.getElementById("moduleTableBody");
                                const errorBox = document.getElementById("errorBox");

                                if (!tbody)
                                    return;

                                // ===== NO DATA =====
                                if (!data || data.length === 0) {

                                    tbody.innerHTML =
                                            "<tr>" +
                                            "<td colspan='6' style='text-align:center;'>No modules found.</td>" +
                                            "</tr>";

                                    if (errorBox) {
                                        errorBox.innerHTML =
                                                "<div class='alert-msg'>No modules have been assigned to you.</div>";
                                    }
                                    return;
                                }

                                // ===== DATA FOUND =====
                                if (errorBox)
                                    errorBox.innerHTML = "";

                                tbody.innerHTML = data.map(m =>
                                    "<tr>" +
                                            "<td>" + m.moduleID + "</td>" +
                                            "<td>" +
                                            "<a class='assessment-link' href='" + ctx + "/Assessment?action=list&moduleID=" + m.moduleID + "'>" +
                                            escapeHtml(m.moduleName) +
                                            "</a>"+
                                            "</td>" +
                                            "<td>" + escapeHtml(m.moduleCode) + "</td>" +
                                            "<td>" + escapeHtml(m.description) + "</td>" +
                                            "<td>" + escapeHtml(m.createdBy) + "</td>" +
                                            "<td>" + escapeHtml(m.lecturer) + "</td>" +
                                            "</tr>"
                                ).join("");
                            })
                            .catch(() => {
                                // silent fail (same behavior as your original)
                            });
                }

                // ===== SEARCH MODE CHECK =====
                const params = new URLSearchParams(window.location.search);
                const action = params.get("action");
                const isSearchMode = (action === "search");

                const intervalMs = 2000;

                if (!isSearchMode) {
                    refreshModuleTable();                 // run once immediately
                    setInterval(refreshModuleTable, intervalMs);
                }

            })();
        </script>
    </body>
</html>
