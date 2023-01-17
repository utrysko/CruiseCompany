package com.cruise.controller.command.admin.cruiseShip;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.CruiseShip;
import com.cruise.model.Staff;
import com.cruise.service.CruiseShipService;
import com.cruise.service.StaffService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class EditStaffCommand implements Command {
    private CruiseShipService cruiseShipService;
    private StaffService staffService;
    public EditStaffCommand(){
        cruiseShipService = AppContext.getInstance().getCruiseShipService();
        staffService = AppContext.getInstance().getStaffService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.EDIT_STAFF_PAGE;
        String cruiseShipId = req.getParameter("cruiseShipId");
        String staffId = req.getParameter("findStaffId");
        CruiseShip cruiseShip = null;
        List<Staff> listStaff = new ArrayList<>();
        if (staffId != null){
            try {
                cruiseShip = cruiseShipService.findById(Integer.parseInt(cruiseShipId));
                listStaff.add(staffService.findById(Integer.parseInt(staffId)));
            } catch (ServiceException e) {
                req.setAttribute("error", e.getMessage());
            }
            req.setAttribute("cruiseShip", cruiseShip);
            req.setAttribute("listStaff", listStaff);
            return forward;
        }
        int orderBy = PaginationUtil.getSortBy(req);
        int limit = PaginationUtil.getLimit(req);
        int offset = PaginationUtil.getPage(req) * limit;
        int amount;
        try {
            cruiseShip = cruiseShipService.findById(Integer.parseInt(cruiseShipId));
            amount = staffService.getAllByCruiseId(Integer.parseInt(cruiseShipId)).size();
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        int pages = PaginationUtil.getPages(amount, limit);
        try {
            listStaff = staffService.getStaffInOrderAndLimit(orderBy, limit, offset, Integer.parseInt(cruiseShipId));
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("cruiseShip", cruiseShip);
        req.setAttribute("pages", pages);
        req.setAttribute("listStaff", listStaff);
        return forward;
    }
}
