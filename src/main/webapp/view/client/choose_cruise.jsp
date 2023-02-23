<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="controller?action=choose_cruise" scope="request"/>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title><fmt:message key="chooseCruise.title"/></title>
    <!-- Custom styles for this template -->
</head>
<body class="text-center">
<jsp:include page="/view/templates/client_navbar.jsp"/>

<div class="container">
    <jsp:include page="/view/templates/display_error.jsp"/>
    <div class="row">
        <div class="col-md-8">
            <form class="row" action="/Cruise/controller" >
                <input name="action" value="choose_cruise" type="hidden">
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
                        <option value="3" ${requestScope.sortBy eq '3' ? 'selected' : ''}><fmt:message key="cruises.table.startDate"/></option>
                        <option value="4" ${requestScope.sortBy eq '4' ? 'selected' : ''}><fmt:message key="cruises.table.endDate"/></option>
                        <option value="6" ${requestScope.sortBy eq '6' ? 'selected' : ''}><fmt:message key="cruises.table.ticketPrice"/></option>
                    </select>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-3"><fmt:message key="common.filtering.filter"/></button>
                </div>
            </form>
        </div>
        <div class="col-md-4">
            <form class="row" action="/Cruise/controller">
                <input name="action" value="choose_cruise" type="hidden">
                <div class="col-auto"><span class="form-control"> <fmt:message key="common.filtering.findById"/>:</span></div>
                <div class="col-md-3"><input type="number" name="findCruiseId" class="form-control"/></div>
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
                <th scope="col"><fmt:message key="cruises.table.startDate"/></th>
                <th scope="col"><fmt:message key="cruises.table.endDate"/></th>
                <th scope="col"><fmt:message key="common.table.status"/></th>
                <th scope="col"><fmt:message key="cruises.table.ticketPrice"/></th>
                <th scope="col"><fmt:message key="cruiseShips.table.name"/></th>
                <th scope="col"><fmt:message key="cruises.table.duration"/></th>
                <th scope="col"><fmt:message key="cruises.table.freeSpaces"/></th>
                <th scope="col"><fmt:message key="common.table.action"/></th>
            </tr>
            </thead>
            <c:set var="cruise" scope="request" value="${cruises}"/>
            <c:forEach items="${cruises}" var="cruise">
            <tbody>
            <tr>
                <th scope="row"><c:out value="${cruise.id}"/></th>
                <td><c:out value="${cruise.start}"/></td>
                <td><c:out value="${cruise.end}"/></td>
                <td><c:out value="${cruise.status}"/></td>
                <td><c:out value="${cruise.ticketPrice}"/></td>
                <td><c:out value="${cruise.cruiseShip.name}"/></td>
                <td><c:out value="${cruise.route.endPort}"/></td>
                <td><c:out value="${cruise.freeSpaces}"/></td>
                <td><c:if test="${cruise.status != 'Started' && cruise.status != 'Ended' && cruise.freeSpaces != 0}">
                    <button value="${cruise}" type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal"
                            data-bs-target="#buy${cruise.id}">
                        <fmt:message key="chooseCruise.button.buyTicket"/>
                    </button>
                </c:if>
                </td>
            </tr>
            <!-- Modal Buy Ticket-->
            <div class="modal fade" id="buy${cruise.id}" tabindex="-1" aria-labelledby="buy${cruise.id}Label"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="buy${cruise.id}Label"><fmt:message key="chooseCruise.button.buyTicket"/></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                        <form action="controller?action=buy_ticket&cruiseId=${cruise.id}&clientId=${sessionScope.user.id}" method="post" enctype="multipart/form-data">
                            <fmt:message key="chooseCruise.text.addDocument"/>
                            <input type="file" name="document" class="form-control" id="inputGroupFile01" required>
                            <br/>
                            <p><fmt:message key="chooseCruise.text.payment"/></p>
                            <br/>
                            <button class="btn btn-primary" type="submit"><fmt:message key="chooseCruise.button.buyTicket"/></button>
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
                <a href="controller?action=choose_cruise&page=${i-1}&limit=${requestScope.limit}&sortBy=${requestScope.sortBy}"
                   class="btn btn-sm btn-secondary fw-bold border-white bg-black">${i}</a>
            </c:forEach>
        </div>
    </div>

</div>
</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>
