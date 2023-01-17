<%@ include file="/view/jspf/page.jspf" %>
<%@ include file="/view/jspf/taglib.jspf" %>
<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <a href="index.jsp" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
            <span class="fs-4">Dream Cruise</span>
        </a>

        <ul class="nav nav-pills">
            <li><span class="btn btn-md btn-info"><fmt:message key="admin.navbar.currentDate"/><ctg:now/></span> </li>
            <li class="nav-item"><a href="/Cruise/controller?action=cruises" class="nav-link"
                                    aria-current="page"><fmt:message key="admin.navbar.cruises"/></a></li>
            <li class="nav-item"><a href="/Cruise/controller?action=cruise_ships" class="nav-link"
                                    aria-current="page"><fmt:message key="admin.navbar.cruiseShips"/></a></li>
            <li class="nav-item"><a href="/Cruise/controller?action=routes" class="nav-link"
                                    aria-current="page"><fmt:message key="admin.navbar.routes"/></a></li>
            <li class="nav-item"><a href="/Cruise/controller?action=manage_tickets" class="nav-link"><fmt:message
                    key="admin.navbar.manage.tickets"/></a></li>
            <li class="nav-item"><a href="/Cruise/controller?action=logout" class="nav-link"><fmt:message
                    key="common.logout"/></a></li>
            <li class="nav-item">
                <form action="/Cruise/controller?action=change_language" method="POST" class="d-flex" >
                    <input type="hidden" name="localePage" value="${requestScope.localePage}">
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