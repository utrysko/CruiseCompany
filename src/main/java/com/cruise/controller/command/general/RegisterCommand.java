package com.cruise.controller.command.general;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.UserDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);

    UserService service;
    public RegisterCommand(){
        service = AppContext.getInstance().getUserService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = AllPath.REGISTER_PAGE;
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null){
            return forward;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(login);
        userDTO.setFirstName(request.getParameter("firstName"));
        userDTO.setLastName(request.getParameter("lastName"));
        userDTO.setEmail(request.getParameter("email"));
        try {
            service.register(userDTO, password);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            request.getSession().setAttribute("error", e.getMessage());
            return forward;
        }
        return AllPath.PAGE_INDEX;
    }
}