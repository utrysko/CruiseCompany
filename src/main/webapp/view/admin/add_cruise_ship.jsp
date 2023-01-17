<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"></jsp:include>
<head>
  <title><fmt:message key="addCruiseShip.title"/></title>
  <!-- Custom styles for this template -->
  <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">
</head>
<body class="text-center">
  <form class="form-signin" method="post" action="controller?action=add_cruise_ship">
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="addCruiseShip.header"/></h1>
    <c:if test="${requestScope.error != null}"><h4 style="color: red"><fmt:message key="${requestScope.error}"/></h4></c:if>
    <input type="text" name="name" class="form-control"
           placeholder="<fmt:message key="addCruiseShip.placeholder.name"/>" required>
    <input type="text" name="capacity" class="form-control"
           placeholder="<fmt:message key="addCruiseShip.placeholder.capacity"/>" required>
    <input type="text" name="freeSpaces" class="form-control"
           placeholder="<fmt:message key="addCruiseShip.placeholder.free.spaces"/>" required>
    <input type="text" name="status" class="form-control"
           placeholder="<fmt:message key="addCruiseShip.placeholder.status"/>" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
            key="common.button.confirm"/></button>
  </form>
</body>
</html>
