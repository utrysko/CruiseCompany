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
 * Class for replenish balance. Accessible only by an authorized client.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class ReplenishBalanceCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ReplenishBalanceCommand.class);
    private final UserService userService;
    public ReplenishBalanceCommand(){
        userService = AppContext.getInstance().getUserService();
    }

    /**
     * Called from main controller. Tries to replenish user balance.
     *
     * @param req to get user and sum of replenishment
     * @return path to redirect from main controller
     */
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
            LOG.error(e.getMessage());
            session.setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
