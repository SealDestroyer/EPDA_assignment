<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Modify Student Mark</title>

        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-addmodule.css">
    </head>

    <body>
        <jsp:include page="Lnavbar.jsp" />

        <div class="add-container">
            <div class="add-box">
                <h2 class="add-title">Modify Student Mark</h2>
                <p class="add-subtitle">Update the assessment mark and feedback below.</p>

                <form action="${pageContext.request.contextPath}/StudentAssessment"
                      method="POST" novalidate>

                    <input type="hidden" name="action" value="update" />

                    <!-- needed to find record -->
                    <input type="hidden" name="studentAssessmentID"
                           value="<c:out value='${studentAssessmentIDVal}'/>" />
                    
                    <input type="hidden" name="moduleID" value="<c:out value='${moduleID}'/>" />

                    <!-- needed to redirect back to list -->
                    <input type="hidden" name="assessmentID"
                           value="<c:out value='${assessmentID}'/>" />

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

                    <!-- Grade (auto generated) -->
                    <div class="form-group">
                        <input class="input-field" type="text"
                               value="<c:out value='${gradeVal}'/>"
                               readonly placeholder=" " />
                        <label class="input-label">Grade</label>
                    </div>

                    <!-- Date Assessed (display only) -->
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
                        <button class="btn" type="submit">Update</button>

                        <a class="btn btn-secondary"
                           href="${pageContext.request.contextPath}/Assessment?action=studentList&moduleID=<c:out value='${moduleID}'/>&assessmentID=<c:out value='${assessmentID}'/>">
                            Cancel
                        </a>
                    </div>

                </form>
            </div>
        </div>

    </body>
</html>
