package com.cruise.appcontext;

import com.cruise.dao.*;
import com.cruise.service.*;
import com.cruise.service.impl.*;

/**
 * Class creates all services and dao when application starts.
 *
 * @author  Vasyl Utrysko.
 */
public class AppContext {
    private static volatile AppContext appContext = new AppContext();
    //dao
    private final UserDAO userDao = DAOFactory.getInstance().getUserDAO();
    private final CruiseShipDAO cruiseShipDao = DAOFactory.getInstance().getCruiseShipDAO();
    private final CruiseDAO cruiseDAO = DAOFactory.getInstance().getCruiseDAO();
    private final RouteDAO routeDao = DAOFactory.getInstance().getRouteDAO();
    private final StaffDAO staffDao = DAOFactory.getInstance().getStaffDAO();
    private final TicketDAO ticketDAO = DAOFactory.getInstance().getTicketDAO();


    //services
    private final UserService userService = new UserServiceImpl(userDao);
    private final CruiseShipService cruiseShipService = new CruiseShipServiceImpl(cruiseShipDao);
    private final CruiseService cruiseService = new CruiseServiceImpl(cruiseDAO, cruiseShipDao);
    private final RouteService routeService = new RouteServiceImpl(routeDao, cruiseDAO);
    private final StaffService staffService = new StaffServiceImpl(staffDao);
    private final TicketService ticketService = new TicketServiceImpl(ticketDAO, cruiseDAO, userDao);

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
