package com.cruise.controller.command.admin.tickets;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Ticket;
import com.cruise.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TicketChangeStatusCommand implements Command {
    private TicketService ticketService;
    public TicketChangeStatusCommand(){
        ticketService = AppContext.getInstance().getTicketService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.MANAGE_TICKETS_COMMAND;
        String ticketId = req.getParameter("ticketId");
        try {
            Ticket ticket = ticketService.findById(Integer.parseInt(ticketId));
            ticketService.changeStatus(ticket, req.getParameter("status"));
        } catch (ServiceException e){
            req.setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
