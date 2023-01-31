package com.cruise.controller.command.admin.tickets;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Ticket;
import com.cruise.model.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for changing ticket status. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class TicketChangeStatusCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(TicketChangeStatusCommand.class);
    private final TicketService ticketService;
    public TicketChangeStatusCommand(){
        ticketService = AppContext.getInstance().getTicketService();
    }

    /**
     * Called from main controller. Tries to change ticket status.
     *
     * @param req for get ticket and new status
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.MANAGE_TICKETS_COMMAND;
        String ticketId = req.getParameter("ticketId");
        try {
            Ticket ticket = ticketService.findById(Integer.parseInt(ticketId));
            ticketService.changeStatus(ticket, req.getParameter("status"));
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
