package com.cruise.controller.command.admin.cruise;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.service.CruiseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for changing cruise status. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class ChangeCruiseStatusCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ChangeCruiseStatusCommand.class);
    private final CruiseService cruiseService;

    public ChangeCruiseStatusCommand(){
        cruiseService = AppContext.getInstance().getCruiseService();
    }

    /**
     * Called from main controller. Tries to change cruise status.
     *
     * @param req for get cruise and new status
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISES_COMMAND;
        try {
            Cruise cruise = cruiseService.findById(Integer.parseInt(req.getParameter("cruiseId")));
            cruiseService.changeStatus(cruise, req.getParameter("status"));
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
