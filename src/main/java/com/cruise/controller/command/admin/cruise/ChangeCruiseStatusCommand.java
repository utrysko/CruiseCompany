package com.cruise.controller.command.admin.cruise;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.service.CruiseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeCruiseStatusCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ChangeCruiseStatusCommand.class);
    private CruiseService cruiseService;
    public ChangeCruiseStatusCommand(){
        cruiseService = AppContext.getInstance().getCruiseService();
    }
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
