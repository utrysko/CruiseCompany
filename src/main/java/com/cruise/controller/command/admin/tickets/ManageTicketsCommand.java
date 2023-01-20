package com.cruise.controller.command.admin.tickets;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;

import com.cruise.model.Ticket;
import com.cruise.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

public class ManageTicketsCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ManageTicketsCommand.class);

    private final TicketService ticketService;
    public ManageTicketsCommand(){
        ticketService = AppContext.getInstance().getTicketService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.MANAGE_TICKETS_PAGE;
        String ticketId = req.getParameter("findTicketId");
        List<Ticket> tickets = new ArrayList<>();
        if (ticketId != null){
            return getById(req, forward, ticketId, tickets);
        }
        int orderBy = PaginationUtil.getSortBy(req);
        int limit = PaginationUtil.getLimit(req);
        int offset = PaginationUtil.getPage(req) * limit;
        int amount;
        int pages;
        try {
            amount = ticketService.countAll();
            pages = PaginationUtil.getPages(amount, limit);
            tickets = ticketService.getTicketsInOrderAndLimit(orderBy, limit, offset);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("pages", pages);
        req.setAttribute("tickets", tickets);
        return forward;
    }

    private String getById(HttpServletRequest req, String forward, String ticketId, List<Ticket> tickets) {
        try {
            tickets.add(ticketService.findById(Integer.parseInt(ticketId)));
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        req.setAttribute("tickets", tickets);
        return forward;
    }
}
