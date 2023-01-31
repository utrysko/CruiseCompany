package com.cruise.controller.listener;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Context listener.
 *
 * @author Vasyl Utrysko.
 * @version 1.0
 */
public class ContextListener implements ServletContextListener {
    private static final Logger LOG = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.trace("Servlet context initialization starts");

        ServletContext servletContext = sce.getServletContext();
        initCommandFactory();
        initI18N(servletContext);

        LOG.trace("Servlet context initialization finished");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.trace("Servlet context destroyed");
    }

    /**
     * Initializes i18n system.
     */
    private void initI18N(ServletContext servletContext) {
        LOG.debug("I18N system initialization started");

        String localesValue = servletContext.getInitParameter("locales");
        if (localesValue == null || localesValue.isEmpty()) {
            LOG.warn("'locales' init parameter is empty, the default encoding will be used");
        } else {
            List<String> locales = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(localesValue);
            while (st.hasMoreTokens()) {
                String localeName = st.nextToken();
                locales.add(localeName);
            }

            LOG.debug("Application attribute set: locales --> " + locales);
            servletContext.setAttribute("locales", locales);
        }
        LOG.debug("I18N system initialization finished");
    }

    /**
     * Initializes CommandFactory.
     */
    private void initCommandFactory() {
        LOG.debug("Command container initialization started");

        try {
            Class.forName("com.cruise.controller.command.CommandFactory");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        LOG.debug("Command container initialization finished");
    }
}
