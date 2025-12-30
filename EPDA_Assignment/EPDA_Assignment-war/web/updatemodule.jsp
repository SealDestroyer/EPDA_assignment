<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Modify Module</title>
        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-addmodule.css">
    </head>

    <body>
        <jsp:include page="ALnavbar.jsp" />

        <div class="add-container">
            <div class="add-box">
                <h2 class="add-title">Modify Module</h2>
                <p class="add-subtitle">Update module details below.</p>

                <form action="Module" method="POST" novalidate>
                    <input type="hidden" name="action" value="update" />
                    <input type="hidden" name="classID" value="${classID}" />
                    <input type="hidden" name="moduleID" value="${moduleIDVal}" />

                    <!-- Module Name -->
                    <div class="form-group">
                        <input
                            class="input-field <c:if test='${not empty errors.moduleName}'>input-error</c:if>"
                                type="text"
                                id="moduleName"
                                name="moduleName"
                                value="${moduleNameVal}"
                            placeholder=" "
                            />
                        <label class="input-label" for="moduleName">Module Name</label>
                        <div class="field-error"><c:out value="${errors.moduleName}" /></div>
                    </div>

                    <!-- Module Code -->
                    <div class="form-group">
                        <input
                            class="input-field <c:if test='${not empty errors.moduleCode}'>input-error</c:if>"
                                type="text"
                                id="moduleCode"
                                name="moduleCode"
                                value="${moduleCodeVal}"
                            placeholder=" "
                            />
                        <label class="input-label" for="moduleCode">Module Code</label>
                        <div class="field-error"><c:out value="${errors.moduleCode}" /></div>
                    </div>

                    <!-- Description -->
                    <div class="form-group">
                        <textarea
                            class="input-field textarea-field <c:if test='${not empty errors.description}'>input-error</c:if>"
                                id="description"
                                name="description"
                                placeholder=" "
                                >${descriptionVal}</textarea>
                        <label class="input-label" for="description">Description</label>
                        <div class="field-error"><c:out value="${errors.description}" /></div>
                    </div>

                    <!-- Created By -->
                    <div class="form-group">
                        <input class="input-field" type="text" id="createdBy" value="${createdByName}" readonly placeholder=" " />
                        <label class="input-label" for="createdBy">Created By</label>
                    </div>

                    <!-- Assigned Lecturer -->
                    <div class="form-group select-group">
                        <select
                            class="input-field <c:if test='${not empty errors.assignedLecturerID}'>input-error</c:if>"
                                id="assignedLecturerID"
                                name="assignedLecturerID"
                                required
                                >
                                <option value="" disabled hidden <c:if test="${empty assignedLecturerIDVal}">selected</c:if>>
                                    Select Lecturer
                                </option>

                            <c:forEach items="${lecturerList}" var="lec">
                                <option value="${lec.userID}" <c:if test="${lec.userID == assignedLecturerIDVal}">selected</c:if>>
                                    ${lec.userID} - ${lec.fullName}
                                </option>
                            </c:forEach>
                        </select>
                        <label class="input-label" for="assignedLecturerID">Assigned Lecturer</label>
                        <div class="field-error"><c:out value="${errors.assignedLecturerID}" /></div>
                    </div>

                    <div class="form-buttons">
                        <button class="btn" type="submit">Update</button>
                        <a class="btn btn-secondary"
                           href="${pageContext.request.contextPath}/Module?action=list&classID=${classID}">
                            Cancel
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
