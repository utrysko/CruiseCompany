package com.cruise.controller;

import com.cruise.controller.command.Command;
import com.cruise.controller.command.CommandFactory;
import com.cruise.controller.filter.SecurityFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main Controller Servlet.
 *
 * @author Vasyl Utrysko.
 */

@WebServlet("/controller")
public class MainController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(SecurityFilter.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.getRequestDispatcher(handleRequest(req, resp)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(handleRequest(req, resp));
    }
    private String handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandFactory commandFactory = CommandFactory.getInstance();
        Command command = commandFactory.getCommand(req);
        return command.execute(req, resp);
    }
}
