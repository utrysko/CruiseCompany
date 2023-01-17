package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.StaffDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.service.StaffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddStaffCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AddStaffCommand.class);
    private StaffService staffService;
    public AddStaffCommand(){
        staffService = AppContext.getInstance().getStaffService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StaffDTO staffDTO = getStaffDTO(req);
        String cruiseShipId = req.getParameter("cruiseShipId");
        try {
            staffService.create(staffDTO);
        } catch (ServiceException e){
            LOG.error(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        return AllPath.EDIT_STAFF_COMMAND + "&cruiseShipId=" + cruiseShipId;
    }

    private StaffDTO getStaffDTO(HttpServletRequest req){
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setFirstName(req.getParameter("firstName"));
        staffDTO.setLastName(req.getParameter("lastName"));
        staffDTO.setPosition(req.getParameter("position"));
        staffDTO.setCruiseShipId(Integer.parseInt(req.getParameter("cruiseShipId")));
        return staffDTO;
    }
}
