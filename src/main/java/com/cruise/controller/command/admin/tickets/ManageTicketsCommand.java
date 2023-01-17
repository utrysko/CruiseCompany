package com.cruise.controller.command.admin.tickets;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;

import com.cruise.model.Ticket;
import com.cruise.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

public class ManageTicketsCommand implements Command {
    private TicketService ticketService;
    public ManageTicketsCommand(){
        ticketService = AppContext.getInstance().getTicketService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.MANAGE_TICKETS_PAGE;
        String ticketId = req.getParameter("findTicketId");
        List<Ticket> tickets = new ArrayList<>();
        if (ticketId != null){
            try {
                tickets.add(ticketService.findById(Integer.parseInt(ticketId)));
            } catch (ServiceException e) {
                req.setAttribute("error", e.getMessage());
            }
            req.setAttribute("tickets", tickets);
            return forward;
        }
        int orderBy = PaginationUtil.getSortBy(req);
        int limit = PaginationUtil.getLimit(req);
        int offset = PaginationUtil.getPage(req) * limit;
        int amount;
        try {
            amount = ticketService.getAllTickets().size();
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        int pages = PaginationUtil.getPages(amount, limit);
        try {
            tickets = ticketService.getTicketsInOrderAndLimit(orderBy, limit, offset);
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("pages", pages);
        req.setAttribute("tickets", tickets);
        return forward;
    }
}
