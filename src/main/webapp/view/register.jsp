<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"></jsp:include>
<head>
    <title><fmt:message key="register.title"/></title>
    <!-- Custom styles for this template -->
    <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">
</head>
<body class="text-center">
<form class="form-signin" method="post" action="controller?action=register">
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="register.header"/></h1>
    <c:if test="${requestScope.error != null}"><h4 style="color: red"><fmt:message key="${requestScope.error}"/></h4></c:if>
    <input type="text" id="login" name="login" class="form-control" placeholder="<fmt:message key="login.placeholder.login"/>" required>
    <input type="text" name="firstName" class="form-control" placeholder="<fmt:message key="register.placeholder.firstname"/>" required>
    <input type="text" name="lastName" class="form-control" placeholder="<fmt:message key="register.placeholder.lastname"/>" required>
    <input type="email" name="email" class="form-control" placeholder="<fmt:message key="register.placeholder.email"/>" required>
    <input type="password" id="password" name="password" class="form-control" placeholder="<fmt:message key="login.placeholder.password"/>" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="register.button.register"/></button>
</form>
</body>
</html>
