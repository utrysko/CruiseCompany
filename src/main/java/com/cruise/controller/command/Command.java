package com.cruise.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for the Command pattern implementation.
 *
 * @author Vasyl Utrysko
 */

public interface Command {
    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}
