<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Mark</title>

        <!-- SAME CSS USED IN ADD MODULE / ADD ASSESSMENT -->
        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-addmodule.css">
    </head>

    <body>
        <!-- Lecturer navbar -->
        <jsp:include page="Lnavbar.jsp" />

        <div class="add-container">
            <div class="add-box">
                <h2 class="add-title">Add Student Mark</h2>
                <p class="add-subtitle">Enter the assessment mark and feedback below.</p>

                <form action="${pageContext.request.contextPath}/StudentAssessment"
                      method="POST" novalidate>

                    <input type="hidden" name="action" value="add" />

                    <!-- IDs used for backend insert -->
                    <input type="hidden" name="assessmentID"
                           value="<c:out value='${assessmentID}'/>" />

                    <input type="hidden" name="studentID"
                           value="<c:out value='${studentID}'/>" />

                    <input type="hidden" name="lecturerID"
                           value="<c:out value='${lecturerID}'/>" />

                    <!-- Student Assessment ID (auto generated) -->
                    <div class="form-group">
                        <input class="input-field" type="text"
                               value="(Auto Generated)" disabled placeholder=" " />
                        <label class="input-label">Student Assessment ID</label>
                    </div>

                    <!-- Assessment (display only) -->
                    <div class="form-group">
                        <input class="input-field" type="text"
                               value="<c:out value='${assessmentName}'/>"
                               readonly placeholder=" " />
                        <label class="input-label">Assessment</label>
                    </div>

                    <!-- Student (display only) -->
                    <div class="form-group">
                        <input class="input-field" type="text"
                               value="<c:out value='${studentDisplay}'/>"
                               readonly placeholder=" " />
                        <label class="input-label">Student</label>
                    </div>

                    <!-- Mark -->
                    <div class="form-group">
                        <input
                            class="input-field <c:if test='${not empty errors.mark}'>input-error</c:if>"
                                type="number"
                                name="mark"
                                value="<c:out value='${markVal}'/>"
                            min="0" max="100"
                            placeholder=" " />
                        <label class="input-label">Mark (0 - 100)</label>
                        <div class="field-error">
                            <c:out value="${errors.mark}" />
                        </div>
                    </div>

                    <!-- Grade (auto generated from grading table) -->
                    <div class="form-group">
                        <input class="input-field" type="text"
                               value="<c:out value='${gradeVal}'/>"
                               readonly placeholder=" " />
                        <label class="input-label">Grade</label>
                        <div class="field-error">
                            <c:out value="${errors.grade}" />
                        </div>
                    </div>

                    <!-- Date Assessed (auto set by system) -->
                    <div class="form-group">
                        <input class="input-field" type="text"
                               value="<c:out value='${dateAssessedVal}'/>"
                               readonly placeholder=" " />
                        <label class="input-label">Date Assessed</label>
                    </div>

                    <!-- Feedback -->
                    <div class="form-group">
                        <textarea
                            class="input-field <c:if test='${not empty errors.feedbackText}'>input-error</c:if>"
                                name="feedbackText"
                                rows="4"
                                placeholder=" "><c:out value='${feedbackVal}'/></textarea>
                        <label class="input-label">Feedback</label>
                        <div class="field-error">
                            <c:out value="${errors.feedbackText}" />
                        </div>
                    </div>

                    <!-- Assessed By (display only) -->
                    <div class="form-group">
                        <input class="input-field" type="text"
                               value="<c:out value='${lecturerDisplay}'/>"
                               readonly placeholder=" " />
                        <label class="input-label">Assessed By</label>
                    </div>

                    <div class="form-buttons">
                        <button class="btn" type="submit">Add</button>

                        <!-- go back to student assessment list for that assessment -->
                        <a class="btn btn-secondary"
                           href="${pageContext.request.contextPath}/StudentAssessment?action=list&assessmentID=<c:out value='${assessmentID}'/>">
                            Cancel
                        </a>
                    </div>

                </form>
            </div>
        </div>

    </body>
</html>
