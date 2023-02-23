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
<body class="text-center">
<jsp:include page="/view/templates/admin_navbar.jsp"/>
<div class="container" >
  <div class="text-center">
    <button type="button" class="btn btn-lg btn-primary" data-bs-toggle="modal"
            data-bs-target="#makeAdmin">
      <fmt:message key="admin.menu.makeAdmin"/>
    </button>
    <br/>
    <h2><fmt:message key="admin.menu.welcome"/> ${sessionScope.user.firstName}</h2>
    <img src="img/CruiseBanner.jpg">
  </div>
</div>
<!-- Modal Make Admin-->
<div class="modal fade" id="makeAdmin" tabindex="-1" aria-labelledby="makeAdminLabel"
     aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="makeAdminLabel"><fmt:message key="admin.menu.makeAdmin"/></h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form action="/Cruise/controller?action=make_admin" method="post">
            <fmt:message key="admin.menu.enter.login"/>
        <br/>
            <input type="text" name="login" class="form-control" required>
          <br/>
          <button class="btn btn-md btn-primary btn-block" type="submit"><fmt:message
                  key="common.button.confirm"/></button>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="common.button.close"/></button>
      </div>
    </div>
  </div>
</div>
</body>
<jsp:include page="/view/templates/footer.jsp"/>
</html>
