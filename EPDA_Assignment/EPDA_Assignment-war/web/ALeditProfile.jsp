<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit Profile</title>
        <link rel="stylesheet" href="css/al-dashboard.css">
        <link rel="stylesheet" href="css/al-addmodule.css">
    </head>

    <body>
        <jsp:include page="ALnavbar.jsp" />

        <div class="add-container">
            <div class="add-box">
                <h2 class="add-title">Profile</h2>
                <p class="add-subtitle">Update your profile details below.</p>

                <form action="ALProfile" method="POST" novalidate>
                    <input type="hidden" name="action" value="update" />

                    <input type="hidden" name="userID" value="${userIDVal}" />

                    <!-- Full Name -->
                    <div class="form-group">
                        <input
                            class="input-field <c:if test='${not empty errors.fullName}'>input-error</c:if>"
                                type="text"
                                id="fullName"
                                name="fullName"
                                value="${fullNameVal}"
                            placeholder=" "
                            />
                        <label class="input-label" for="fullName">Full Name</label>
                        <div class="field-error"><c:out value="${errors.fullName}" /></div>
                    </div>

                    <!-- Password (optional) -->
                    <div class="form-group">
                        <input
                            class="input-field <c:if test='${not empty errors.password}'>input-error</c:if>"
                                type="password"
                                id="password"
                                name="password"
                                value=""
                                placeholder=" "
                                />
                            <label class="input-label" for="password">New Password (optional)</label>
                            <div class="field-error"><c:out value="${errors.password}" /></div>
                    </div>
                    
                    <!-- Secret Key -->
                    <div class="form-group">
                        <input
                            class="input-field <c:if test='${not empty errors.secretKey}'>input-error</c:if>"
                                type="text"
                                id="secretKey"
                                name="secretKey"
                                value="${secretKeyVal}"
                            placeholder=" "
                            />
                        <label class="input-label" for="secretKey">Secret Key</label>
                        <div class="field-error"><c:out value="${errors.secretKey}" /></div>
                    </div>

                    <!-- Gender -->
                    <div class="form-group select-group">
                        <select
                            class="input-field <c:if test='${not empty errors.gender}'>input-error</c:if>"
                                id="gender"
                                name="gender"
                                required
                                >
                                <option value="" disabled hidden <c:if test="${empty genderVal}">selected</c:if>>
                                    Select Gender
                                </option>
                                <option value="Male" <c:if test="${genderVal == 'Male'}">selected</c:if>>Male</option>
                            <option value="Female" <c:if test="${genderVal == 'Female'}">selected</c:if>>Female</option>
                            </select>
                            <label class="input-label" for="gender">Gender</label>
                            <div class="field-error"><c:out value="${errors.gender}" /></div>
                    </div>

                    <!-- Phone -->
                    <div class="form-group">
                        <input
                            class="input-field <c:if test='${not empty errors.phone}'>input-error</c:if>"
                                type="text"
                                id="phone"
                                name="phone"
                                value="${phoneVal}"
                            placeholder=" "
                            />
                        <label class="input-label" for="phone">Phone</label>
                        <div class="field-error"><c:out value="${errors.phone}" /></div>
                    </div>

                    <!-- IC Number -->
                    <div class="form-group">
                        <input
                            class="input-field <c:if test='${not empty errors.icNumber}'>input-error</c:if>"
                                type="text"
                                id="icNumber"
                                name="icNumber"
                                value="${icNumberVal}"
                            placeholder=" "
                            />
                        <label class="input-label" for="icNumber">IC Number</label>
                        <div class="field-error"><c:out value="${errors.icNumber}" /></div>
                    </div>

                    <!-- Email -->
                    <div class="form-group">
                        <input
                            class="input-field <c:if test='${not empty errors.email}'>input-error</c:if>"
                                type="email"
                                id="email"
                                name="email"
                                value="${emailVal}"
                            placeholder=" "
                            />
                        <label class="input-label" for="email">Email</label>
                        <div class="field-error"><c:out value="${errors.email}" /></div>
                    </div>

                    <!-- Address -->
                    <div class="form-group">
                        <textarea
                            class="input-field textarea-field <c:if test='${not empty errors.address}'>input-error</c:if>"
                                id="address"
                                name="address"
                                placeholder=" "
                                >${addressVal}</textarea>
                        <label class="input-label" for="address">Address</label>
                        <div class="field-error"><c:out value="${errors.address}" /></div>
                    </div>

                    <!-- Leader Role -->
                    <div class="form-group">
                        <input class="input-field" type="text" id="leaderRole" value="${leaderRoleVal}" readonly placeholder=" " />
                        <label class="input-label" for="leaderRole">Leader Role</label>
                    </div>

                    <div class="form-buttons">
                        <button class="btn" type="submit">Save</button>
                        <a class="btn btn-secondary" href="ALdashboard.jsp">Cancel</a>
                    </div>
                </form>
            </div>
        </div>

    </body>
</html>
