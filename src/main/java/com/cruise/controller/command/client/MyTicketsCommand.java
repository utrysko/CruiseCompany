package com.cruise.controller.command.client;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.dto.UserDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.model.Ticket;
import com.cruise.model.User;
import com.cruise.service.CruiseService;
import com.cruise.service.TicketService;
import com.cruise.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class MyTicketsCommand implements Command {
    TicketService ticketService;
    CruiseService cruiseService;
    UserService userService;
    public MyTicketsCommand(){
        ticketService = AppContext.getInstance().getTicketService();
        cruiseService = AppContext.getInstance().getCruiseService();
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
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("tickets", tickets);
        return forward;
    }
}
