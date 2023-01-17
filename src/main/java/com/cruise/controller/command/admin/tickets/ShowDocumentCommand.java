package com.cruise.controller.command.admin.tickets;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Ticket;
import com.cruise.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;


public class ShowDocumentCommand implements Command {
    private TicketService ticketService;
    public ShowDocumentCommand(){
        ticketService = AppContext.getInstance().getTicketService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.MANAGE_TICKETS_COMMAND;
        Ticket ticket;
        try {
            ticket = ticketService.findById(Integer.parseInt(req.getParameter("ticketId")));
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        resp.setContentType("image/gif");
        try {
            OutputStream os = resp.getOutputStream();
            byte [] bytes = ticket.getDocument().getBinaryStream().readAllBytes();
            os.write(bytes);
            os.flush();
            os.close();
        } catch (IOException | SQLException e) {
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        return "";
    }
}
