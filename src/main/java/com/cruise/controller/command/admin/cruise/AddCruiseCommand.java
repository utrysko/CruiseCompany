package com.cruise.controller.command.admin.cruise;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.CruiseDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.service.CruiseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class AddCruiseCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AddCruiseCommand.class);
    private final CruiseService cruiseService;
    public AddCruiseCommand(){
        cruiseService = AppContext.getInstance().getCruiseService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISES_COMMAND;
        CruiseDTO cruiseDTO = getCruiseDTO(req);
        try {
            cruiseService.create(cruiseDTO);
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        return forward;
    }
    private CruiseDTO getCruiseDTO(HttpServletRequest req){
        CruiseDTO cruiseDTO = new CruiseDTO();
        cruiseDTO.setStart(Date.valueOf(req.getParameter("startDate")));
        cruiseDTO.setEnd(Date.valueOf(req.getParameter("endDate")));
        cruiseDTO.setStatus(req.getParameter("status"));
        cruiseDTO.setTicketPrice(Double.parseDouble(req.getParameter("ticketPrice")));
        cruiseDTO.setFreeSpaces(Integer.parseInt(req.getParameter("freeSpaces")));
        return cruiseDTO;
    }
}