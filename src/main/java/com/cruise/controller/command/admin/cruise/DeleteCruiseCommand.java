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
 * Class for deleting cruise. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class DeleteCruiseCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(DeleteCruiseCommand.class);
    private final CruiseService cruiseService;
    public DeleteCruiseCommand(){
        cruiseService = AppContext.getInstance().getCruiseService();
    }

    /**
     * Called from main controller. Tries to delete cruise.
     *
     * @param req for get cruise
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISES_COMMAND;
        int cruiseId = Integer.parseInt(req.getParameter("id"));
        try {
            Cruise cruise = cruiseService.findById(cruiseId);
            cruiseService.delete(cruise);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
