<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="controller?action=add_route_to_cruise&cruiseId=${requestScope.cruiseId}" scope="request"/>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title><fmt:message key="routes.title"/></title>
    <!-- Custom styles for this template -->
</head>
<body class="text-center">
<jsp:include page="/view/templates/admin_navbar.jsp"/>
<div class="container">
    <form class="row g-3" action="/Cruise/controller" method="get">
        <input type="hidden" name="action" value="add_route_to_cruise">
        <input type="hidden" name="cruiseId" value="${requestScope.cruiseId}">
        <div class="col-auto"><span class="form-control"> <fmt:message key="common.filtering.numberPerPage"/>:</span></div>
        <div class="col-auto">
            <select name="limit" class="form-select">
                <option value="2">2</option>
                <option value="5">5</option>
                <option value="10">10</option>
            </select>
        </div>
        <div class="col-auto"><span class="form-control"><fmt:message key="common.filtering.sortBy"/>:</span></div>
        <div class="col-auto">
            <select name="sortBy" class="form-select">
                <option value="1">Id</option>
                <option value="2"><fmt:message key="routes.table.startPort"/></option>
                <option value="4"><fmt:message key="routes.table.endPort"/></option>
            </select>
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary mb-3"><fmt:message key="common.filtering.filter"/></button>
        </div>
    </form>
    <div class="row">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col"><fmt:message key="routes.table.startPort"/></th>
                <th scope="col"><fmt:message key="routes.table.middlePorts"/></th>
                <th scope="col"><fmt:message key="routes.table.endPort"/></th>
                <th scope="col"><fmt:message key="routes.table.numberOfPorts"/></th>
                <th scope="col" class="text-center"><fmt:message key="common.table.action"/></th>
            </tr>
            </thead>
            <c:set var="route" scope="request" value="${routes}"/>
            <c:forEach items="${routes}" var="route">
            <tbody>
            <tr>
                <th scope="row"><c:out value="${route.id}"/></th>
                <td><c:out value="${route.startPort}"/></td>
                <td><c:out value="${route.middlePorts}"/></td>
                <td><c:out value="${route.endPort}"/></td>
                <td><c:out value="${route.numberOfPorts}"/></td>
                <td>
                    <button value="${route}" type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal"
                            data-bs-target="#addToCruise${route.id}">
                        <fmt:message key="routes.button.addRouteToCruise"/>
                    </button>
                </td>
            </tr>
            <!-- Modal Add To Cruise-->
            <div class="modal fade" id="addToCruise${route.id}" tabindex="-1" aria-labelledby="addToCruise${route}Label"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="addToCruise${route.id}Label"><fmt:message key="routes.button.addRouteToCruise"/></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <fmt:message key="routes.text.addRouteToCruise"/> = ${route.id}
                            <form action="/Cruise/controller?action=add_route_to_cruise" method="post">
                                <input type="hidden" name="routeId" value="${route.id}">
                                <input type="hidden" name="cruiseId" value="${requestScope.cruiseId}">
                                <br/>
                                <button class="btn btn-md btn-primary btn-block" type="submit"><fmt:message
                                        key="common.button.confirm"/></button>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="common.button.close"/></button>
                        </div>
                    </div>
                </div>
            </div>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="row">
        <div class="col-md-12">
            <c:forEach var="i" begin="1" end="${requestScope.pages}">
                <a href="controller?action=add_route_to_cruise&page=${i-1}&limit=${requestScope.limit}&sortBy=${requestScope.sortBy}"
                   class="btn btn-sm btn-secondary fw-bold border-white bg-black">${i}</a>
            </c:forEach>
        </div>
    </div>
</div>

</div>
</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>
