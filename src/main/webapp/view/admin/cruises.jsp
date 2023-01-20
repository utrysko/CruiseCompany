<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="controller?action=cruises" scope="request"/>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title><fmt:message key="cruises.title"/></title>
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
            <input type="hidden" name="action" value="cruises">
            <div class="col-auto">
                <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#createModal">
                <fmt:message key="cruises.button.createCruise"/>
                </button>
            </div>
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
                    <option value="3"><fmt:message key="cruises.table.startDate"/></option>
                    <option value="4"><fmt:message key="cruises.table.endDate"/></option>
                    <option value="6"><fmt:message key="cruises.table.ticketPrice"/></option>
                </select>
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary mb-3"><fmt:message key="common.filtering.filter"/></button>
            </div>
        </form>
        </div>
        <div class="col-md-4">
            <form class="row" action="/Cruise/controller" method="get">
                <input type="hidden" name="action" value="cruises">
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
                <th scope="col"><fmt:message key="cruises.table.shipName"/></th>
                <th scope="col"><fmt:message key="cruises.table.duration"/></th>
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
                <td><c:if test="${cruise.cruiseShip == null}">
                    <a href="controller?action=add_ship_to_cruise&cruiseId=${cruise.id}"
                       class="btn btn-sm btn-primary"><fmt:message key="cruises.button.addShip"/></a>
                </c:if> <c:if test="${cruise.cruiseShip != null}"><c:out value="${cruise.cruiseShip.name}"/></c:if></td>
                <td><c:if test="${cruise.route == null}">
                    <a href="controller?action=add_route_to_cruise&cruiseId=${cruise.id}"
                       class="btn btn-sm btn-primary"><fmt:message key="cruises.button.addRoute"/></a>
                </c:if> <c:if test="${cruise.route != null}"><c:out value="${cruise.route.endPort}"/></c:if></td>
                <td>
                    <c:if test="${cruise.status != 'Started' && cruise.status != 'Ended'}">
                    <button value="${cruiseShip}" type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal"
                            data-bs-target="#edit${cruise.id}">
                        <fmt:message key="cruises.button.editCruise"/>
                    </button>
                    </c:if>
                </td>
                <td>
                    <c:if test="${cruise.status != 'Started' && cruise.status != 'Ended'}">
                    <button value="${cruiseShip}" type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal"
                            data-bs-target="#changeStatus${cruise.id}">
                        <fmt:message key="common.button.changeStatus"/>
                    </button>
                    </c:if>
                </td>
                <td>
                    <c:if test="${cruise.status != 'Started'}">
                    <button value="${cruiseShip}" type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal"
                            data-bs-target="#delete${cruise.id}">
                        <fmt:message key="cruises.button.deleteCruise"/>
                    </button>
                    </c:if>
                </td>
            </tr>
            <!-- Modal Edit-->
            <div class="modal fade" id="edit${cruise.id}" tabindex="-1" aria-labelledby="edit${cruise.id}Label"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="edit${cruise.id}Label"><fmt:message key="cruises.button.editCruise"/></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="/Cruise/controller?action=edit_cruise" method="post">
                                <input type="hidden" name="cruiseId" value="${cruise.id}">
                                <fmt:message key="cruises.editForm.startDate"/>
                                <input type="date" name="startDate" class="form-control">
                                <fmt:message key="cruises.editForm.endDate"/>
                                <input type="date" name="endDate" class="form-control">
                                <fmt:message key="cruises.editForm.ticketPrice"/>
                                <input type="number" name="ticketPrice" class="form-control">
                                <br/>
                                <button class="btn btn-md btn-primary btn-block" type="submit"><fmt:message
                                        key="common.button.confirm"/></button>
                            </form>
                            <br/>
                            <hr/>
                            <a href="controller?action=add_route_to_cruise&cruiseId=${cruise.id}"
                               class="btn btn-md btn-primary"><fmt:message key="cruises.editForm.button.changeRoute"/></a>
                            <a href="controller?action=add_ship_to_cruise&cruiseId=${cruise.id}"
                               class="btn btn-md btn-primary"><fmt:message key="cruises.editForm.button.changeShip"/></a>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                <fmt:message key="common.button.close"/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal Change Status-->
            <div class="modal fade" id="changeStatus${cruise.id}" tabindex="-1"
                 aria-labelledby="changeStatus${cruise.id}Label" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="changeStatus${cruise.id}Label">
                                <fmt:message key="common.button.changeStatus"/></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="/Cruise/controller?action=change_cruise_status" method="post">
                                <fmt:message key="cruises.editForm.changeStatus"/>
                                <input type="hidden" name="cruiseId" value="${cruise.id}">
                                <select name="status" class="form-select" required>
                                    <option value="Available">Available</option>
                                    <option value="Sold out">Sold out</option>
                                    <option value="Canceled">Canceled</option>
                                </select>
                                <br/>
                                <button class="btn btn-md btn-primary btn-block" type="submit"><fmt:message
                                        key="common.button.confirm"/></button>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                <fmt:message key="common.button.close"/></button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal Delete-->
            <div class="modal fade" id="delete${cruise.id}" tabindex="-1" aria-labelledby="delete${cruise.id}Label"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="delete${cruise.id}Label"><fmt:message key="cruises.button.deleteCruise"/></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <fmt:message key="cruises.text.deleteCruise"/> : <c:out value="${cruise.id}"/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="common.button.close"/></button>
                            <a href="controller?action=delete_cruise&id=${cruise.id}"
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
                <a href="controller?action=cruises&page=${i-1}&limit=${requestScope.limit}&sortBy=${requestScope.sortBy}"
                   class="btn btn-sm btn-secondary fw-bold border-white bg-black">${i}</a>
            </c:forEach>
        </div>
    </div>

</div>

<!-- Modal Add Cruise Ship-->
<div class="modal fade" id="createModal" tabindex="-1" aria-labelledby="createModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="createModalLabel"><fmt:message key="cruises.button.createCruise"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="/Cruise/controller?action=add_cruise" method="post">
                    <fmt:message key="cruises.addForm.startDate"/>
                    <input type="date" name="startDate" class="form-control" required>
                    <fmt:message key="cruises.addForm.endDate"/>
                    <input type="date" name="endDate" class="form-control" required>
                    <fmt:message key="cruises.addForm.status"/>
                    <select name="status" class="form-select" required>
                        <option value="Available">Available</option>
                        <option value="Sold out">Sold out</option>
                        <option value="Canceled">Canceled</option>
                    </select>
                    <fmt:message key="cruises.addForm.ticketPrice"/>
                    <input type="number" name="ticketPrice" class="form-control" required>
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
</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>
