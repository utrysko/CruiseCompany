package com.cruise.model.dao.mysqlDao;

import com.cruise.model.connection.DataSource;
import com.cruise.model.dao.CruiseShipDAO;
import com.cruise.model.dao.StaffDAO;
import com.cruise.exceptions.DAOException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of CruiseShipDAO interface for MySQL
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class MysqlCruiseShipDAO implements CruiseShipDAO {

    private final DataSource dataSource;
    private final StaffDAO staffDAO;

    public MysqlCruiseShipDAO(DataSource dataSource, StaffDAO staffDAO){
        this.dataSource = dataSource;
        this.staffDAO = staffDAO;
    }

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM cruise_ship WHERE id = ?";

    private static final String SQL_FIND_BY_NAME =
            "SELECT * FROM cruise_ship WHERE name = ?";
    private static final String SQL_GET_ALL_CRUISE_SHIPS_ORDER_BY_ID =
            "SELECT * FROM cruise_ship ORDER BY id";
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(id) AS total FROM cruise_ship";
    private static final String SQL_GET_ALL_FREE_SHIPS_BY_DATE =
            "SELECT * FROM cruise_ship WHERE available_from < ? ORDER BY id";
    private static final String SQL_ROUTES_IN_ORDER_AND_LIMIT =
            "SELECT * FROM cruise_ship ORDER BY ? LIMIT ? OFFSET ?";
    private static final String SQL_INSERT =
            "INSERT INTO cruise_ship (name, capacity, status, available_from) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE cruise_ship SET name = ?, capacity = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM cruise_ship WHERE name = ?";
    private static final String SQL_CHANGE_STATUS =
            "UPDATE cruise_ship SET status = ? WHERE id = ?";
    private static final String SQL_CHANGE_AVAILABLE_DATE =
            "UPDATE cruise_ship SET available_from = ? WHERE id = ?";


    @Override
    public Optional<CruiseShip> findById(int id) throws DAOException {
        Optional<CruiseShip> cruiseShip = Optional.empty();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)){
            int k = 0;
            pst.setInt(++k, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                cruiseShip = Optional.of(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return cruiseShip;
    }

    @Override
    public Optional<CruiseShip> findByName(String name) throws DAOException {
        Optional<CruiseShip> cruiseShip = Optional.empty();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_NAME)){
            int k = 0;
            pst.setString(++k, name);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                cruiseShip = Optional.of(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return cruiseShip;
    }

    @Override
    public void create(CruiseShip cruiseShip) throws DAOException {
        if (cruiseShip.getId() != 0) {
            throw new DAOException("CruiseShip is already created, the cruiseShip ID is not zero.");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)){
            int k = 0;
            pst.setString(++k, cruiseShip.getName());
            pst.setInt(++k, cruiseShip.getCapacity());
            pst.setString(++k, cruiseShip.getStatus());
            pst.setDate(++k, cruiseShip.getAvailableFrom());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Creating cruiseShip failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pst.getGeneratedKeys()){
                if (!generatedKeys.next()){
                    throw new DAOException("Creating cruiseShip failed, no generated key obtained.");
                }
                cruiseShip.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<CruiseShip> getAllCruiseShip() throws DAOException {
        List<CruiseShip> cruiseShips = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_CRUISE_SHIPS_ORDER_BY_ID)){
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                cruiseShips.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
        return cruiseShips;
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
    public List<CruiseShip> getFreeCruiseShip(Cruise cruise) throws DAOException {
        List<CruiseShip> cruiseShips = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_FREE_SHIPS_BY_DATE)){
            int k = 0;
            pst.setDate(++k, cruise.getStart());
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                cruiseShips.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
        return cruiseShips;
    }

    @Override
    public List<CruiseShip> getCruiseShipsInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException {
        List<CruiseShip> cruiseShips = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_ROUTES_IN_ORDER_AND_LIMIT)){
            int k = 0;
            pst.setInt(++k, orderBy);
            pst.setInt(++k, limit);
            pst.setInt(++k, offset);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                cruiseShips.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
        return cruiseShips;
    }

    @Override
    public void update(CruiseShip cruiseShip) throws DAOException {
        if (cruiseShip.getId() == 0) {
            throw new DAOException("CruiseShip is not created yet, the cruiseShip ID is zero");
        }
        try (Connection con = dataSource.getConnection();
            PreparedStatement pst = con.prepareStatement(SQL_UPDATE)){
            int k = 0;
            pst.setString(++k, cruiseShip.getName());
            pst.setInt(++k, cruiseShip.getCapacity());
            pst.setInt(++k, cruiseShip.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Updating cruiseShip failed, no rows affected.");
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void delete(CruiseShip cruiseShip) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)){
            int k = 0;
            pst.setString(++k, cruiseShip.getName());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Deleting cruiseShip failed, no rows affected.");
            }
            cruiseShip.setId(0);
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void changeStatus(CruiseShip cruiseShip, String status) throws DAOException {
        if (cruiseShip.getId() == 0) {
            throw new DAOException("CruiseShip is not created yet, the cruiseShip ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CHANGE_STATUS)){
            int k = 0;
            pst.setString(++k, status);
            pst.setInt(++k, cruiseShip.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Changing status failed, no rows affected.");
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void changeAvailableDate(CruiseShip cruiseShip, Date date) throws DAOException {
        if (cruiseShip.getId() == 0) {
            throw new DAOException("CruiseShip is not created yet, the cruiseShip ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CHANGE_AVAILABLE_DATE)){
            int k = 0;
            pst.setDate(++k, date);
            pst.setInt(++k, cruiseShip.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Changing available date failed, no rows affected.");
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    private CruiseShip map(ResultSet resultSet) throws SQLException{
        CruiseShip cruiseShip = new CruiseShip();
        cruiseShip.setId(resultSet.getInt("id"));
        cruiseShip.setName(resultSet.getString("name"));
        cruiseShip.setCapacity(resultSet.getInt("capacity"));
        cruiseShip.setStatus(resultSet.getString("status"));
        cruiseShip.setAvailableFrom(resultSet.getDate("available_from"));
        cruiseShip.setStaff(staffDAO.getAllByCruiseId(cruiseShip.getId()));
        return cruiseShip;
    }
}
