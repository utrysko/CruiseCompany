<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title><fmt:message key="register.title"/></title>
    <!-- Custom styles for this template -->
    <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">
</head>
<body class="text-center">
<form class="form-signin" method="post" action="controller?action=register">
    <jsp:include page="/view/templates/display_error.jsp"/>
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="register.header"/></h1>
    <input type="text" id="login" pattern="^[a-zA-Z0-9_]{3,16}$" name="login" class="form-control" placeholder="<fmt:message key="login.placeholder.login"/>" required>
    <input type="text" name="firstName" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,19}" class="form-control" placeholder="<fmt:message key="register.placeholder.firstname"/>" required>
    <input type="text" name="lastName" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,19}" class="form-control" placeholder="<fmt:message key="register.placeholder.lastname"/>" required>
    <input type="email" name="email" pattern="^[\w.%+-]+@[\w.-]+\.[a-z]{2,6}$" class="form-control" placeholder="<fmt:message key="register.placeholder.email"/>" required>
    <input type="password" id="password" name="password" class="form-control" placeholder="<fmt:message key="login.placeholder.password"/>" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="register.button.register"/></button>
</form>
</body>
</html>
