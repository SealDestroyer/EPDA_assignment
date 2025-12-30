<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Class Management</title>
    <link rel="stylesheet" href="css/al-dashboard.css">
    <link rel="stylesheet" href="css/al-module.css">
</head>
<body>

<jsp:include page="ALnavbar.jsp" />

<div class="page-container">

    <!-- SEARCH BAR -->
    <div class="search-bar-container">
        <form action="ALViewClass" method="GET" class="search-form">
            <input type="hidden" name="action" value="search"/>

            <div class="search-box">
                <input type="text" name="keyword"
                       placeholder="Search by Class Name"
                       value="${param.keyword != null ? param.keyword : ''}"/>

                <button type="submit" class="search-btn">Search</button>
            </div>
        </form>
    </div>

    <div class="table-top-bar">
        <form action="ALViewClass" method="GET" class="top-btn-form">
            <input type="hidden" name="action" value="list"/>
            <button type="submit">Reset</button>
        </form>
    </div>

    <c:if test="${not empty errorMsg}">
        <div class="alert-msg">
            ${errorMsg}
        </div>
    </c:if>

    <!-- CLASS TABLE -->
    <div class="table-container">
        <table class="module-table" border="1" width="100%" cellpadding="8" cellspacing="0">
            <thead>
                <tr>
                    <th>Class ID</th>
                    <th>Class Name</th>
                    <th>Semester</th>
                    <th>Academic Year</th>
                    <th>Actions</th>
                </tr>
            </thead>

            <tbody>
                <c:if test="${empty classList}">
                    <tr>
                        <td colspan="5" style="text-align:center;">
                            No classes found.
                        </td>
                    </tr>
                </c:if>

                <c:forEach items="${classList}" var="c">
                    <tr>
                        <td>${c.classID}</td>
                        <td>${c.className}</td>
                        <td>${c.semester}</td>
                        <td>${c.academicYear}</td>

                        <td class="actions-cell">
                            <form action="Module" method="GET" style="display:inline;">
                                <input type="hidden" name="action" value="list"/>
                                <input type="hidden" name="classID" value="${c.classID}"/>
                                <button type="submit" class="modify-btn">Modules</button>
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
