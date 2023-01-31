package com.cruise.model.dao;


import com.cruise.model.connection.DataSource;
import com.cruise.model.dao.mysqlDao.MysqlDAOFactory;

/**
 * Class represents abstract factory that provides concrete factories to get DAOs
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public abstract class DAOFactory {
    /** Singleton */
    private static DAOFactory instance;

    /**
     * Obtains single instance of the class.
     *
     * @return concrete DAO factory
     */
    public static synchronized DAOFactory getInstance() {
        if (instance == null){
            instance = new MysqlDAOFactory(new DataSource());
        }
        return instance;
    }

    /**
     * Obtains instance of UserDAO class
     *
     * @return UserDAO for required database type
     */
    public abstract UserDAO getUserDAO();

    /**
     * Obtains instance of CruiseShipDAO class
     *
     * @return CruiseShipDAO for required database type
     */
    public abstract CruiseShipDAO getCruiseShipDAO();

    /**
     * Obtains instance of CruiseDAO class
     *
     * @return CruiseDAO for required database type
     */
    public abstract CruiseDAO getCruiseDAO();

    /**
     * Obtains instance of RouteDAO class
     *
     * @return RouteDAO for required database type
     */
    public abstract RouteDAO getRouteDAO();

    /**
     * Obtains instance of StaffDAO class
     *
     * @return StaffDAO for required database type
     */
    public abstract StaffDAO getStaffDAO();

    /**
     * Obtains instance of TicketDAO class
     *
     * @return TicketDAO for required database type
     */
    public abstract TicketDAO getTicketDAO();
}
