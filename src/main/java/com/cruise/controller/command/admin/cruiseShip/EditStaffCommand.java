package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.controller.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.utils.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.entities.Staff;
import com.cruise.model.service.CruiseShipService;
import com.cruise.model.service.StaffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for edit cruise. Accessible only by admin.
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class EditStaffCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(EditStaffCommand.class);
    private final CruiseShipService cruiseShipService;
    private final StaffService staffService;
    public EditStaffCommand(){
        cruiseShipService = AppContext.getInstance().getCruiseShipService();
        staffService = AppContext.getInstance().getStaffService();
    }

    /**
     * Called from main controller. Tries to get all staff on ship.
     *
     * @param req for get ship and staff on ship
     * @return path to redirect from main controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.EDIT_STAFF_PAGE;
        String cruiseShipId = req.getParameter("cruiseShipId");
        String staffId = req.getParameter("findStaffId");
        List<Staff> listStaff = new ArrayList<>();
        CruiseShip cruiseShip;
        if (staffId != null){
            return getById(req, forward, cruiseShipId, staffId, listStaff);
        }
        int orderBy = PaginationUtil.getSortBy(req);
        int limit = PaginationUtil.getLimit(req);
        int offset = PaginationUtil.getPage(req) * limit;
        int amount;
        int pages;
        try {
            cruiseShip = cruiseShipService.findById(Integer.parseInt(cruiseShipId));
            amount = staffService.countAll(Integer.parseInt(cruiseShipId));
            pages = PaginationUtil.getPages(amount, limit);
            listStaff = staffService.getStaffInOrderAndLimit(orderBy, limit, offset, Integer.parseInt(cruiseShipId));
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("cruiseShip", cruiseShip);
        req.setAttribute("pages", pages);
        req.setAttribute("listStaff", listStaff);
        return forward;
    }

    /**
     * Method to get Staff by id.
     *
     * @return path to forward from main controller
     */
    private String getById(HttpServletRequest req, String forward, String cruiseShipId, String staffId, List<Staff> listStaff) {
        CruiseShip cruiseShip = null;
        try {
            cruiseShip = cruiseShipService.findById(Integer.parseInt(cruiseShipId));
            listStaff.add(staffService.findById(Integer.parseInt(staffId), cruiseShip.getId()));
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        req.setAttribute("cruiseShip", cruiseShip);
        req.setAttribute("listStaff", listStaff);
        return forward;
    }
}
