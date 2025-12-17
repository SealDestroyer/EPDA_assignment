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
                               placeholder="Search by Module ID / Code / Name"
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
                                <td>${m.createdBy}</td>
                                <td>${m.assignedLecturerID}</td>

                                <td class="actions-cell">
                                    <form action="Module" method="GET" style="display:inline;">
                                        <input type="hidden" name="action" value="edit"/>
                                        <input type="hidden" name="moduleID" value="${m.moduleID}"/>
                                        <button type="submit" class="modify-btn">Modify</button>
                                    </form>

                                    <form action="Module" method="POST" style="display:inline;">
                                        <input type="hidden" name="action" value="delete"/>
                                        <input type="hidden" name="moduleID" value="${m.moduleID}"/>
                                        <button type="submit" class="delete-btn"
                                                onclick="return confirm('Delete this module?');">
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
    </body>
</html>
