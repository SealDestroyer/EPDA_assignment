<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Modify Assessment</title>

        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-addmodule.css">
    </head>

    <body>
        <jsp:include page="Lnavbar.jsp" />

        <div class="add-container">
            <div class="add-box">
                <h2 class="add-title">Modify Assessment</h2>
                <p class="add-subtitle">Update assessment details below.</p>

                <form action="${pageContext.request.contextPath}/Assessment"
                      method="POST" novalidate>

                    <input type="hidden" name="action" value="update" />
                    <input type="hidden" name="moduleID" value="<c:out value='${moduleID}'/>" />
                    <input type="hidden" name="assessmentID" value="<c:out value='${assessmentIDVal}'/>" />

                    <!-- Module ID (display only) -->
                    <div class="form-group">
                        <input class="input-field" type="text"
                               value="<c:out value='${moduleID}'/>"
                               readonly placeholder=" " />
                        <label class="input-label">Module ID</label>
                    </div>

                    <!-- Assessment Name -->
                    <div class="form-group">
                        <input
                            class="input-field <c:if test='${not empty errors.assessmentName}'>input-error</c:if>"
                                type="text"
                                name="assessmentName"
                                value="<c:out value='${assessmentNameVal}'/>"
                            placeholder=" " />
                        <label class="input-label">Assessment Name</label>
                        <div class="field-error">
                            <c:out value="${errors.assessmentName}" />
                        </div>
                    </div>

                    <!-- Weightage -->
                    <div class="form-group">
                        <input
                            class="input-field <c:if test='${not empty errors.weightage}'>input-error</c:if>"
                                type="number"
                                name="weightage"
                                value="<c:out value='${weightageVal}'/>"
                            min="1" max="100"
                            placeholder=" " />
                        <label class="input-label">Weightage (%)</label>
                        <div class="field-error">
                            <c:out value="${errors.weightage}" />
                        </div>
                    </div>

                    <!-- Created By (display only) -->
                    <div class="form-group">
                        <input class="input-field" type="text"
                               value="<c:out value='${createdByName}'/>"
                               readonly placeholder=" " />
                        <label class="input-label">Created By</label>
                    </div>

                    <div class="form-buttons">
                        <button class="btn" type="submit">Update</button>

                        <a class="btn btn-secondary"
                           href="${pageContext.request.contextPath}/Assessment?action=list&moduleID=<c:out value='${moduleID}'/>">
                            Cancel
                        </a>
                    </div>

                </form>
            </div>
        </div>
    </body>
</html>
