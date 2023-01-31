package com.cruise.controller.command.client;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.UserDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.User;
import com.cruise.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for getting user instance. Accessible only by an authorized client.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class PersonalCabinetCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(PersonalCabinetCommand.class);
    private final UserService userService;
    public PersonalCabinetCommand(){
        userService = AppContext.getInstance().getUserService();
    }

    /**
     * Called from main controller. Tries to get user.
     *
     * @param req for get parameters to get user
     * @return path to forward from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.PERSONAL_CABINET_PAGE;
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        User user;
        try {
            user = userService.findById(userDTO.getId());
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return AllPath.CLIENT_MENU;
        }
        req.setAttribute("user", user);
        return forward;
    }
}
