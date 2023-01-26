package com.cruise.dao;


import com.cruise.connection.DataSource;
import com.cruise.dao.mysqlDao.MysqlDAOFactory;

public abstract class DAOFactory {

    private static DAOFactory instance;


    public static synchronized DAOFactory getInstance() {
        if (instance == null){
            instance = new MysqlDAOFactory(new DataSource());
        }
        return instance;
    }

    public abstract UserDAO getUserDAO();
    public abstract CruiseShipDAO getCruiseShipDAO();
    public abstract CruiseDAO getCruiseDAO();
    public abstract RouteDAO getRouteDAO();
    public abstract StaffDAO getStaffDAO();
    public abstract TicketDAO getTicketDAO();
}
