package com.cruise.controller.command.client;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Ticket;
import com.cruise.model.User;
import com.cruise.service.TicketService;
import com.cruise.service.UserService;
import com.cruise.utils.TicketBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TicketDownloadCommand implements Command {
    private UserService userService;
    private TicketService ticketService;
    public TicketDownloadCommand(){
        userService = AppContext.getInstance().getUserService();
        ticketService = AppContext.getInstance().getTicketService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = AllPath.MY_TICKETS_COMMAND;
        try {
            Ticket ticket = ticketService.findById(Integer.parseInt(request.getParameter("ticketId")));
            User user = userService.findById(ticket.getClientId());
            TicketBuilder.ticketPdf(response, user, ticket.getCruise(), ticket);
        } catch (ServiceException e){
            request.setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
