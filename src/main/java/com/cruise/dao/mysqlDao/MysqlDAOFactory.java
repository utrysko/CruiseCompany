package com.cruise.dao.mysqlDao;

import com.cruise.connection.DataSource;
import com.cruise.dao.*;

public class MysqlDAOFactory extends DAOFactory {

    private final DataSource dataSource;
    private CruiseShipDAO cruiseShipDAO;
    private CruiseDAO cruiseDAO;
    private UserDAO userDAO;
    private TicketDAO ticketDAO;
    private RouteDAO routeDAO;
    private StaffDAO staffDAO;

    public MysqlDAOFactory(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public UserDAO getUserDAO() {
        if (userDAO == null){
            userDAO = new MysqlUserDAO(dataSource);
        }
        return userDAO;
    }

    @Override
    public CruiseShipDAO getCruiseShipDAO() {
        if (cruiseShipDAO == null){
            cruiseShipDAO = new MysqlCruiseShipDAO(dataSource, getStaffDAO());
        }
        return cruiseShipDAO;
    }

    @Override
    public CruiseDAO getCruiseDAO() {
        if (cruiseDAO == null){
            cruiseDAO = new MysqlCruiseDAO(dataSource, getCruiseShipDAO(), getRouteDAO());
        }
        return cruiseDAO;
    }

    @Override
    public RouteDAO getRouteDAO() {
        if (routeDAO == null){
            routeDAO = new MysqlRouteDAO(dataSource);
        }
        return routeDAO;
    }

    @Override
    public StaffDAO getStaffDAO() {
        if (staffDAO == null){
            staffDAO = new MysqlStaffDAO(dataSource);
        }
        return staffDAO;
    }

    @Override
    public TicketDAO getTicketDAO() {
        if (ticketDAO == null){
            ticketDAO = new MysqlTicketDAO(dataSource, getCruiseDAO());
        }
        return ticketDAO;
    }
}
