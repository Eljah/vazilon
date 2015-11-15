<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.UsersBundle"/>
<html>
<head>
    <title></title>
</head>
<body>

<display:table name="trips" id="row" pagesize="10"
               export="true" sort="list" requestURI="excursions" class="table table-bordered table-striped">

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
                    sortable="true" headerClass="sortable" sortProperty="date">
        <c:if test="${sessionUser!=null}">

            <form action="excursions" method="post"> <input type="hidden" value="${row.databaseId}" name="dbId">
        </c:if>
        ${row.date}
    </display:column>
    <display:column titleKey="trips.label.expl"
                    sortable="true" headerClass="sortable" class="col-md-7">
        <input type="hidden" value="${row.origin.databaseId}" name="planId">
        ${row.origin.shortExplanation} (${row.origin.author.username})

    </display:column>
    <display:column property="leadingGuide.username" titleKey="trips.label.leader"
                    sortable="true" headerClass="sortable"/>
    <display:column titleKey="trips.label.action"
                    sortable="true" headerClass="sortable">
        <c:if test="${sessionUser!=null}">
            <c:if test="${!sessionUser.hasExcursionInPlanned(row)}">
                <button type="submit" class="btn btn-success"><fmt:message key="trips.label.sign"/></button>
            </c:if>
            <c:if test="${sessionUser.hasExcursionInPlanned(row)}">
                <a href="deleteExcursion?id=${row.databaseId}" type="submit" class="btn btn-success"><fmt:message
                        key="trips.label.delete"/></a>
            </c:if>
        </c:if>
        <a href="excursionDetails?id=${row.databaseId}" class="btn btn-success"><fmt:message
                key="trips.label.details"/></a>
        </form>

    </display:column>

</display:table>

</body>
</html>
