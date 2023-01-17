package com.cruise.controller.command.client;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Ticket;
import com.cruise.service.TicketService;
import com.mysql.cj.jdbc.Blob;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BuyTicketCommand implements Command {
    private TicketService ticketService;
    public BuyTicketCommand(){
        ticketService = AppContext.getInstance().getTicketService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CHOOSE_CRUISE_COMMAND;
        Ticket ticket = getTicket(req);
        try {
            ticketService.create(ticket);
        } catch (ServiceException e){
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }
    private Ticket getTicket(HttpServletRequest req){
        Ticket ticket = new Ticket();
        ticket.setClientId(Integer.parseInt(req.getParameter("clientId")));
        ticket.setCruiseId(Integer.parseInt(req.getParameter("cruiseId")));
        Blob blob = null;
        try {
            ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> files = sf.parseRequest(req);
            blob = new Blob(files.get(0).getInputStream().readAllBytes(), null);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        ticket.setStatus("Created");
        ticket.setDocument(blob);
        return ticket;
    }
}
