<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Student Assessment List</title>

        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-module.css">

        <style>
            .student-cell { word-break: break-word; }
            button:disabled {
                opacity: 0.5;
                cursor: not-allowed;
            }
            .assessment-badge {
                margin-left: 3px;           
                padding: 10px 18px;
                border-radius: 8px;
                border: 2px solid #c8b7a6;
                background: rgba(255, 255, 255, 0.55);
                color: #5a3e2b;
                font-weight: bold;
                backdrop-filter: blur(6px);
                -webkit-backdrop-filter: blur(6px);
            }
            .back-btn-right {
                margin-left: auto;
            }
        </style>
    </head>

    <body>

        <jsp:include page="Lnavbar.jsp" />

        <div class="page-container">

            <!-- SEARCH BAR -->
            <div class="search-bar-container">
                <form action="${pageContext.request.contextPath}/Assessment" method="GET" class="search-form">
                    <input type="hidden" name="action" value="studentList"/>
                    <input type="hidden" name="moduleID" value="${moduleID}"/>
                    <input type="hidden" name="assessmentID" value="${assessmentID}"/>

                    <div class="search-box">
                        <input type="text" name="keyword"
                               placeholder="Search Student ID / Name"
                               value="${param.keyword != null ? param.keyword : ''}"/>
                        <button type="submit" class="search-btn">Search</button>
                    </div>
                </form>
            </div>

            <!-- TOP BAR -->
            <div class="table-top-bar">
                <form action="${pageContext.request.contextPath}/Assessment" method="GET">
                    <input type="hidden" name="action" value="studentList"/>
                    <input type="hidden" name="moduleID" value="${moduleID}"/>
                    <input type="hidden" name="assessmentID" value="${assessmentID}"/>

                    <button type="submit">Reset</button>
                </form>

                <div class="assessment-badge">
                    <c:out value="${assessmentName}"/>
                </div>

                <form class="back-btn-right" action="${pageContext.request.contextPath}/Assessment" method="GET">
                    <input type="hidden" name="action" value="list"/>
                    <input type="hidden" name="moduleID" value="${moduleID}"/>
                    <button type="submit">Back</button>
                </form>

            </div>

            <!-- ERROR -->
            <c:if test="${not empty errorMsg}">
                <div class="alert-msg">
                    <c:out value="${errorMsg}"/>
                </div>
            </c:if>

            <!-- TABLE -->
            <div class="table-container">
                <table class="module-table" width="100%">
                    <thead>
                        <tr>
                            <th>Student</th>
                            <th>Mark</th>
                            <th>Grade</th>
                            <th>Date Assessed</th>
                            <th>Feedback</th>
                            <th>Assessed By</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:if test="${empty studentList}">
                            <tr>
                                <td colspan="7" style="text-align:center;">No students found.</td>
                            </tr>
                        </c:if>

                        <c:forEach items="${studentList}" var="s">
                            <tr>
                                <td class="student-cell">${s[0]} - ${s[1]}</td>

                                <td>${empty s[3] ? "-" : s[3]}</td>
                                <td>${empty s[6] ? "-" : s[6]}</td>
                                <td>${empty s[4] ? "-" : s[4]}</td>
                                <td class="student-cell">${empty s[5] ? "-" : s[5]}</td>
                                <td class="student-cell">
                                    ${empty s[7] ? "-" : s[7]} - ${empty s[8] ? "" : s[8]}
                                </td>

                                <td class="actions-cell">

                                    <!-- ADD -->
                                    <form action="${pageContext.request.contextPath}/StudentAssessment" method="GET" style="display:inline;">
                                        <input type="hidden" name="action" value="goAdd"/>
                                        <input type="hidden" name="assessmentID" value="${assessmentID}"/>
                                        <input type="hidden" name="studentID" value="${s[0]}"/>
                                        <button class="sa-add-btn" ${not empty s[2] ? "disabled" : ""}>Add</button>
                                    </form>

                                    <!-- UPDATE -->
                                    <form action="${pageContext.request.contextPath}/StudentAssessment" method="GET" style="display:inline;">
                                        <input type="hidden" name="action" value="edit"/>
                                        <input type="hidden" name="studentAssessmentID" value="${s[2]}"/>
                                        <input type="hidden" name="assessmentID" value="${assessmentID}"/>
                                        <button class="sa-update-btn" ${empty s[2] ? "disabled" : ""}>Update</button>
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
