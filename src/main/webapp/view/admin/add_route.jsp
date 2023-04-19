<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title><fmt:message key="addRoute.title"/></title>
    <!-- Custom styles for this template -->
    <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">
</head>
<body class="text-center">
<div class="container">
<form class="form-signin" method="post" action="controller?action=add_route">
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="addRoute.header"/></h1>
    <jsp:include page="/view/templates/display_error.jsp"/>
    <input type="text" name="startPort" class="form-control"
           placeholder="<fmt:message key="addRoute.placeholder.startPort"/>" pattern="^[A-ZА-ЩЬЮЯҐІЇЄ]{1}[a-zа-щьюяґіїє]{1,20}[- ]?[A-ZА-ЩЬЮЯҐІЇЄ]?[a-zа-щьюяґіїє]*" required>
    <c:forEach var="i" begin="2" end="${requestScope.numberOfPorts - 1}">
        <input type="text" name="middlePort${i}" class="form-control"
               placeholder="<fmt:message key="addRoute.placeholder.middlePort"/>" pattern="^[A-ZА-ЩЬЮЯҐІЇЄ]{1}[a-zа-щьюяґіїє]{1,20}[- ]?[A-ZА-ЩЬЮЯҐІЇЄ]?[a-zа-щьюяґіїє]*" required>
    </c:forEach>
    <input type="hidden" name="numberOfPorts" value="${requestScope.numberOfPorts}">
    <input type="text" name="endPort" class="form-control"
           placeholder="<fmt:message key="addRoute.placeholder.endPort"/>" pattern="^[A-ZА-ЩЬЮЯҐІЇЄ]{1}[a-zа-щьюяґіїє]{1,20}[- ]?[A-ZА-ЩЬЮЯҐІЇЄ]?[a-zа-щьюяґіїє]*" required>
    <a href="controller?action=routes"
       class="btn btn-lg btn-primary"><fmt:message key="common.button.back"/></a>
    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
            key="common.button.confirm"/></button>

</form>
</div>
</body>
</html>
