package com.cruise.dao.mysqlDao;

import com.cruise.connection.DataSource;
import com.cruise.exceptions.DAOException;
import com.cruise.dao.DAOFactory;
import com.cruise.dao.StaffDAO;
import com.cruise.model.CruiseShip;
import com.cruise.model.Staff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlStaffDAO implements StaffDAO {


    private DataSource dataSource;

    public MysqlStaffDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM staff WHERE id = ?";
    private static final String SQL_FIND_ALL_BY_CRUISE_ID =
            "SELECT * FROM staff WHERE cruise_ship_id = ?";
    private static final String SQL_STAFF_IN_ORDER_AND_LIMIT =
            "SELECT * FROM staff WHERE cruise_ship_id = ? ORDER BY ? LIMIT ? OFFSET ?";
    private static final String SQL_INSERT =
            "INSERT INTO staff (first_name, last_name, position, cruise_ship_id) VALUES (?, ? ,?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE staff SET  first_name = ?, last_name = ?, position = ?, cruise_ship_id = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM staff WHERE id = ?";


    @Override
    public Staff findById(int id) throws DAOException {
        Staff staff = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)){
            int k = 0;
            pst.setInt(++k, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                staff = map(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return staff;
    }

    @Override
    public List<Staff> getStaffInOrderAndLimit(int orderBy, int limit, int offset, int cruiseShipId) throws DAOException {
        List<Staff> staff = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_STAFF_IN_ORDER_AND_LIMIT)){
            int k = 0;
            pst.setInt(++k, cruiseShipId);
            pst.setInt(++k, orderBy);
            pst.setInt(++k, limit);
            pst.setInt(++k, offset);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                staff.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
        return staff;
    }

    @Override
    public List<Staff> getAllByCruiseId(int cruiseId) throws DAOException {
        List<Staff> staff = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_ALL_BY_CRUISE_ID)){
            int k = 0;
            pst.setInt(++k, cruiseId);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                staff.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return staff;
    }

    @Override
    public void create(Staff staff) throws DAOException {
        if (staff.getId() != 0) {
            throw new DAOException("Staff is already created, the user ID is not zero.");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)){
            int k = 0;
            pst.setString(++k, staff.getFirstName());
            pst.setString(++k, staff.getLastName());
            pst.setString(++k, staff.getPosition());
            pst.setInt(++k, staff.getCruiseShipId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Creating staff failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pst.getGeneratedKeys()){
                if (!generatedKeys.next()){
                    throw new DAOException("Creating staff failed, no generated key obtained.");
                }
                staff.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void delete(Staff staff) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)){
            int k = 0;
            pst.setInt(++k, staff.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Deleting staff failed, no rows affected.");
            }
            staff.setId(0);
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void update(Staff staff) throws DAOException {
        if (staff.getId() == 0) {
            throw new DAOException("Staff is not created yet, the user ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)){
            int k = 0;
            pst.setString(++k, staff.getFirstName());
            pst.setString(++k, staff.getLastName());
            pst.setString(++k, staff.getPosition());
            pst.setInt(++k, staff.getCruiseShipId());
            pst.setInt(++k, staff.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Updating staff failed, no rows affected.");
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    private Staff map(ResultSet resultSet) throws SQLException{
        Staff staff = new Staff();
        staff.setId(resultSet.getInt("id"));
        staff.setFirstName(resultSet.getString("first_name"));
        staff.setLastName(resultSet.getString("last_name"));
        staff.setPosition(resultSet.getString("position"));
        staff.setCruiseShipId(resultSet.getInt("cruise_ship_id"));
        return staff;
    }
}
