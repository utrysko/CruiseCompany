<%@ include file="view/jspf/page.jspf" %>
<%@ include file="view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="index.jsp" scope="page"/>
<!doctype html>
<html class="h-100">
<head>
    <jsp:include page="/view/templates/head.jsp"/>
    <title><fmt:message key="index.title"/></title>
</head>
<body>
<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <a href="index.jsp" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
            <span class="fs-4">Dream Cruise</span>
        </a>

        <ul class="nav nav-pills">
            <li class="nav-item"><a href="index.jsp" class="nav-link"><fmt:message
                    key="index.navbar.home"/></a></li>
            <li class="nav-item"><button type="button" class="btn btn-md btn-primary" data-bs-toggle="modal" data-bs-target="#signInModal">
                <fmt:message
                        key="index.navbar.login"/>
            </button></li>
            <li class="nav-item"><a href="/Cruise/controller?action=register" class="nav-link"><fmt:message
                    key="index.navbar.register"/></a></li>
            <li class="nav-item"><a href="#" class="nav-link"><fmt:message key="index.navbar.contact"/></a></li>
            <li class="nav-item">
                <form action="controller?action=change_language" method="POST" class="d-flex">
                    <input type="hidden" name="localePage" value="${pageScope.localePage}">
                    <label>
                        <select class="form-select" name="language" onchange='submit();'>
                            <option value="en" ${sessionScope.defaultLocale eq 'en' ? 'selected' : ''}>en</option>
                            <option value="ua" ${sessionScope.defaultLocale eq 'ua' ? 'selected' : ''}>ua</option>
                        </select>
                    </label>
                </form>
            </li>
        </ul>
    </header>
</div>
<div class="container">
    <jsp:include page="/view/templates/display_error.jsp"/>
    <div class="text-center">
        <h1><fmt:message key="index.welcome"/></h1>
        <img src="view/img/CruiseBanner.jpg">

    </div>
</div>
<div class="modal fade" id="signInModal" tabindex="-1" aria-labelledby="signInModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5 " id="signInModalLabel">Sign In</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form class="text-center" method="post" action="controller?action=sign_in">
                    <h1 class="h3 mb-3 font-weight-normal">Dream Cruise</h1>
                    <c:if test="${requestScope.error != null}"><h4 style="color: red"><fmt:message key="${requestScope.error}"/></h4></c:if>
                    <fmt:message key="login.placeholder.login"/>
                    <input type="text" id="login" name="login" class="form-control" pattern="[a-z]{4,10}" title="lowercase a-z length = 4 to 10" placeholder="<fmt:message key="login.placeholder.login"/>" required>
                    <fmt:message key="login.placeholder.password"/>
                    <input type="password" id="password" name="password" class="form-control" placeholder="<fmt:message key="login.placeholder.password"/>" required>
                    <br/>
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login.button.login"/></button>
                    <p class="mt-5 mb-3 text-muted">&copy; Dream Cruise 2022</p>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>
