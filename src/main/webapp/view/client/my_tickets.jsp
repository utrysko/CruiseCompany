<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="controller?action=my_tickets" scope="request"/>
<!doctype html>
<html class="">
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title><fmt:message key="myTickets.title"/></title>
</head>
<body class="text-center">
<jsp:include page="/view/templates/client_navbar.jsp"/>
<div class="container">
    <div class="row"><h2><fmt:message key="myTickets.title"/></h2></div>
    <hr/>
    <div class="row justify-content-md-center">
<c:set var="ticket" scope="request" value="${tickets}"/>
<c:forEach items="${tickets}" var="ticket">

    <div class="col-md-3">
    <div class="card" style="width: 100%; background-color: aqua">
        <div class="card-body">
            <h5 class="card-title"><fmt:message key="myTickets.ticket.cruise"/> ${ticket.id}</h5>
            <p class="card-text"><fmt:message key="cruises.table.startDate"/>: ${ticket.cruise.start}</p>
            <p class="card-text"><fmt:message key="cruises.table.endDate"/>: ${ticket.cruise.end}</p>
            <p class="card-text"><fmt:message key="myTickets.ticket.shipName"/>: ${ticket.cruise.cruiseShip.name}</p>
            <p class="card-text"><fmt:message key="myTickets.ticket.price"/>: ${ticket.cruise.ticketPrice}</p>
            <a href="/Cruise/controller?action=download_ticket&ticketId=${ticket.id}" class="btn btn-primary"><fmt:message key="myTickets.ticket.button"/></a>
        </div>
    </div>
    </div>

</c:forEach>
    </div>
</div>
</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>