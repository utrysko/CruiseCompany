package com.cruise.controller.command.client;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.User;
import com.cruise.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class for changing user password. Accessible only by an authorized client.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class ChangePasswordCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ChangePasswordCommand.class);
    private final UserService userService;
    public ChangePasswordCommand(){
        userService = AppContext.getInstance().getUserService();
    }

    /**
     * Called from main controller. Tries to change user password.
     *
     * @param req for get old password and new password
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.PERSONAL_CABINET_COMMAND;
        HttpSession session = req.getSession();
        String oldPassword = req.getParameter("oldPassword");
        String newPasswordFirst = req.getParameter("newPasswordFirst");
        String newPasswordSecond = req.getParameter("newPasswordSecond");
        if(!newPasswordFirst.equals(newPasswordSecond)){
            session.setAttribute("error", "new password don't match");
            return forward;
        }
        try {
            User user = userService.findById(Integer.parseInt(req.getParameter("userId")));
            userService.changePassword(user, newPasswordFirst, oldPassword);
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            session.setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
