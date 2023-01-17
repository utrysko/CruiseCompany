package com.cruise.controller.command.general;

import com.cruise.controller.command.Command;
import com.cruise.controller.filter.SecurityFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

/**
 * Internationalization controller command.
 *
 * @author Vasyl Utrysko
 */
public class I18NCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(SecurityFilter.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String language = request.getParameter("language");
        String page = request.getParameter("localePage");
        System.out.println(page);

        String fmtLocale = "javax.servlet.jsp.jstl.fmt.locale";
        String defaultLocale = "defaultLocale";

        if (language.equals("ua")) {
            Config.set(session, fmtLocale, "ua");
            session.setAttribute(defaultLocale, "ua");
        } else {
            Config.set(session, fmtLocale, "en");
            session.setAttribute(defaultLocale, "en");
        }
        return page;
    }
}
