<%@ include file="jspf/page.jspf" %>
<%@ include file="jspf/taglib.jspf" %>
<%--
  Author: Vasyl Utrysko
--%>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title>Error 404</title>
</head>
<body class="text-center">
<div class="container">
    <c:if test="${sessionScope.error != null}">
        <div class="form-control">
            <h2 style="color: red"><c:out value="${sessionScope.error}"/></h2>
                ${sessionScope.remove('error')}
        </div>
    </c:if>
    <c:if test="${requestScope.error != null}">
        <div class="form-control">
            <h2 style="color: red"><c:out value="${requestScope.error}"/></h2>
        </div>
    </c:if>
    <h1>Error 404</h1>
    <h2>Something went wrong</h2>
    <h6><a href="/Cruise/index.jsp">Go Home</a></h6>
</div>
</body>
</html>
