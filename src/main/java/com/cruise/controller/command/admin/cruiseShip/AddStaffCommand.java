package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.dto.StaffDTO;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.service.StaffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for adding staff to cruise ship. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class AddStaffCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AddStaffCommand.class);
    private final StaffService staffService;
    public AddStaffCommand(){
        staffService = AppContext.getInstance().getStaffService();
    }

    /**
     * Called from main controller. Tries to add staff to cruise ship.
     *
     * @param req to get staff instance
     * @return path to redirect from main controller
     */
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

    /**
     * Method to get StaffDTO from request
     * @param req to get StaffDTO
     * @return StaffDTO
     */
    private StaffDTO getStaffDTO(HttpServletRequest req){
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setFirstName(req.getParameter("firstName"));
        staffDTO.setLastName(req.getParameter("lastName"));
        staffDTO.setPosition(req.getParameter("position"));
        staffDTO.setCruiseShipId(Integer.parseInt(req.getParameter("cruiseShipId")));
        return staffDTO;
    }
}
