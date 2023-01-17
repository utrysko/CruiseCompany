package com.cruise.controller.filter;

import com.cruise.controller.AllPath;
import com.cruise.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter.
 *
 * @author Vasyl Utrysko
 */

public class SecurityFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(SecurityFilter.class);

    // commands permission
    private static Map<Role, List<String>> permissionMap = new HashMap<>();
    private static List<String> general = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.debug("Filter initialization starts");
        // roles
        permissionMap.put(Role.ADMIN, asList(filterConfig.getInitParameter("admin")));
        permissionMap.put(Role.CLIENT, asList(filterConfig.getInitParameter("client")));
        LOG.trace("Access map --> " + permissionMap);

        // general
        general = asList(filterConfig.getInitParameter("general"));
        LOG.trace("Common commands --> " + general);

        LOG.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        LOG.debug("Filter starts");
        if (accessAllowed(servletRequest)) {
            LOG.debug("Filter finished");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String errorMessages = "You do not have permission to access the requested resource";
            servletRequest.setAttribute("errorMessage", errorMessages);

            LOG.trace("Set the request attribute: errorMessage --> " + errorMessages);

            servletRequest.getRequestDispatcher(AllPath.ERROR_PAGE).forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        LOG.debug("Filter destroyed");
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;

        String command = req.getParameter("action");
        if (command == null || command.isEmpty()) {
            return false;
        }

        if (general.contains(command)) {
            return true;
        }

        HttpSession session = req.getSession(false);
        if (session == null) {
            return false;
        }

        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole == null) {
            return false;
        }

        return permissionMap.get(userRole).contains(command);
    }

    private List<String> asList(String param) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(param);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }
}
