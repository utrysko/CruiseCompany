package com.cruise.dao.mysqlDao;

import com.cruise.connection.DataSource;
import com.cruise.exceptions.DAOException;
import com.cruise.dao.RouteDAO;
import com.cruise.model.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MysqlRouteDAO implements RouteDAO {

    private final DataSource dataSource;

    public MysqlRouteDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }
    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM route WHERE id = ?";
    private static final String SQL_GET_ALL_ROUTE_ORDER_BY_ID =
            "SELECT * FROM route ORDER BY id";
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(id) AS total FROM cruises";
    private static final String SQL_ROUTES_IN_ORDER_AND_LIMIT =
            "SELECT * FROM route ORDER BY ? LIMIT ? OFFSET ?";
    private static final String SQL_INSERT =
            "INSERT INTO route (number_of_ports, start_port, middle_ports, end_port) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE route SET  number_of_ports = ?, start_port = ?,  middle_ports = ?, end_port = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM route WHERE id = ?";

    @Override
    public Route findById(int id) throws DAOException {
        Route route = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)){
            int k = 0;
            pst.setInt(++k, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                route = map(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return route;
    }

    @Override
    public List<Route> getAllRoutes() throws DAOException {
        List<Route> routes = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_ROUTE_ORDER_BY_ID)){
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                routes.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
        return routes;
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
    public List<Route> getRoutesInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException {
        List<Route> routes = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_ROUTES_IN_ORDER_AND_LIMIT)){
            int k = 0;
            pst.setInt(++k, orderBy);
            pst.setInt(++k, limit);
            pst.setInt(++k, offset);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                routes.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
        return routes;
    }

    @Override
    public void create(Route route) throws DAOException {
        if (route.getId() != 0) {
            throw new DAOException("Route is already created, the route ID is not zero.");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)){
            int k = 0;
            StringBuilder middlePorts = new StringBuilder();
            for(String str : route.getMiddlePorts()){
                middlePorts.append(str);
                middlePorts.append(";");
            }
            pst.setInt(++k, route.getNumberOfPorts());
            pst.setString(++k, route.getStartPort());
            pst.setString(++k, String.valueOf(middlePorts));
            pst.setString(++k, route.getEndPort());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Creating route failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pst.getGeneratedKeys()){
                if (!generatedKeys.next()){
                    throw new DAOException("Creating route failed, no generated key obtained.");
                }
                route.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void update(Route route) throws DAOException {
        if (route.getId() == 0) {
            throw new DAOException("Route is not created yet, the user ID is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)){
            int k = 0;
            StringBuilder middlePorts = new StringBuilder();
            for(String str : route.getMiddlePorts()){
                middlePorts.append(str);
                middlePorts.append(";");
            }
            pst.setInt(++k, route.getNumberOfPorts());
            pst.setString(++k, route.getStartPort());
            pst.setString(++k, String.valueOf(middlePorts));
            pst.setString(++k, route.getEndPort());
            pst.setInt(++k, route.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Updating route failed, no rows affected.");
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void delete(Route route) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)){
            int k = 0;
            pst.setInt(++k, route.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0){
                throw new DAOException("Deleting route failed, no rows affected.");
            }
            route.setId(0);
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    private Route map(ResultSet resultSet) throws SQLException{
        Route route = new Route();
        route.setId(resultSet.getInt("id"));
        route.setNumberOfPorts(resultSet.getInt("number_of_ports"));
        route.setStartPort(resultSet.getString("start_port"));
        String[] mp = resultSet.getString("middle_ports").split(";");
        List<String> middlePorts = new ArrayList<>();
        middlePorts.addAll(Arrays.asList(mp));
        route.setMiddlePorts(middlePorts);
        route.setEndPort(resultSet.getString("end_port"));
        return route;
    }
}
