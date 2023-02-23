package com.cruise.controller.command.admin;

import com.cruise.controller.AllPath;
import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.command.Command;
import com.cruise.dto.UserDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Role;
import com.cruise.model.service.UserService;
import com.cruise.utils.ConvertorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for make new Admin. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class MakeAdminCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(MakeAdminCommand.class);
    private final UserService userService;

    public MakeAdminCommand(){
        userService = AppContext.getInstance().getUserService();
    }

    /**
     * Called from main controller. Tries to make new admin.
     *
     * @param req to get cruise instance
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.ADMIN_MENU;
        String login = req.getParameter("login");
        try {
            UserDTO userDTO = ConvertorUtil.convertUserToDTO(userService.findByLogin(login));
            userDTO.setRoleId(Role.getRoleId(Role.ADMIN));
            userService.update(userDTO);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
