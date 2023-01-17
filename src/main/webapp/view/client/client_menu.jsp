<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="view/client/client_menu.jsp" scope="request"/>
<!doctype html>
<html class="h-100">
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title><fmt:message key="index.title"/></title>
    <link href="https://getbootstrap.com/docs/5.2/examples/modals/modals.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/view/templates/client_navbar.jsp"/>
<div class="container">
    <div class="text-center">
        <h2>Welcome ${sessionScope.user.firstName}</h2>
        <img src="img/CruiseBanner.jpg">
    </div>
</div>

</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>