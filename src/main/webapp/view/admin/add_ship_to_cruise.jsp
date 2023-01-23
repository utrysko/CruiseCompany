<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="controller?action=add_ship_to_cruise&cruiseId=${requestScope.cruiseId}" scope="request"/>
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
  <div class="row">
    <table class="table table-striped">
      <thead>
      <tr>
        <th scope="col">Id</th>
        <th scope="col"><fmt:message key="cruiseShips.table.name"/></th>
        <th scope="col"><fmt:message key="cruiseShips.table.capacity"/></th>
        <th scope="col"><fmt:message key="common.table.status"/></th>
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
        <td><c:out value="${cruiseShip.status}"/></td>
        <td><button value="${cruiseShip}" type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#addShip${cruiseShip.name}">
          <fmt:message key="cruiseShips.button.addRouteToCruise"/>
        </button></td>
      </tr>
      <!-- Modal Add To Cruise-->
      <div class="modal fade" id="addShip${cruiseShip.name}" tabindex="-1" aria-labelledby="addShip${cruiseShip.name}Label" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="addShip${cruiseShip.name}Label"><fmt:message key="cruiseShips.button.addRouteToCruise"/></h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <fmt:message key="cruiseShips.text.addRouteToCruise"/> : ${cruiseShip.name}
              <form action="/Cruise/controller?action=add_ship_to_cruise" method="post">
                <input type="hidden" name="cruiseShipId" value="${cruiseShip.id}">
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

</div>
</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>
