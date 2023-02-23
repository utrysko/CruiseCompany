package com.cruise.model.dao.mysqlDao;

import com.cruise.model.connection.DataSource;
import com.cruise.exceptions.DAOException;
import com.cruise.model.dao.UserDAO;
import com.cruise.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of UserDAO interface for MySQL
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class MysqlUserDAO implements UserDAO {


    private final DataSource dataSource;

    public MysqlUserDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private static final String SQL_FIND_BY_ID =
            "SELECT id, login, first_name, last_name, email, password, role_id, balance FROM user WHERE id = ?";
    private static final String SQL_FIND_BY_LOGIN =
            "SELECT id, login, first_name, last_name, email, password, role_id, balance FROM user WHERE login = ?";
    private static final String SQL_GET_ALL_USER_ORDER_BY_ID =
            "SELECT id, login, first_name, last_name, email, password, role_id, balance FROM user ORDER BY id";
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(id) AS total FROM cruises";
    private static final String SQL_INSERT =
            "INSERT INTO user (login, first_name, last_name, email, password, role_id, balance) VALUES (?, ? ,?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE user SET login = ?, first_name = ?, last_name = ?, email = ?, role_id = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM user WHERE login = ?";
    private static final String SQL_CHANGE_PASSWORD =
            "UPDATE user SET password = ? WHERE id = ?";
    private static final String SQL_CHANGE_BALANCE =
            "UPDATE user SET balance = ? WHERE id = ?";



    @Override
    public Optional<User> findById(int id) throws DAOException {
        Optional<User> user = Optional.empty();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)){
            int k = 0;
            pst.setInt(++k, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                user = Optional.of(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return user;
    }
    @Override
    public Optional<User> findByLogin(String login) throws DAOException {
        Optional<User> user = Optional.empty();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_LOGIN)){
            int k = 0;
            pst.setString(++k, login);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                user = Optional.of(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return user;
    }


    @Override
    public List<User> getAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_USER_ORDER_BY_ID)){
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                users.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
        return users;
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
    public void create(User user) throws DAOException {
        if (user.getId() != 0) {
            throw new DAOException("User is already created, the user ID is not zero.");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)){
            int k =0;
            pst.setString(++k, user.getLogin());
            pst.setString(++k, user.getFirstName());
            pst.setString(++k, user.getLastName());
            pst.setString(++k, user.getEmail());
            pst.setString(++k, user.getPassword());
            pst.setInt(++k, user.getRoleId());
            pst.setDouble(++k, user.getBalance());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pst.getGeneratedKeys()){
                if (!generatedKeys.next()){
                    throw new DAOException("Creating user failed, no generated key obtained.");
                }
                user.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void update(User user) throws DAOException {
        if (user.getId() == 0) {
            throw new DAOException("User is not created yet, the user ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)){
            int k = 0;
            pst.setString(++k, user.getLogin());
            pst.setString(++k, user.getFirstName());
            pst.setString(++k, user.getLastName());
            pst.setString(++k, user.getEmail());
            pst.setInt(++k, user.getRoleId());
            pst.setInt(++k, user.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void delete(User user) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)){
            int k = 0;
            pst.setString(++k, user.getLogin());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Deleting user failed, no rows affected.");
            }
            user.setId(0);
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void changePassword(User user, String password) throws DAOException {
        if (user.getId() == 0) {
            throw new DAOException("User is not created yet, the user ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CHANGE_PASSWORD)){
            int k = 0;
            pst.setString(++k, password);
            pst.setInt(++k, user.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Changing password failed, no rows affected.");
            }
            user.setPassword(password);
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void changeBalance(User user, double balance) throws DAOException {
        if (user.getId() == 0) {
            throw new DAOException("User is not created yet, the user ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CHANGE_BALANCE)){
            int k = 0;
            pst.setDouble(++k, balance);
            pst.setInt(++k, user.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Changing balance failed, no rows affected.");
            }
            user.setBalance(balance);
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    private User map(ResultSet resultSet) throws SQLException{
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRoleId(resultSet.getInt("role_id"));
        user.setBalance(resultSet.getDouble("balance"));
        return user;
    }
}
