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
 * Class for edit cruise. Accessible only by an authorized client.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class EditProfileCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(EditProfileCommand.class);
    private final UserService userService;
    public EditProfileCommand (){
        userService = AppContext.getInstance().getUserService();
    }

    /**
     * Called from main controller. Tries to edit user.
     *
     * @param req for get userDTO
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.PERSONAL_CABINET_COMMAND;
        UserDTO userDTO;
        try{
            userDTO = getUserDTO(req);
            userService.update(userDTO);
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }
    /**
     * Method to get UserDTO from request
     * @param req to get userDTO
     * @return UserDTO
     */
    private UserDTO getUserDTO(HttpServletRequest req) throws ServiceException {
        String userId = req.getParameter("userId");
        User user = userService.findById(Integer.parseInt(userId));

        String login = req.getParameter("login");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(Integer.parseInt(userId));
        if (login != null && !login.isEmpty()){
            userDTO.setLogin(login);
        } else {
            userDTO.setLogin(user.getLogin());
        }
        if (firstName != null && !firstName.isEmpty()){
            userDTO.setFirstName(firstName);
        } else {
            userDTO.setFirstName(user.getFirstName());
        }
        if (lastName != null && !lastName.isEmpty()){
            userDTO.setLastName(lastName);
        } else {
            userDTO.setLastName(user.getLastName());
        }
        if (email != null && !email.isEmpty()){
            userDTO.setEmail(email);
        } else {
            userDTO.setEmail(user.getEmail());
        }
        return userDTO;
    }
}
