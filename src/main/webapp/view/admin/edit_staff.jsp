<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="controller?action=edit_staff&cruiseShipId=${requestScope.cruiseShip.id}" scope="request"/>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title><fmt:message key="staff.title"/></title>
    <!-- Custom styles for this template -->
</head>
<body class="text-center">
<jsp:include page="/view/templates/admin_navbar.jsp"/>
<div class="container">
    <jsp:include page="/view/templates/display_error.jsp"/>
    <div class="row">
        <div class="col-md-8">
            <form class="row" action="/Cruise/controller" method="get">
                <input type="hidden" name="action" value="edit_staff">
                <input type="hidden" name="cruiseShipId" value="${requestScope.cruiseShip.id}">
                <div class="col-auto">
                    <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addModal">
                        <fmt:message key="staff.button.addStaff"/>
                    </button>
                </div>
                <div class="col-auto"><span class="form-control"> <fmt:message key="common.filtering.numberPerPage"/>:</span></div>
                <div class="col-auto">
                    <select name="limit" class="form-select">
                        <option value="2" ${requestScope.limit eq '2' ? 'selected' : ''}>2</option>
                        <option value="5" ${requestScope.limit eq '5' ? 'selected' : ''}>5</option>
                        <option value="10" ${requestScope.limit eq '10' ? 'selected' : ''}>10</option>
                    </select>
                </div>
                <div class="col-auto"><span class="form-control"><fmt:message key="common.filtering.sortBy"/>:</span></div>
                <div class="col-auto">
                    <select name="sortBy" class="form-select">
                        <option value="1" ${requestScope.sortBy eq '1' ? 'selected' : ''}>Id</option>
                        <option value="4" ${requestScope.sortBy eq '4' ? 'selected' : ''}><fmt:message key="staff.filtering.position"/></option>
                    </select>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-3"><fmt:message key="common.filtering.filter"/></button>
                </div>
            </form>
        </div>
        <div class="col-md-4">
            <form class="row" action="/Cruise/controller" method="get">
                <input type="hidden" name="action" value="edit_staff">
                <input type="hidden" name="cruiseShipId" value="${requestScope.cruiseShip.id}">
                <div class="col-auto"><span class="form-control"> <fmt:message key="common.filtering.findById"/>:</span></div>
                <div class="col-md-3"><input type="number" name="findStaffId" class="form-control"/></div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-3"><fmt:message key="common.filtering.button.find"/></button>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col"><fmt:message key="staff.table.firstName"/></th>
                <th scope="col"><fmt:message key="staff.table.lastName"/></th>
                <th scope="col"><fmt:message key="staff.filtering.position"/></th>
                <th scope="col" class="text-center"><fmt:message key="common.table.action"/></th>
            </tr>
            </thead>
            <c:set var="staff" scope="request" value="${listStaff}"/>
            <c:forEach items="${listStaff}" var="staff">
            <tbody>
            <tr>
                <th scope="row"><c:out value="${staff.id}"/></th>
                <td><c:out value="${staff.firstName}"/></td>
                <td><c:out value="${staff.lastName}"/></td>
                <td><c:out value="${staff.position}"/></td>
                <td>
                    <button value="${staff}" type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal"
                            data-bs-target="#delete${staff.id}">
                        <fmt:message key="staff.button.deleteStaff"/>
                    </button>
                </td>
            </tr>
            <!-- Modal Delete-->
            <div class="modal fade" id="delete${staff.id}" tabindex="-1" aria-labelledby="delete${staff.id}Label"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="delete${staff.id}Label"><fmt:message key="staff.button.deleteStaff"/></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <fmt:message key="staff.text.deleteStaff"/> id = <c:out value="${staff.id}"/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="common.button.close"/></button>
                            <a href="controller?action=delete_staff&id=${staff.id}&cruiseShipId=${requestScope.cruiseShip.id}"
                               class="btn btn-secondary"><fmt:message key="common.button.delete"/></a>
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
                <a href="controller?action=edit_staff&page=${i-1}&limit=${requestScope.limit}&sortBy=${requestScope.sortBy}&cruiseShipId=${requestScope.cruiseShip.id}"
                   class="btn btn-sm btn-secondary fw-bold border-white bg-black">${i}</a>
            </c:forEach>
        </div>
    </div>
</div>

</div>
<!-- Modal Add-->
<div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="addModalLabel"><fmt:message key="staff.button.addStaff"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form class="form" method="post" action="controller?action=add_staff">
                    <fmt:message key="staff.form.firstName"/>
                    <input type="text" name="firstName" class="form-control" required>
                    <fmt:message key="staff.form.lastName"/>
                    <input type="text" name="lastName" class="form-control" required>
                    <fmt:message key="staff.form.position"/>
                    <input type="text" name="position" class="form-control" required>
                    <input type="hidden" name="cruiseShipId" value="${requestScope.cruiseShip.id}" required>
                    <button class="btn btn-md btn-primary btn-block" type="submit"><fmt:message key="common.button.confirm"/></button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="common.button.close"/></button>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>
