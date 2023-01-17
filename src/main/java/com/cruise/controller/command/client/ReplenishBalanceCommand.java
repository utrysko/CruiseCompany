package com.cruise.controller.command.client;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.User;
import com.cruise.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReplenishBalanceCommand implements Command {
    private UserService userService;
    public ReplenishBalanceCommand(){
        userService = AppContext.getInstance().getUserService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.PERSONAL_CABINET_COMMAND;
        HttpSession session = req.getSession();
        String userId = req.getParameter("userId");
        String sum = req.getParameter("sum");
        try {
            User user = userService.findById(Integer.parseInt(userId));
            userService.changeBalance(user, user.getBalance() + Double.parseDouble(sum));
        } catch (ServiceException e){
            session.setAttribute("error", e.getMessage());
        }
        return forward;
    }
}