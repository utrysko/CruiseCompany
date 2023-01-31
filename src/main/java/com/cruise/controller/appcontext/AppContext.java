package com.cruise.controller.appcontext;

import com.cruise.model.dao.*;
import com.cruise.model.service.*;
import com.cruise.model.service.impl.*;

/**
 * Class creates all services and dao when application starts.
 *
 * @author  Vasyl Utrysko.
 * @version 1.0
 */
public class AppContext {
    private static AppContext appContext = new AppContext();
    /** DAO */
    private final UserDAO userDao = DAOFactory.getInstance().getUserDAO();
    private final CruiseShipDAO cruiseShipDao = DAOFactory.getInstance().getCruiseShipDAO();
    private final CruiseDAO cruiseDAO = DAOFactory.getInstance().getCruiseDAO();
    private final RouteDAO routeDao = DAOFactory.getInstance().getRouteDAO();
    private final StaffDAO staffDao = DAOFactory.getInstance().getStaffDAO();
    private final TicketDAO ticketDAO = DAOFactory.getInstance().getTicketDAO();


    /** Services */
    private final UserService userService = new UserServiceImpl(userDao);
    private final CruiseShipService cruiseShipService = new CruiseShipServiceImpl(cruiseShipDao);
    private final CruiseService cruiseService = new CruiseServiceImpl(cruiseDAO, cruiseShipDao);
    private final RouteService routeService = new RouteServiceImpl(routeDao, cruiseDAO);
    private final StaffService staffService = new StaffServiceImpl(staffDao);
    private final TicketService ticketService = new TicketServiceImpl(ticketDAO, cruiseDAO, userDao);

    /**
     * @return instance of AppContext
     */
    public static AppContext getInstance() {
        return appContext;
    }

    public UserService getUserService(){
        return userService;
    }
    public CruiseService getCruiseService(){
        return cruiseService;
    }
    public CruiseShipService getCruiseShipService(){
        return cruiseShipService;
    }
    public RouteService getRouteService(){
        return routeService;
    }
    public TicketService getTicketService(){
        return ticketService;
    }
    public StaffService getStaffService(){
        return staffService;
    }
}
