<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Module</title>

        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-addmodule.css">
    </head>

    <body>
        <jsp:include page="ALnavbar.jsp" />

        <div class="add-container">
            <div class="add-box">
                <h2 class="add-title">New Module</h2>
                <p class="add-subtitle">Enter module details below.</p>

                <form action="Module" method="POST">
                    <input type="hidden" name="action" value="add" />

                    <!-- Module ID (auto generated) -->
                    <div class="form-group">
                        <input class="input-field" type="text" id="moduleID" value="(Auto Generated)" disabled placeholder=" " />
                        <label class="input-label" for="moduleID">Module ID</label>
                    </div>

                    <div class="form-group">
                        <input class="input-field" type="text" id="moduleName" name="moduleName" placeholder=" " required />
                        <label class="input-label" for="moduleName">Module Name</label>
                    </div>

                    <div class="form-group">
                        <input class="input-field" type="text" id="moduleCode" name="moduleCode" placeholder=" " required />
                        <label class="input-label" for="moduleCode">Module Code</label>
                    </div>

                    <div class="form-group">
                        <textarea class="input-field textarea-field" id="description" name="description" placeholder=" "></textarea>
                        <label class="input-label" for="description">Description</label>
                    </div>

                    <div class="form-group">
                        <input class="input-field" type="text" id="createdBy" value="${createdByName}" readonly placeholder=" " />
                        <label class="input-label" for="createdBy">Created By</label>
                    </div>

                    <div class="form-group select-group">
                        <select class="input-field" id="assignedLecturerID" name="assignedLecturerID" required>
                            <option value="" disabled selected>Select Lecturer</option>

                            <c:forEach items="${lecturerList}" var="lec">
                                <option value="${lec.userID}">
                                    ${lec.userID} - ${lec.fullName}
                                </option>
                            </c:forEach>
                        </select>
                        <label class="input-label" for="assignedLecturerID">Assigned Lecturer</label>
                    </div>

                    <div class="form-buttons">
                        <button class="btn" type="submit">Add</button>
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/Module?action=list">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
