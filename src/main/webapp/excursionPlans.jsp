<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.UsersBundle"/>
<html>
<head>
    <title><fmt:message key="plans.title"/>
</head>
<body>
<table class="table table-bordered table-striped">
    <tr>
        <th><fmt:message key="plans.label.short"/></th>
        <th class="col-md-9"><fmt:message key="plans.label.expl"/></th>
        <th><fmt:message key="plans.label.action"/></th>
    </tr>

    <c:forEach var="plan" items="${plans}">

        <tr>
            <form action="excursionPlans" method="post">
                <td>
                    <input type="hidden" value="${plan.databaseId}" name="dbId">
                    <input type="text" name="shortExplanation" value="${plan.shortExplanation}">
                </td>
                <td>
                    <div class="span6">
                        <textarea rows="3" class="form-control" style="min-width: 100%" name="explanation">${plan.explanation}</textarea>
                    </div>
                </td>
                <td>
                    <button type="submit" class="btn btn-success"><fmt:message key="plans.label.update"/></button>
                    <a href="excursionPlanDetails?id=${plan.databaseId}" class="btn btn-success"><fmt:message
                            key="plans.label.details"/></a>

                </td>
            </form>
        </tr>
    </c:forEach>
</table>

<table class="table table-bordered table-striped">
    <tr>

        <form action="excursionPlans" method="post">
            <td>
                <div class="span6">
                    <input type="text" name="shortExplanation" value="${plan.shortExplanation}"
                           placeholder="Add short description here">
                </div>
            </td>
            <td class="col-md-9">
                <textarea rows="9" class="form-control" style="min-width: 100%" id="textarea" name="explanation"
                          placeholder="Add detailed description here">${plan.explanation}</textarea>

            </td>
            <td>
                <button type="submit" class="btn btn-success"><fmt:message key="plans.label.new"/></button>
            </td>
        </form>
    </tr>
</table>
</body>
</html>
