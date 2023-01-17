package com.cruise.controller.command.admin.cruise;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.model.CruiseShip;
import com.cruise.service.CruiseService;
import com.cruise.service.CruiseShipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AddShipToCruiseCommand implements Command {
    private CruiseService cruiseService;
    private CruiseShipService cruiseShipService;
    public AddShipToCruiseCommand(){
        cruiseService = AppContext.getInstance().getCruiseService();
        cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.ADD_SHIP_TO_CRUISE_PAGE;
        List<CruiseShip> cruiseShips;
        if (req.getParameter("cruiseShipId") != null){
            int cruiseShipId = Integer.parseInt(req.getParameter("cruiseShipId"));
            int cruiseId = Integer.parseInt(req.getParameter("cruiseId"));
            try {
                Cruise cruise = cruiseService.findById(cruiseId);
                CruiseShip cruiseShipOld = cruise.getCruiseShip();
                CruiseShip cruiseShipNew = cruiseShipService.findById(cruiseShipId);
                cruiseService.addShipToCruise(cruiseShipNew, cruise);
                cruiseShipService.changeAvailableDate(cruiseShipNew, cruise.getEnd());
                if (cruiseShipOld != null){
                    cruiseShipService.changeAvailableDate(cruiseShipOld, Date.valueOf(LocalDate.now()));
                }
            } catch (ServiceException e) {
                req.setAttribute("error", e.getMessage());
            }
            return AllPath.CRUISES_COMMAND;
        }
        try {
            int cruiseId = Integer.parseInt(req.getParameter("cruiseId"));
            Cruise cruise = cruiseService.findById(cruiseId);
            cruiseShips = cruiseShipService.getAllFreeCruiseShip(cruise);
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("cruiseShips", cruiseShips);
        req.setAttribute("cruiseId", req.getParameter("cruiseId"));
        return forward;
    }
}
