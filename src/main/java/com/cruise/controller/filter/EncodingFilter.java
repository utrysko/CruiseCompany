package com.cruise.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Encoding filter.
 *
 * @author Vasyl Utrysko.
 * @version 1.0
 */

public class EncodingFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(EncodingFilter.class);
    private String encoding;

    /**
     * Destroy filter method.
     */
    public void destroy() {
        LOG.debug("Filter destroyed");
    }

    /**
     * Main filter method.
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        LOG.debug("Filter starts");

        String requestEncoding = req.getCharacterEncoding();

        if (requestEncoding == null) {
            LOG.trace("Request encoding is null, set encoding --> " + encoding);
            req.setCharacterEncoding(encoding);
        }
        LOG.debug("Filter finished");
        chain.doFilter(req, resp);
    }

    /**
     * Init filter method.
     */
    public void init(FilterConfig config) {
        LOG.debug("Filter initialization starts");
        encoding = config.getInitParameter("encoding");
        LOG.trace("Encoding from web.xml --> " + encoding);
        LOG.debug("Filter initialization finished");
    }
}