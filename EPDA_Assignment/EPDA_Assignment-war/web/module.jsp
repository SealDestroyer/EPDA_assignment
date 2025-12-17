<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Module Management</title>

        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-module.css">

    </head>

    <body>

        <!-- NAVBAR -->
        <jsp:include page="ALnavbar.jsp" />

        <!-- PAGE CONTENT WRAPPER -->
        <div class="page-container">

            <!-- SEARCH BAR (TOP MIDDLE) -->
            <div class="search-bar-container">
                <form action="Module" method="GET" class="search-form">
                    <input type="hidden" name="action" value="search"/>

                    <div class="search-box">
                        <input type="text" name="keyword"
                               placeholder="Search by  Module Code / Name"
                               value="${param.keyword != null ? param.keyword : ''}"/>

                        <button type="submit" class="search-btn">Search</button>
                    </div>

                </form>
            </div>

            <div class="table-top-bar">
                <form action="Module" method="GET" class="top-btn-form">
                    <input type="hidden" name="action" value="goAdd"/>
                    <button type="submit">+ Add New Module</button>
                </form>

                <form action="Module" method="GET" class="top-btn-form">
                    <input type="hidden" name="action" value="list"/>
                    <button type="submit">Reset</button>
                </form>
            </div>

            <c:if test="${not empty errorMsg}">
                <div class="alert-msg">
                    ${errorMsg}
                </div>
            </c:if>

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
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:if test="${empty moduleList}">
                            <tr>
                                <td colspan="7" style="text-align:center;">
                                    No modules found.
                                </td>
                            </tr>
                        </c:if>

                        <c:forEach items="${moduleList}" var="m">
                            <tr>
                                <td>${m.moduleID}</td>
                                <td>${m.moduleName}</td>
                                <td>${m.moduleCode}</td>
                                <td>${m.description}</td>
                                <td>${userNameMap[m.createdBy]} (${m.createdBy})</td>
                                <td>${userNameMap[m.assignedLecturerID]} (${m.assignedLecturerID})</td>

                                <td class="actions-cell">
                                    <form action="Module" method="GET" style="display:inline;">
                                        <input type="hidden" name="action" value="edit"/>
                                        <input type="hidden" name="moduleID" value="${m.moduleID}"/>
                                        <button type="submit" class="modify-btn">Modify</button>
                                    </form>

                                    <form action="Module" method="POST" class="delete-form" style="display:inline;">
                                        <input type="hidden" name="action" value="delete"/>
                                        <input type="hidden" name="moduleID" value="${m.moduleID}"/>

                                        <button type="button"
                                                class="delete-btn open-delete-modal"
                                                data-moduleid="${m.moduleID}"
                                                data-modulename="${m.moduleName}">
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

                <p class="modal-text">Do you wish to delete the following module?</p>

                <div class="modal-info">
                    <div><strong>Module ID:</strong> <span id="dmModuleID"></span></div>
                    <div><strong>Module Name:</strong> <span id="dmModuleName"></span></div>
                </div>

                <div class="modal-actions">
                    <button type="button" id="confirmDeleteBtn" class="modal-btn modal-btn-danger">Delete</button>
                    <button type="button" id="cancelDeleteBtn" class="modal-btn modal-btn-cancel">Cancel</button>
                </div>
            </div>
        </div>
        <script>
            (function () {
                const modal = document.getElementById("deleteModal");
                const dmModuleID = document.getElementById("dmModuleID");
                const dmModuleName = document.getElementById("dmModuleName");
                const confirmBtn = document.getElementById("confirmDeleteBtn");
                const cancelBtn = document.getElementById("cancelDeleteBtn");

                let selectedForm = null;

                // open modal
                document.querySelectorAll(".open-delete-modal").forEach(btn => {
                    btn.addEventListener("click", function () {
                        const moduleId = this.dataset.moduleid;
                        const moduleName = this.dataset.modulename;

                        dmModuleID.textContent = moduleId;
                        dmModuleName.textContent = moduleName;

                        selectedForm = this.closest("form");
                        modal.style.display = "flex";
                    });
                });

                // confirm delete -> submit the correct form
                confirmBtn.addEventListener("click", function () {
                    if (selectedForm) {
                        selectedForm.submit();
                    }
                });

                // cancel -> close modal
                function closeModal() {
                    modal.style.display = "none";
                    selectedForm = null;
                }

                cancelBtn.addEventListener("click", closeModal);

                // click outside box closes modal
                modal.addEventListener("click", function (e) {
                    if (e.target === modal)
                        closeModal();
                });

                // ESC closes modal
                document.addEventListener("keydown", function (e) {
                    if (e.key === "Escape" && modal.style.display !== "none") {
                        closeModal();
                    }
                });
            })();
        </script>
    </body>
</html>
