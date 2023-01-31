<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="view/admin/admin_menu.jsp" scope="request"/>
<!doctype html>
<html class="h-100">
<jsp:include page="/view/templates/head.jsp"/>
<head>
  <title><fmt:message key="index.title"/></title>
</head>
<body>
<jsp:include page="/view/templates/admin_navbar.jsp"/>
<div class="container" >
  <div class="text-center">
    <h2><fmt:message key="admin.menu.welcome"/> ${sessionScope.user.firstName}</h2>
    <img src="img/CruiseBanner.jpg">
  </div>
</div>

</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>
