<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"></jsp:include>
<head>
  <title><fmt:message key="staff.form.header"/></title>
  <!-- Custom styles for this template -->
  <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">
</head>
<body class="text-center">
<form class="form-signin" method="post" action="controller?action=add_staff">
  <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="staff.form.header"/></h1>
  <jsp:include page="/view/templates/display_error.jsp"/>
  <input type="text" name="firstName" class="form-control"
         placeholder="<fmt:message key="staff.form.firstName"/>" required>
  <input type="text" name="lastName" class="form-control"
         placeholder="<fmt:message key="staff.form.lastName"/>" required>
  <input type="text" name="<fmt:message key="staff.form.position"/>" class="form-control"
         placeholder="position" required>
  <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
          key="common.button.confirm"/></button>
</form>
</body>
</html>
