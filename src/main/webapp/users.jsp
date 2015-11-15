<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.UsersBundle" />

<html>
<head>
    <title><fmt:message key="users.title" /></title>
</head>
<body>
<table class="table table-bordered table-striped">
    <tr>
        <th><fmt:message key="users.label.username" /></th>
        <th><fmt:message key="users.label.userrole" /></th>
        <th><fmt:message key="users.label.firstname" /></th>
        <th><fmt:message key="users.label.lastname" /></th>
        <th><fmt:message key="users.label.email" /></th>
        <th><fmt:message key="users.label.action" /></th>
    </tr>

    <c:forEach var="user" items="${users}">

        <tr>
            <form action="users" method="post">
            <td>${user.username}</td>
            <td>
                <input type="hidden" value="${user.databaseId}" name="dbId">
                <c:if test="${sessionUser.isAdmin()}">
                    <select name="role">
                        <option ${user.role == 'SIGHTSEER' ? 'selected' : ''} value="SIGHTSEER">Sightseer</option>
                        <option ${user.role == 'GUIDE' ? 'selected' : ''} value="GUIDE">Guide</option>
                        <option ${user.role == 'BUSDRIVER' ? 'selected' : ''} value="BUSDRIVER">Bus Driver</option>
                        <option ${user.role == 'ADMIN' ? 'selected' : ''} value="ADMIN">Admin</option>
                    </select>
                </c:if>
                <c:if test="${!sessionUser.isAdmin()}">
                    <c:choose>

                        <c:when test="${user.role=='ADMIN'}">
                            <i>ADMIN</i>
                        </c:when>
                        <c:when test="${user.role=='GUIDE'}">
                            <b>GUIDE</b>
                        </c:when>
                        <c:otherwise>
                            ${user.role}
                        </c:otherwise>
                    </c:choose>
                </c:if>

            </td>

            <td>
                <c:if test="${user.isYou(sessionUser)||sessionUser.isAdmin()}">
                    <input type="text" name="firstname" value="${user.firstname}">
                </c:if>
                <c:if test="${!user.isYou(sessionUser)&&!sessionUser.isAdmin()}">
                    ${user.firstname}
                </c:if>
            </td>
            <td>
                <c:if test="${user.isYou(sessionUser)||sessionUser.isAdmin()}">
                    <input type="text" name="lastname" value="${user.lastname}">
                </c:if>
                <c:if test="${!user.isYou(sessionUser)&&!sessionUser.isAdmin()}">
                    ${user.lastname}
                </c:if>
            </td>
            <td>
                <c:if test="${user.isYou(sessionUser)||sessionUser.isAdmin()}">
                    <input type="text" name="email" value="${user.email}">
                </c:if>
                <c:if test="${!user.isYou(sessionUser)&&!sessionUser.isAdmin()}">
                    ${user.email}
                </c:if>

            </td>
            <td> <c:if test="${user.isYou(sessionUser)||sessionUser.isAdmin()}">
                <button type="submit" class="btn btn-success"><fmt:message key="users.label.update" /></button>
            </c:if></td>
            </form>
        </tr>
    </c:forEach>
</table>
</body>
</html>
