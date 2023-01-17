<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="controller?action=cruise_ships" scope="request"/>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title><fmt:message key="cruiseShips.title"/></title>
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
                <input type="hidden" name="action" value="cruise_ships">
                <div class="col-auto">
                    <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal"
                            data-bs-target="#addModal"><fmt:message key="cruiseShips.button.addCruiseShip"/>
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
                        <option value="2"><fmt:message key="cruiseShips.table.name"/></option>
                        <option value="3"><fmt:message key="cruiseShips.table.capacity"/></option>
                        <option value="4"><fmt:message key="cruiseShips.table.freeSpaces"/></option>
                    </select>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-3"><fmt:message key="common.filtering.filter"/></button>
                </div>
            </form>
        </div>
        <div class="col-md-4">
            <form class="row" action="/Cruise/controller" method="get">
                <input type="hidden" name="action" value="cruise_ships">
                <div class="col-auto"><span class="form-control"> <fmt:message key="common.filtering.findById"/>:</span></div>
                <div class="col-md-3"><input type="number" name="findCruiseShipId" class="form-control"/></div>
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
                <th scope="col"><fmt:message key="cruiseShips.table.name"/></th>
                <th scope="col"><fmt:message key="cruiseShips.table.capacity"/></th>
                <th scope="col"><fmt:message key="cruiseShips.table.freeSpaces"/></th>
                <th scope="col"><fmt:message key="common.table.status"/></th>
                <th scope="col"><fmt:message key="cruiseShips.table.dateFrom"/></th>
                <th scope="col"><fmt:message key="common.table.action"/></th>
            </tr>
            </thead>
            <c:set var="cruiseShip" scope="request" value="${cruiseShips}"/>
            <c:forEach items="${cruiseShips}" var="cruiseShip">
            <tbody>
            <tr>
                <th scope="row"><c:out value="${cruiseShip.id}"/></th>
                <td><c:out value="${cruiseShip.name}"/></td>
                <td><c:out value="${cruiseShip.capacity}"/></td>
                <td><c:out value="${cruiseShip.freeSpaces}"/></td>
                <td><c:out value="${cruiseShip.status}"/></td>
                <td><c:out value="${cruiseShip.availableFrom}"/></td>
                <td>
                    <button value="${cruiseShip}" type="button" class="btn btn-sm btn-primary"
                            data-bs-toggle="modal" data-bs-target="#edit${cruiseShip.name}">
                        <fmt:message key="cruiseShips.button.editShip"/>
                    </button>
                </td>
                <td>
                    <button value="${cruiseShip}" type="button" class="btn btn-sm btn-primary"
                            data-bs-toggle="modal" data-bs-target="#changeStatus${cruiseShip.name}">
                        <fmt:message key="common.button.changeStatus"/>
                    </button>
                </td>
                <td>
                    <a href="controller?action=edit_staff&cruiseShipId=${cruiseShip.id}"
                       class="btn btn-sm btn-primary"><fmt:message key="cruiseShips.button.editStaff"/></a>
                </td>
                <td>
                    <button value="${cruiseShip}" type="button" class="btn btn-sm btn-primary"
                            data-bs-toggle="modal" data-bs-target="#delete${cruiseShip.name}">
                        <fmt:message key="cruiseShips.button.deleteShip"/>
                    </button>
                </td>
            </tr>
            <!-- Modal Edit-->
            <div class="modal fade" id="edit${cruiseShip.name}" tabindex="-1"
                 aria-labelledby="edit${cruiseShip.name}Label" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="edit${cruiseShip.name}Label"><fmt:message key="cruiseShips.button.editShip"/></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="/Cruise/controller?action=edit_cruise_ship" method="post">
                                <input type="hidden" name="cruiseShipId" value="${cruiseShip.id}">
                                <fmt:message key="cruiseShips.editForm.name"/>
                                <input type="text" name="name" class="form-control">
                                <fmt:message key="cruiseShips.editForm.capacity"/>
                                <input type="number" name="capacity" class="form-control">
                                <fmt:message key="cruiseShips.editForm.freeSpaces"/>
                                <input type="number" name="freeSpaces" class="form-control">
                                <br/>
                                <button class="btn btn-md btn-primary btn-block" type="submit"><fmt:message
                                        key="common.button.confirm"/></button>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="common.button.close"/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal Change Status-->
            <div class="modal fade" id="changeStatus${cruiseShip.name}" tabindex="-1"
                 aria-labelledby="changeStatus${cruiseShip.name}Label" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="changeStatus${cruiseShip.name}Label">
                                <fmt:message key="common.button.changeStatus"/>
                            </h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="/Cruise/controller?action=cruise_ship_change_status" method="post">
                                <fmt:message key="cruiseShips.editForm.status"/>
                                <input type="hidden" name="cruiseShipId" value="${cruiseShip.id}">
                                <select name="status" class="form-select">
                                    <option value="Available">Available</option>
                                    <option value="Reserved">Reserved</option>
                                    <option value="In Service">In Service</option>
                                </select>
                                <br/>
                                <button class="btn btn-md btn-primary btn-block" type="submit"><fmt:message
                                        key="common.button.confirm"/></button>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                <fmt:message key="common.button.close"/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="delete${cruiseShip.name}" tabindex="-1"
                 aria-labelledby="delete${cruiseShip.name}Label" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="delete${cruiseShip.name}Label">
                                <fmt:message key="cruiseShips.button.deleteShip"/>
                                </h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <fmt:message key="cruiseShips.text.deleteCruiseShip"/> : <c:out value="${cruiseShip.name}"/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                <fmt:message key="common.button.close"/>
                            </button>
                            <a href="controller?action=delete_cruise_ship&id=${cruiseShip.id}"
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
                <a href="controller?action=cruise_ships&page=${i-1}&limit=${requestScope.limit}&sortBy=${requestScope.sortBy}"
                   class="btn btn-sm btn-secondary fw-bold border-white bg-black">${i}</a>
            </c:forEach>
        </div>
    </div>

</div>

<!-- Modal Add Cruise-->
<div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="addModalLabel"><fmt:message key="cruiseShips.button.addCruiseShip"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="/Cruise/controller?action=add_cruise_ship" method="post">
                    <fmt:message key="cruiseShips.addForm.name"/>
                    <input type="text" name="name" class="form-control">
                    <fmt:message key="cruiseShips.addForm.capacity"/>
                    <input type="text" name="capacity" class="form-control">
                    <fmt:message key="cruiseShips.addForm.freeSpaces"/>
                    <input type="text" name="freeSpaces" class="form-control">
                    <fmt:message key="cruiseShips.addForm.status"/>
                    <select name="status" class="form-select">
                        <option value="Available">Available</option>
                        <option value="Reserved">Reserved</option>
                        <option value="In Service">In Service</option>
                    </select>
                    <br/>
                    <button class="btn btn-md btn-primary btn-block" type="submit">
                        <fmt:message key="common.button.confirm"/></button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <fmt:message key="common.button.close"/></button>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>
