<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.UsersBundle"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Planned excursion trips</title>
</head>
<body>

<display:table name="trips" id="row" pagesize="10"
               export="true" sort="list" requestURI="excursionTrips" class="table table-bordered table-striped">

    <display:setProperty name="paging.banner.no_items_found">
        <div class="pagination">No {0} found.</div>
    </display:setProperty>
    <display:setProperty name="paging.banner.one_item_found">
        <div class="pagination">One {0} found.</div>
    </display:setProperty>
    <display:setProperty name="paging.banner.all_items_found">
        <div class="pagination">{0} {1} found, displaying all {2}.</div>
    </display:setProperty>
    <display:setProperty name="paging.banner.some_items_found">
        <div class="pagination">{0} {1} found, displaying {2} to {3}.</div>
    </display:setProperty>
    <display:setProperty name="paging.banner.onepage">
        <div class="pagination">{0}</div>
    </display:setProperty>

    <display:column titleKey="trips.label.date"
                    sortable="true" headerClass="sortable" sortProperty="date" >
        <form action="excursionTrips" method="post" name="excursionTrips">
        <input type="hidden" value="${row.databaseId}" name="dbId">
        <c:if test="${row.leadingGuide.isYou(sessionUser)||sessionUser.isAdmin()}">
            <input type="text" class="datepicker" name="date" value="${row.date}">
        </c:if>
        <c:if test="${!(row.leadingGuide.isYou(sessionUser)||sessionUser.isAdmin())}">
            ${row.date}
        </c:if>
    </display:column>
    <display:column titleKey="trips.label.expl"
                    sortable="true" headerClass="sortable" class="col-md-9">
        <input type="hidden" value="${row.origin.databaseId}" name="planId">
        ${row.origin.shortExplanation} (${row.origin.author.username})

    </display:column>
    <display:column property="leadingGuide.username" titleKey="trips.label.leader"
                    sortable="true" headerClass="sortable"/>

    <display:column titleKey="trips.label.action"
                    sortable="true" headerClass="sortable">
        <button type="submit" class="btn btn-success"><fmt:message key="trips.label.update"/></button>
        </form>
    </display:column>
</display:table>
</form>


<table class="table table-bordered table-striped">
    <tr>

        <form action="excursionTrips" method="post">
            <td>
                <input type="text" class="datepicker" name="date"
                       placeholder="<fmt:message key="trips.label.insertdate"/>">
            </td>
            <td>
                <select name="planId">
                    <c:forEach var="plan" items="${excplans}">
                        <option value="${plan.databaseId}">${plan.shortExplanation} (${plan.author.username})</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <button type="submit" class="btn btn-success"><fmt:message key="trips.label.new"/></button>
            </td>
        </form>
    </tr>
</table>

</body>
</html>
