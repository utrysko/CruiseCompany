package com.cruise.controller.command.client;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.UserDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Ticket;
import com.cruise.model.User;
import com.cruise.service.TicketService;
import com.cruise.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MyTicketsCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(MyTicketsCommand.class);
    private final TicketService ticketService;
    private final UserService userService;
    public MyTicketsCommand(){
        ticketService = AppContext.getInstance().getTicketService();
        userService = AppContext.getInstance().getUserService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.MY_TICKETS_PAGE;
        List<Ticket> tickets;
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        try {
            User user = userService.findById(userDTO.getId());
            tickets = ticketService.findAllByUser(user);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("tickets", tickets);
        return forward;
    }
}
