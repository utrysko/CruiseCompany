package com.cruise.controller.command.general;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.UserDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Register controller command.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class RegisterCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);

    private final UserService service;
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

        try {
            UserDTO userDTO = getUserDTO(request, login);
            service.register(userDTO, password);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            request.getSession().setAttribute("error", e.getMessage());
            return forward;
        }
        return AllPath.PAGE_INDEX;
    }

    private static UserDTO getUserDTO(HttpServletRequest request, String login) {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(login);
        userDTO.setFirstName(request.getParameter("firstName"));
        userDTO.setLastName(request.getParameter("lastName"));
        userDTO.setEmail(request.getParameter("email"));
        return userDTO;
    }
}