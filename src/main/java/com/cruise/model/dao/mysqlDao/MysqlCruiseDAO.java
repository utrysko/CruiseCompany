package com.cruise.model.dao.mysqlDao;

import com.cruise.model.connection.DataSource;
import com.cruise.model.dao.CruiseDAO;
import com.cruise.model.dao.CruiseShipDAO;
import com.cruise.model.dao.RouteDAO;
import com.cruise.exceptions.DAOException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;
import com.cruise.model.entities.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of CruiseDAO interface for MySQL
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class MysqlCruiseDAO implements CruiseDAO {

    private final DataSource dataSource;
    private final CruiseShipDAO cruiseShipDAO;
    private final RouteDAO routeDAO;
    public MysqlCruiseDAO(DataSource dataSource, CruiseShipDAO cruiseShipDAO, RouteDAO routeDAO){
        this.dataSource = dataSource;
        this.cruiseShipDAO = cruiseShipDAO;
        this.routeDAO = routeDAO;
    }

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM cruises WHERE id = ?";
    private static final String SQL_GET_ALL_CRUISE_ORDER_BY_ID =
            "SELECT * FROM cruises ORDER BY id";
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(id) AS total FROM cruises";
    private static final String SQL_CHECK_ROUTE =
            "SELECT COUNT(id) AS total FROM cruises WHERE route_id = ?";
    private static final String SQL_CRUISES_IN_ORDER_AND_LIMIT =
            "SELECT * FROM cruises ORDER BY ? LIMIT ? OFFSET ?";
    private static final String SQL_INSERT =
            "INSERT INTO cruises ( start, end, status, ticket_price, free_spaces) VALUES ( ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE cruises SET start = ?, end = ?, status = ?, ticket_price = ?, free_spaces = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM cruises WHERE id = ?";
    private static final String SQL_CHANGE_STATUS =
            "UPDATE cruises SET status = ? WHERE id = ?";
    private static final String SQL_CHANGE_FREE_SPACES =
            "UPDATE cruises SET free_spaces = ? WHERE id = ?";
    private static final String SQL_ADD_SHIP_TO_CRUISE =
            "UPDATE cruises SET cruise_ship_id = ? WHERE id = ?";
    private static final String SQL_ADD_ROUTE_TO_CRUISE =
            "UPDATE cruises SET route_id = ? WHERE id = ?";

    @Override
    public Optional<Cruise> findById(int id) throws DAOException {
        Optional<Cruise> cruise = Optional.empty();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)){
            int k = 0;
            pst.setInt(++k, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                cruise = Optional.of(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return cruise;
    }

    @Override
    public void create(Cruise cruise) throws DAOException {
        if (cruise.getId() != 0) {
            throw new DAOException("Cruise is already created, the cruise ID is not zero.");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)){
            int k = 0;
            pst.setDate(++k, cruise.getStart());
            pst.setDate(++k, cruise.getEnd());
            pst.setString(++k, cruise.getStatus());
            pst.setDouble(++k, cruise.getTicketPrice());
            pst.setInt(++k, cruise.getFreeSpaces());
            int affectedRows =pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Creating cruise failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pst.getGeneratedKeys()){
                if (!generatedKeys.next()){
                    throw new DAOException("Creating cruise failed, no generated key obtained.");
                }
                cruise.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Cruise> getAllCruise() throws DAOException {
        List<Cruise> cruises = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_CRUISE_ORDER_BY_ID)){
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                cruises.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
        return cruises;
    }

    @Override
    public int countAll() throws DAOException {
        int count = 0;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_COUNT_ALL)){
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) count = resultSet.getInt("total");
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
        return count;
    }

    @Override
    public List<Cruise> getCruisesInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException {
        List<Cruise> cruises = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CRUISES_IN_ORDER_AND_LIMIT)){
            int k = 0;
            pst.setInt(++k, orderBy);
            pst.setInt(++k, limit);
            pst.setInt(++k, offset);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                cruises.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
        return cruises;
    }

    @Override
    public void update(Cruise cruise) throws DAOException {
        if (cruise.getId() == 0) {
            throw new DAOException("Cruise is not created yet, the cruise ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)){
            int k = 0;
            pst.setDate(++k, cruise.getStart());
            pst.setDate(++k, cruise.getEnd());
            pst.setString(++k, cruise.getStatus());
            pst.setDouble(++k, cruise.getTicketPrice());
            pst.setInt(++k, cruise.getFreeSpaces());
            pst.setInt(++k, cruise.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Updating cruise failed, no rows affected.");
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void delete(Cruise cruise) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)){
            int k = 0;
            pst.setInt(++k, cruise.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Deleting cruise failed, no rows affected.");
            }
            cruise.setId(0);
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void changeStatus(Cruise cruise, String status) throws DAOException {
        if (cruise.getId() == 0) {
            throw new DAOException("Cruise is not created yet, the cruise ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CHANGE_STATUS)) {
            int k = 0;
            pst.setString(++k, status);
            pst.setInt(++k, cruise.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Changing status failed, no rows affected.");
            }
            cruise.setStatus(status);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void changeFreeSpaces(Cruise cruise, int freeSpaces) throws DAOException {
        if (cruise.getId() == 0) {
            throw new DAOException("Cruise is not created yet, the cruise ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CHANGE_FREE_SPACES)) {
            int k = 0;
            pst.setInt(++k, freeSpaces);
            pst.setInt(++k, cruise.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Changing status failed, no rows affected.");
            }
            cruise.setFreeSpaces(freeSpaces);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void addShipToCruise(CruiseShip cruiseShip, Cruise cruise) throws DAOException {
        if (cruise.getId() == 0) {
            throw new DAOException("Cruise is not created yet, the cruise ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_ADD_SHIP_TO_CRUISE)) {
            int k = 0;
            pst.setInt(++k, cruiseShip.getId());
            pst.setInt(++k, cruise.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Adding ship failed, no rows affected.");
            }
            cruise.setCruiseShip(cruiseShip);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void addRouteToCruise(Route route, Cruise cruise) throws DAOException {
        if (cruise.getId() == 0) {
            throw new DAOException("Cruise is not created yet, the cruise ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_ADD_ROUTE_TO_CRUISE)) {
            int k = 0;
            pst.setInt(++k, route.getId());
            pst.setInt(++k, cruise.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Adding route failed, no rows affected.");
            }
            cruise.setRoute(route);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public boolean cruiseUsedRoute(int idRoute) throws DAOException {
        int count = 0;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CHECK_ROUTE)){
            int k = 0;
            pst.setInt(++k, idRoute);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) count = resultSet.getInt("total");
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
        return count != 0;
    }

    private Cruise map(ResultSet resultSet) throws SQLException{
        Cruise cruise = new Cruise();
        cruise.setId(resultSet.getInt("id"));
        cruise.setStart(resultSet.getDate("start"));
        cruise.setEnd(resultSet.getDate("end"));
        cruise.setStatus(resultSet.getString("status"));
        cruise.setTicketPrice(resultSet.getDouble("ticket_price"));
        cruise.setFreeSpaces(resultSet.getInt("free_spaces"));
        Optional<CruiseShip> cruiseShip = cruiseShipDAO.findById(resultSet.getInt("cruise_ship_id"));
        cruiseShip.ifPresent(cruise::setCruiseShip);
        Optional<Route> route = routeDAO.findById(resultSet.getInt("route_id"));
        route.ifPresent(cruise::setRoute);
        return cruise;
    }
}
