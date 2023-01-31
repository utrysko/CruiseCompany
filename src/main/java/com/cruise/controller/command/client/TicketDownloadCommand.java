package com.cruise.controller.command.client;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Ticket;
import com.cruise.model.entities.User;
import com.cruise.model.service.TicketService;
import com.cruise.model.service.UserService;
import com.cruise.utils.TicketBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for download ticket. Accessible only by an authorized client.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class TicketDownloadCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(TicketDownloadCommand.class);
    private final UserService userService;
    private final TicketService ticketService;
    public TicketDownloadCommand(){
        userService = AppContext.getInstance().getUserService();
        ticketService = AppContext.getInstance().getTicketService();
    }

    /**
     * Called from main controller. Tries to get ticket from database and transfer it into pdf.
     *
     * @param req to get ticket instance
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.MY_TICKETS_COMMAND;
        try {
            Ticket ticket = ticketService.findById(Integer.parseInt(req.getParameter("ticketId")));
            User user = userService.findById(ticket.getClientId());
            TicketBuilder.ticketPdf(resp, user, ticket.getCruise(), ticket);
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
