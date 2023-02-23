<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<%--
  author: Vasyl Utrysko
--%>
<c:set var="localePage" value="controller?action=personal_cabinet" scope="request"/>
<!doctype html>
<html>
<jsp:include page="/view/templates/head.jsp"/>
<head>
    <title><fmt:message key="personalCabinet.title"/></title>
    <!-- Custom styles for this template -->
</head>
<body class="">
<jsp:include page="/view/templates/client_navbar.jsp"/>
<div class="container">
    <jsp:include page="/view/templates/display_error.jsp"/>
    <br/>
        <div class="row justify-content-md-center">
    <div class="col-md-6 text-center">
    <table class="table table-striped table-hover">
        <tr>
            <td><h5><fmt:message key="personalCabinet.field.login"/></h5></td>
            <td><h5><c:out value="${user.login}"/></h5></td>
            <td><button value="${user}" type="button" class="btn btn-primary" data-bs-toggle="modal"
                        data-bs-target="#edit${user.id}"><fmt:message key="personalCabinet.button.editProfile"/></button></td>
        </tr>
        <tr>
            <td><h5><fmt:message key="personalCabinet.field.firstName"/></h5></td>
            <td><h5><c:out value="${user.firstName}"/></h5></td>
            <td><button value="${user}" type="button" class="btn btn-primary" data-bs-toggle="modal"
                        data-bs-target="#changePassword${user.id}"><fmt:message key="personalCabinet.button.changePassword"/></button></td>
        </tr>
        <tr>
            <td><h5><fmt:message key="personalCabinet.field.lastName"/></h5></td>
            <td><h5><c:out value="${user.lastName}"/></h5></td>
        </tr>
        <tr>
            <td><h5><fmt:message key="personalCabinet.field.email"/></h5></td>
            <td><h5><c:out value="${user.email}"/></h5></td>
            <td><button value="${user}" type="button" class="btn btn-primary" data-bs-toggle="modal"
                        data-bs-target="#editEmail${user.id}"><fmt:message key="personalCabinet.button.changeEmail"/></button></td>
        </tr>
        <tr>
            <td><h5><fmt:message key="personalCabinet.field.balance"/></h5></td>
            <td><h5><c:out value="${user.balance}"/></h5></td>
            <td><button value="${user}" type="button" class="btn btn-primary" data-bs-toggle="modal"
                        data-bs-target="#changeBalance${user.id}"><fmt:message key="personalCabinet.button.replenishBalance"/></button></td>
        </tr>
        <tr>
            <td><h5>Date :</h5></td>
            <td><h5><ctg:now/></h5></td>
        </tr>
    </table>
    </div>
        </div>
<!-- Modal Edit-->
<div class="modal fade" id="edit${user.id}" tabindex="-1" aria-labelledby="edit${user.id}Label"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="edit${user.id}Label"><fmt:message key="personalCabinet.button.editProfile"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="controller?action=edit_profile" method="post">
                    <input type="hidden" name="userId" value="${sessionScope.user.id}">
                    <p><fmt:message key="editProfile.enter.login"/></p>
                    <input type="text" name="login" class="form-control">
                    <p><fmt:message key="editProfile.enter.firstName"/></p>
                    <input type="text" name="firstName" class="form-control">
                    <p><fmt:message key="editProfile.enter.lastName"/></p>
                    <input type="text" name="lastName" class="form-control">
                    <br/>
                    <button type="submit" class="btn btn-lg btn-primary"><fmt:message key="common.button.confirm"/></button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="common.button.close"/></button>
            </div>
        </div>
    </div>
</div>
        <!-- Modal Edit Email-->
        <div class="modal fade" id="editEmail${user.id}" tabindex="-1" aria-labelledby="editEmail${user.id}Label"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="editEmail${user.id}Label"><fmt:message key="personalCabinet.button.changeEmail"/></h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="controller?action=edit_profile" method="post">
                            <input type="hidden" name="userId" value="${sessionScope.user.id}">
                            <p><fmt:message key="editProfile.enter.email"/></p>
                            <input type="email" name="email" class="form-control">
                            <br/>
                            <button type="submit" class="btn btn-lg btn-primary"><fmt:message key="common.button.confirm"/></button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="common.button.close"/></button>
                    </div>
                </div>
            </div>
        </div>
<!-- Modal Change Password-->
<div class="modal fade" id="changePassword${user.id}" tabindex="-1" aria-labelledby="changePassword${user.id}Label"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="changePassword${user.id}Label"><fmt:message key="personalCabinet.button.changePassword"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="controller?action=change_password" method="post">
                    <input type="hidden" name="userId" value="${sessionScope.user.id}">
                    <p><fmt:message key="editProfile.enter.oldPassword"/></p>
                    <input type="password" name="oldPassword" class="form-control">
                    <p><fmt:message key="editProfile.enter.newPassword"/></p>
                    <input type="password" name="newPasswordFirst" class="form-control">
                    <p><fmt:message key="editProfile.enter.newPasswordAgain"/></p>
                    <input type="password" name="newPasswordSecond" class="form-control">
                    <br/>
                    <button type="submit" class="btn btn-lg btn-primary"><fmt:message key="common.button.confirm"/></button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="common.button.close"/></button>
            </div>
        </div>
    </div>
</div>
<!-- Modal Change Balance-->
<div class="modal fade" id="changeBalance${user.id}" tabindex="-1" aria-labelledby="changeBalance${user.id}Label"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="changeBalance${user.id}Label"><fmt:message key="personalCabinet.button.replenishBalance"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="controller?action=replenish_balance" method="post">
                    <input type="hidden" name="userId" value="${sessionScope.user.id}">
                    <p><fmt:message key="editProfile.enter.topUpSum"/></p>
                    <input type="number" name="sum" class="form-control">
                    <br/>
                    <button type="submit" class="btn btn-lg btn-primary"><fmt:message key="common.button.confirm"/></button>
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
