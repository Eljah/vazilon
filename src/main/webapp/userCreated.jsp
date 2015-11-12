<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.UsersBundle" />

<html>
<head>
    <title><fmt:message key="emailsent.title" /></title>
</head>
<body>
<fmt:message key="emailsent.message" />
</body>
</html>
