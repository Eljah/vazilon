<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.UsersBundle"/>

<html>
<head>
    <title></title>
</head>
<body>
<div class="container">

    <form action="login" method=post class="form-signin">
        <div class="form-group">
            <label for="username" class="sr-only"><fmt:message key="login.label.username"/></label>
            <input type="text" name="j_username" size="25" id="username" class="form-control"
                   placeholder="<fmt:message key="login.label.username" />">
        </div>
        <div class="form-group">
            <label for="password" class="sr-only"><fmt:message key="login.label.password"/></label>
            <input type="text" name="j_password" size="15" id="password" class="form-control"
                   placeholder="<fmt:message key="login.label.password" />">
        </div>
        <div class="form-group">
            <label for="rememberme"><fmt:message key="login.label.remember"/></label>
            <input type="checkbox" name="rememberme" checked="checked" id="rememberme"/>
        </div>
        <input type="hidden" name="url"
               value="<% if (request.getParameter("url")!= null) {%><%=request.getParameter("url")%><%} else %>${requestScope['javax.servlet.forward.request_uri']}">

        <div class="form-group">

            <input class="btn btn-success" type="submit" value="<fmt:message key="login.label.submit" />">
            <input class="btn btn-success" type="reset" value="<fmt:message key="login.label.reset" />">
        </div>
        <div class="form-group">
            <a href="signin" class="btn btn-success"><fmt:message key="login.label.facebook"/></a>
        </div>
        <div class="form-group">
            <a href="register" class="btn btn-success"><fmt:message key="login.label.register"/></a>
        </div>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <strong><%=request.getAttribute("error")%></strong>
            </div>
        <% }%>
    </form>
</div>
</body>
</html>
