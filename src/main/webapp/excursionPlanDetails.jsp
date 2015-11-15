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

<display:table name="points" id="row" pagesize="10"
               export="true" sort="list" requestURI="excursionPlanDetails" class="table table-bordered table-striped">

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

    <display:column property="name" titleKey="trips.label.leader"
                    sortable="true" headerClass="sortable"/>
    <display:column titleKey="trips.label.action"
                    sortable="true" headerClass="sortable">
               <a href="point?id=${row.id}" class="btn btn-success"><fmt:message
                key="trips.label.details"/></a>
     </display:column>

</display:table>
<a href="point?route=${id}" class="btn btn-success"><fmt:message
        key="trips.label.new"/></a>
</body>
</html>

