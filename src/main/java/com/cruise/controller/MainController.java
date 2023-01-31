package com.cruise.controller;

import com.cruise.controller.command.Command;
import com.cruise.controller.command.CommandFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main Controller Servlet. Implements command pattern. Chooses command to execute and redirect or forward result.
 *
 * @author Vasyl Utrysko.
 * @version 1.0
 */

@WebServlet("/controller")
public class MainController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(MainController.class);

    /**
     * Calls and executes command and then forwards requestDispatcher
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.getRequestDispatcher(handleRequest(req, resp)).forward(req, resp);
    }

    /**
     * Calls and command action and then sendRedirect for PRG pattern implementation
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(handleRequest(req, resp));
    }

    /**
     * Obtains path to use in doGet and doPost methods.
     * @return path
     */
    private String handleRequest(HttpServletRequest req, HttpServletResponse resp){
        CommandFactory commandFactory = CommandFactory.getInstance();
        Command command = commandFactory.getCommand(req);
        return command.execute(req, resp);
    }
}
