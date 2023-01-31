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
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Class for getting client document. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class ShowDocumentCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ShowDocumentCommand.class);
    private final TicketService ticketService;
    public ShowDocumentCommand(){
        ticketService = AppContext.getInstance().getTicketService();
    }

    /**
     * Called from main controller. Tries to show client document.
     *
     * @param req for get ticket id
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.MANAGE_TICKETS_COMMAND;
        Ticket ticket;
        try {
            ticket = ticketService.findById(Integer.parseInt(req.getParameter("ticketId")));
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
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
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        return "";
    }
}
