<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Assessment Management</title>

        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-module.css">
        <style>
            /* Make Actions column compact */
            table.module-table th:last-child,
            table.module-table td:last-child {
                width: 200px;        /* adjust if needed */
                text-align: left;
            }
        </style>

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

                <form action="${pageContext.request.contextPath}/Assessment" method="GET" class="top-btn-form">
                    <input type="hidden" name="action" value="goAdd"/>
                    <input type="hidden" name="moduleID" value="<c:out value='${moduleID}'/>"/>

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
                                <td>
                                    <a class="assessment-link"
                                       href="${pageContext.request.contextPath}/Assessment?action=studentList&moduleID=${moduleID}&assessmentID=${a.assessmentID}">
                                        <c:out value="${a.assessmentName}"/>
                                    </a>
                                </td>
                                <td>${a.weightage}</td>
                                <td>${createdByNameMap[a.createdBy]} (${a.createdBy})</td>
                                <td class="actions-cell">
                                    <form action="${pageContext.request.contextPath}/Assessment" method="GET" style="display:inline;">
                                        <input type="hidden" name="action" value="edit"/>
                                        <input type="hidden" name="assessmentID" value="${a.assessmentID}"/>
                                        <input type="hidden" name="moduleID" value="${moduleID}"/>
                                        <button type="submit" class="modify-btn">Modify</button>
                                    </form>

                                    <form action="${pageContext.request.contextPath}/Assessment" method="POST" class="delete-form" style="display:inline;">
                                        <input type="hidden" name="action" value="delete"/>
                                        <input type="hidden" name="assessmentID" value="${a.assessmentID}"/>
                                        <input type="hidden" name="moduleID" value="${moduleID}"/>

                                        <button type="button"
                                                class="delete-btn open-delete-modal"
                                                data-assessmentid="${a.assessmentID}"
                                                data-assessmentname="${a.assessmentName}"
                                                data-weightage="${a.weightage}">
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

        <!-- DELETE CONFIRM MODAL -->
        <div id="deleteModal" class="modal-overlay" style="display:none;">
            <div class="modal-box">
                <h3 class="modal-title">Confirm Delete</h3>

                <p class="modal-text">Do you wish to delete the following assessment?</p>

                <div class="modal-info">
                    <div><strong>Assessment ID:</strong> <span id="dmAssessmentID"></span></div>
                    <div><strong>Assessment Name:</strong> <span id="dmAssessmentName"></span></div>
                    <div><strong>Weightage (%):</strong> <span id="dmWeightage"></span></div>
                </div>

                <div class="modal-actions">
                    <button type="button" id="confirmDeleteBtn" class="modal-btn modal-btn-danger">Delete</button>
                    <button type="button" id="cancelDeleteBtn" class="modal-btn modal-btn-cancel">Cancel</button>
                </div>
            </div>
        </div>

        <!-- ===== REAL-TIME AUTO REFRESH ===== -->
        <script>
            (function () {

                const ctx = "<%= request.getContextPath()%>";
                const moduleID = "<c:out value='${moduleID}'/>";

                let pauseAutoRefresh = false;

                // ====== PAUSE ON ADD CLICK ======
                const addBtn = document.getElementById("addAssessmentBtn");
                if (addBtn) {
                    addBtn.addEventListener("click", function () {
                        pauseAutoRefresh = true;
                    });
                }

                // ====== DELETE MODAL + EVENT DELEGATION ======
                const modal = document.getElementById("deleteModal");
                const dmAssessmentID = document.getElementById("dmAssessmentID");
                const dmAssessmentName = document.getElementById("dmAssessmentName");
                const dmWeightage = document.getElementById("dmWeightage");
                const confirmBtn = document.getElementById("confirmDeleteBtn");
                const cancelBtn = document.getElementById("cancelDeleteBtn");

                let selectedForm = null;

                function closeModal() {
                    if (modal)
                        modal.style.display = "none";
                    selectedForm = null;
                    pauseAutoRefresh = false;
                }

                // Open modal (works for both JSP rows + AJAX rows)
                document.addEventListener("click", function (e) {
                    const btn = e.target.closest(".open-delete-modal");
                    if (!btn)
                        return;

                    if (dmAssessmentID)
                        dmAssessmentID.textContent = btn.dataset.assessmentid || "";
                    if (dmAssessmentName)
                        dmAssessmentName.textContent = btn.dataset.assessmentname || "";
                    if (dmWeightage)
                        dmWeightage.textContent = btn.dataset.weightage || "";

                    selectedForm = btn.closest("form");
                    pauseAutoRefresh = true;

                    if (modal)
                        modal.style.display = "flex";
                });

                // Confirm delete
                if (confirmBtn) {
                    confirmBtn.addEventListener("click", function () {
                        if (selectedForm) {
                            selectedForm.submit();
                        }
                        closeModal();
                    });
                }

                // Cancel delete
                if (cancelBtn)
                    cancelBtn.addEventListener("click", closeModal);

                // Click outside modal -> close
                if (modal) {
                    modal.addEventListener("click", function (e) {
                        if (e.target === modal)
                            closeModal();
                    });
                }

                // ESC -> close
                document.addEventListener("keydown", function (e) {
                    if (e.key === "Escape" && modal && modal.style.display !== "none") {
                        closeModal();
                    }
                });

                // ====== PAUSE ON MODIFY CLICK (optional but good) ======
                document.addEventListener("click", function (e) {
                    if (e.target.closest(".modify-btn")) {
                        pauseAutoRefresh = true;
                    }
                });

                // ====== HELPERS ======
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
                            if (!isNaN(w))
                                sum += w;
                        });
                    }

                    const badge = document.getElementById("weightageBadge");
                    if (badge)
                        badge.textContent = "Total Weightage: " + sum + " / 100";

                    const btn = document.getElementById("addAssessmentBtn");
                    if (btn)
                        btn.disabled = (sum >= 100);
                }

                // ====== AJAX AUTO REFRESH ======
                function refreshAssessmentTable() {

                    if (pauseAutoRefresh)
                        return;
                    if (!moduleID)
                        return;

                    fetch(ctx + "/Assessment?action=listJson&moduleID=" + encodeURIComponent(moduleID), {
                        cache: "no-store",
                        headers: {"Accept": "application/json"}
                    })
                            .then(res => {
                                const ct = res.headers.get("content-type") || "";
                                if (!ct.includes("application/json"))
                                    return null;
                                return res.json();
                            })
                            .then(data => {

                                if (data === null)
                                    return;

                                const tbody = document.getElementById("assessmentTableBody");
                                const errorBox = document.getElementById("errorBox");
                                if (!tbody)
                                    return;

                                updateWeightageUI(data);

                                if (!data || data.length === 0) {
                                    tbody.innerHTML =
                                            "<tr><td colspan='5' style='text-align:center;'>No assessments found.</td></tr>";
                                    return;
                                }

                                if (errorBox)
                                    errorBox.innerHTML = "";

                                tbody.innerHTML = data.map(a =>
                                    "<tr>" +
                                            "<td>" + a.assessmentID + "</td>" +
                                            "<td>" +
                                            "<a class='assessment-link' href='" + ctx + "/Assessment?action=studentList" +
                                            "&moduleID=" + encodeURIComponent(moduleID) +
                                            "&assessmentID=" + encodeURIComponent(a.assessmentID) + "'>" +
                                            escapeHtml(a.assessmentName) +
                                            "</a>" +
                                            "</td>" +
                                            "<td>" + a.weightage + "</td>" +
                                            "<td>" + escapeHtml(a.createdBy) + "</td>" +
                                            "<td class='actions-cell'>" +
                                            // Modify
                                            "<form action='" + ctx + "/Assessment' method='GET' style='display:inline;'>" +
                                            "<input type='hidden' name='action' value='edit'/>" +
                                            "<input type='hidden' name='assessmentID' value='" + a.assessmentID + "'/>" +
                                            "<input type='hidden' name='moduleID' value='" + escapeHtml(moduleID) + "'/>" +
                                            "<button type='submit' class='modify-btn'>Modify</button>" +
                                            "</form>" +
                                            // Delete (Modal)
                                            "<form action='" + ctx + "/Assessment' method='POST' class='delete-form' style='display:inline;'>" +
                                            "<input type='hidden' name='action' value='delete'/>" +
                                            "<input type='hidden' name='assessmentID' value='" + a.assessmentID + "'/>" +
                                            "<input type='hidden' name='moduleID' value='" + escapeHtml(moduleID) + "'/>" +
                                            "<button type='button' class='delete-btn open-delete-modal' " +
                                            "data-assessmentid='" + a.assessmentID + "' " +
                                            "data-assessmentname='" + escapeHtml(a.assessmentName) + "' " +
                                            "data-weightage='" + a.weightage + "'>" +
                                            "Delete" +
                                            "</button>" +
                                            "</form>" +
                                            "</td>" +
                                            "</tr>"
                                ).join("");
                            })
                            .catch(() => {
                                // silent fail
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
