package com.cruise.controller.command.admin.cruise;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.CruiseDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.service.CruiseService;
import com.cruise.service.CruiseShipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class EditCruiseCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(EditCruiseCommand.class);
    private CruiseService cruiseService;
    private CruiseShipService cruiseShipService;
    public EditCruiseCommand(){
        cruiseService = AppContext.getInstance().getCruiseService();
        cruiseShipService = AppContext.getInstance().getCruiseShipService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISES_COMMAND;
        try {
            Cruise cruise = cruiseService.findById(Integer.parseInt(req.getParameter("cruiseId")));
            CruiseDTO cruiseDTO = getCruiseDTO(req, cruise);
            cruiseService.update(cruiseDTO);
            cruiseShipService.changeAvailableDate(cruise.getCruiseShip(), cruiseDTO.getEnd());
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }
    private CruiseDTO getCruiseDTO(HttpServletRequest req, Cruise cruise){
        CruiseDTO cruiseDTO = new CruiseDTO();
        cruiseDTO.setId(cruise.getId());
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String ticketPrice = req.getParameter("ticketPrice");
        cruiseDTO.setStart(cruise.getStart());
        if (startDate != null && !startDate.isEmpty()){
            cruiseDTO.setStart(Date.valueOf(startDate));
        }
        cruiseDTO.setEnd(cruise.getEnd());
        if (endDate != null && !endDate.isEmpty()){
            cruiseDTO.setEnd(Date.valueOf(endDate));
        }
        cruiseDTO.setStatus(cruise.getStatus());
        cruiseDTO.setTicketPrice(cruise.getTicketPrice());
        if (ticketPrice != null && !ticketPrice.isEmpty()){
            cruiseDTO.setTicketPrice(Double.parseDouble(ticketPrice));
        }
        return cruiseDTO;
    }
}
