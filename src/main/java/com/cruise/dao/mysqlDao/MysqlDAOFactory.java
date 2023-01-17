package com.cruise.dao.mysqlDao;

import com.cruise.connection.DataSource;
import com.cruise.dao.*;

public class MysqlDAOFactory extends DAOFactory {

    private CruiseShipDAO cruiseShipDAO;
    private CruiseDAO cruiseDAO;
    private UserDAO userDAO;
    private TicketDAO ticketDAO;
    private RouteDAO routeDAO;
    private StaffDAO staffDAO;
    @Override
    public UserDAO getUserDAO() {
        if (userDAO == null){
            userDAO = new MysqlUserDAO(new DataSource());
        }
        return userDAO;
    }

    @Override
    public CruiseShipDAO getCruiseShipDAO() {
        if (cruiseShipDAO == null){
            cruiseShipDAO = new MysqlCruiseShipDAO(new DataSource());
        }
        return cruiseShipDAO;
    }

    @Override
    public CruiseDAO getCruiseDAO() {
        if (cruiseDAO == null){
            cruiseDAO = new MysqlCruiseDAO(new DataSource());
        }
        return cruiseDAO;
    }

    @Override
    public RouteDAO getRouteDAO() {
        if (routeDAO == null){
            routeDAO = new MysqlRouteDAO(new DataSource());
        }
        return routeDAO;
    }

    @Override
    public StaffDAO getStaffDAO() {
        if (staffDAO == null){
            staffDAO = new MysqlStaffDAO(new DataSource());
        }
        return staffDAO;
    }

    @Override
    public TicketDAO getTicketDAO() {
        if (ticketDAO == null){
            ticketDAO = new MysqlTicketDAO(new DataSource());
        }
        return ticketDAO;
    }
}
