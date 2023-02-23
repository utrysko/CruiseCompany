<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<c:if test="${sessionScope.error != null}">
    <div class="form-control">
        <h2 style="color: red"><fmt:message key="${sessionScope.error}"/></h2>
            ${sessionScope.remove('error')}
    </div>
</c:if>
<c:if test="${requestScope.error != null}">
    <div class="form-control">
        <h2 style="color: red"><fmt:message key="${requestScope.error}"/></h2>
    </div>
</c:if>
