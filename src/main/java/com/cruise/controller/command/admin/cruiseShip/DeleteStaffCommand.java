package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Staff;
import com.cruise.service.StaffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteStaffCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(DeleteStaffCommand.class);
    private final StaffService staffService;
    public DeleteStaffCommand(){
        staffService = AppContext.getInstance().getStaffService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.EDIT_STAFF_COMMAND;
        int cruiseId = Integer.parseInt(req.getParameter("id"));
        try {
            Staff staff = staffService.findById(cruiseId);
            staffService.delete(staff);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        return forward;
    }
}
