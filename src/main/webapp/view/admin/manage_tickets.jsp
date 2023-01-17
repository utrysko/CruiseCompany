<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="controller?action=manage_tickets" scope="request"/>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title><fmt:message key="manageTickets.title"/></title>
    <!-- Custom styles for this template -->
</head>
<body class="text-center">
<jsp:include page="/view/templates/admin_navbar.jsp"/>
<div class="container">
    <c:if test="${sessionScope.error != null}">
        <div class="form-control">
            <h2 style="color: red"><c:out value="${sessionScope.error}"/></h2>
                ${sessionScope.remove('error')}
        </div>
    </c:if>
    <c:if test="${requestScope.error != null}">
        <div class="form-control">
            <h2 style="color: red"><c:out value="${requestScope.error}"/></h2>
        </div>
    </c:if>
    <div class="row">
        <div class="col-md-8">
            <form class="row" action="/Cruise/controller" method="get">
                <input type="hidden" name="action" value="manage_tickets">
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
                        <option value="2"><fmt:message key="manageTickets.table.cruiseId"/></option>
                        <option value="3"><fmt:message key="manageTickets.table.clientId"/></option>
                        <option value="4"><fmt:message key="common.table.status"/></option>
                    </select>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-3"><fmt:message key="common.filtering.filter"/></button>
                </div>
            </form>
        </div>
        <div class="col-md-4">
            <form class="row" action="/Cruise/controller" method="get">
                <input type="hidden" name="action" value="manage_tickets">
                <div class="col-auto"><span class="form-control"> <fmt:message key="common.filtering.findById"/>:</span></div>
                <div class="col-md-3"><input type="number" name="findTicketId" class="form-control"/></div>
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
                <th scope="col"><fmt:message key="manageTickets.table.cruiseId"/></th>
                <th scope="col"><fmt:message key="manageTickets.table.clientId"/></th>
                <th scope="col"><fmt:message key="common.table.status"/></th>
                <th scope="col"><fmt:message key="manageTickets.table.document"/></th>
                <th scope="col" class="text-center"><fmt:message key="common.table.action"/></th>
            </tr>
            </thead>
            <c:set var="ticket" scope="request" value="${tickets}"/>
            <c:forEach items="${tickets}" var="ticket">
            <tbody>
            <tr>
                <th scope="row"><c:out value="${ticket.id}"/></th>
                <td><c:out value="${ticket.cruiseId}"/></td>
                <td><c:out value="${ticket.clientId}"/></td>
                <td><c:out value="${ticket.status}"/></td>
                <td><a class="btn btn-sm btn-primary" href="controller?action=show_document&ticketId=${ticket.id}">
                    <fmt:message key="manageTickets.button.showDocument"/>
                </a></td>
                <td>
                    <button value="${ticket}" type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal"
                            data-bs-target="#changeStatus${ticket.id}">
                        <fmt:message key="common.button.changeStatus"/>
                    </button>
                </td>
            </tr>
            <!-- Modal Change Status-->
            <div class="modal fade" id="changeStatus${ticket.id}" tabindex="-1" aria-labelledby="changeStatus${ticket.id}Label"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="changeStatus${ticket.id}Label"><fmt:message key="common.button.changeStatus"/></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="/Cruise/controller?action=ticket_change_status" method="post">
                                <fmt:message key="manageTickets.editForm.changeStatus"/>
                                <input type="hidden" name="ticketId" value="${ticket.id}">
                                <select name="status" class="form-select" required>
                                    <option value="Payed">Payed</option>
                                    <option value="Not Payed">Not Payed</option>
                                </select>
                                <br/>
                                <button class="btn btn-md btn-primary btn-block" type="submit"><fmt:message key="common.button.confirm"/></button>
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
                <a href="controller?action=manage_tickets&page=${i-1}&limit=${requestScope.limit}&sortBy=${requestScope.sortBy}"
                   class="btn btn-sm btn-secondary fw-bold border-white bg-black">${i}</a>
            </c:forEach>
        </div>
    </div>
</div>
</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>