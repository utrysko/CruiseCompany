package com.cruise.controller.command.admin.cruise;

import com.cruise.appcontext.AppContext;
import com.cruise.controller.AllPath;
import com.cruise.controller.PaginationUtil;
import com.cruise.controller.command.Command;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.Cruise;
import com.cruise.service.CruiseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CruisesCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(CruisesCommand.class);

    private final CruiseService cruiseService;
    public CruisesCommand(){
        cruiseService = AppContext.getInstance().getCruiseService();
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String forward = AllPath.CRUISES_PAGE;
        String cruiseId = req.getParameter("findCruiseId");
        List<Cruise> cruises = new ArrayList<>();
        if (cruiseId != null){
            return findById(req, forward, cruiseId, cruises);
        }
        int orderBy = PaginationUtil.getSortBy(req);
        int limit = PaginationUtil.getLimit(req);
        int offset = PaginationUtil.getPage(req) * limit;
        int amount;
        int pages;
        try {
            amount = cruiseService.countAll();
            pages = PaginationUtil.getPages(amount, limit);
            cruises = cruiseService.getCruisesInOrderAndLimit(orderBy, limit, offset);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return forward;
        }
        req.setAttribute("pages", pages);
        req.setAttribute("cruises", cruises);
        return forward;
    }

    private String findById(HttpServletRequest req, String forward, String cruiseId, List<Cruise> cruises) {
        try {
            cruises.add(cruiseService.findById(Integer.parseInt(cruiseId)));
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
        }
        req.setAttribute("cruises", cruises);
        return forward;
    }
}
