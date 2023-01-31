package com.cruise.controller.command.client;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Ticket;
import com.cruise.model.service.TicketService;
import com.mysql.cj.jdbc.Blob;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class for buy ticket. Accessible only by an authorized client.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class BuyTicketCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(BuyTicketCommand.class);
    private final TicketService ticketService;
    public BuyTicketCommand(){
        ticketService = AppContext.getInstance().getTicketService();
    }

    /**
     * Called from main controller. Tries to create new ticket.
     *
     * @param req to get ticket instance
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CHOOSE_CRUISE_COMMAND;
        try {
            Ticket ticket = getTicket(req);
            ticketService.create(ticket);
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }
    /**
     * Method to get Ticket from request
     * @param req to get ticket
     * @return Ticket
     */
    private Ticket getTicket(HttpServletRequest req) throws ServiceException{
        Ticket ticket = new Ticket();
        ticket.setClientId(Integer.parseInt(req.getParameter("clientId")));
        ticket.setCruiseId(Integer.parseInt(req.getParameter("cruiseId")));
        Blob blob;
        try {
            ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> files = sf.parseRequest(req);
            blob = new Blob(files.get(0).getInputStream().readAllBytes(), null);
        } catch (Exception e){
            throw new ServiceException("cannot load document");
        }
        ticket.setStatus("Created");
        ticket.setDocument(blob);
        return ticket;
    }
}
