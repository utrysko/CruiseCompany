<%@ include file="jspf/page.jspf" %>
<%@ include file="jspf/taglib.jspf" %>
<%--
  Author: Vasyl Utrysko
--%>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"></jsp:include>
<head>
    <title><fmt:message key="login.title"/></title>
    <!-- Custom styles for this template -->
    <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">
</head>
<body class="text-center">
<form class="form-signin" method="post" action="controller?action=sign_in">
    <h1 class="h3 mb-3 font-weight-normal">Dream Cruise</h1>
    <c:if test="${sessionScope.error != null}"><h4 style="color: red"><fmt:message key="${sessionScope.error}"/></h4>${sessionScope.remove('error')}</c:if>
    <input type="text" id="login" name="login" class="form-control" pattern="[a-z]{4,10}" title="lowercase a-z length = 4 to 10" placeholder="<fmt:message key="login.placeholder.login"/>" required>
    <input type="password" id="password" name="password" class="form-control" placeholder="<fmt:message key="login.placeholder.password"/>" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login.button.login"/></button>
    <p class="mt-5 mb-3 text-muted">&copy; Dream Cruise 2022</p>
</form>
</body>
</html>
