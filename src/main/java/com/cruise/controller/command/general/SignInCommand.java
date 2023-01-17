package com.cruise.controller.command.general;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.UserDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Role;
import com.cruise.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignInCommand implements Command {
    UserService service;
    public SignInCommand(){
        service = AppContext.getInstance().getUserService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = AllPath.PAGE_SING_IN;
        HttpSession session = request.getSession();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null){
            return forward;
        }

        UserDTO userDTO;
        try {
           userDTO = service.signIn(login, password);
        } catch (ServiceException e) {
            session.setAttribute("error", e.getMessage());
            return forward;
        }
        Role userRole = Role.getRole(userDTO);
        if (userRole == Role.ADMIN) {
            forward = AllPath.ADMIN_MENU;
        }
        if (userRole == Role.CLIENT) {
            forward = AllPath.CLIENT_MENU;
        }
        session.setAttribute("user", userDTO);
        session.setAttribute("userRole", userRole);
        return forward;
    }
}
