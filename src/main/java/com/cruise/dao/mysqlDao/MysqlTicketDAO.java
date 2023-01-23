package com.cruise.dao.mysqlDao;

import com.cruise.connection.DataSource;
import com.cruise.dao.DAOFactory;
import com.cruise.exceptions.DAOException;
import com.cruise.dao.TicketDAO;
import com.cruise.model.Cruise;
import com.cruise.model.Ticket;
import com.cruise.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlTicketDAO implements TicketDAO {


    private final DataSource dataSource;

    public MysqlTicketDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_FIND_BY_ID =
            "SELECT id, user_id, cruises_id, status, document FROM ticket WHERE id = ?";
    private static final String SQL_FIND_ALL_BY_USER =
            "SELECT id, user_id, cruises_id, status, document FROM ticket WHERE user_id = ?";
    private static final String SQL_FIND_BY_USER_AND_CRUISE =
            "SELECT id, user_id, cruises_id, status, document FROM ticket WHERE user_id = ? AND cruises_id = ?";
    private static final String SQL_FIND_ALL_BY_CRUISE =
            "SELECT id, user_id, cruises_id, status, document FROM ticket WHERE cruises_id = ?";
    private static final String SQL_TICKETS_IN_ORDER_AND_LIMIT =
            "SELECT * FROM ticket ORDER BY ? LIMIT ? OFFSET ?";
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(id) AS total FROM cruises";
    private static final String SQL_GET_ALL_TICKET =
            "SELECT id, user_id, cruises_id, status, document FROM ticket ORDER BY id";
    private static final String SQL_INSERT =
            "INSERT INTO ticket (user_id, cruises_id, status, document) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE ticket SET user_id = ?, cruises_id = ?, status = ?, document = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM ticket WHERE id = ?";
    private static final String SQL_CHANGE_STATUS =
            "UPDATE ticket SET status = ? WHERE id = ?";
    private static final String SQL_CHANGE_FREE_SPACES =
            "UPDATE cruises SET free_spaces = ? WHERE id = ?";
    private static final String SQL_CHANGE_BALANCE =
            "UPDATE user SET balance = ? WHERE id = ?";


    @Override
    public List<Ticket> findAllByUser(User user) throws DAOException {
        List<Ticket> ticketList = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_ALL_BY_USER)) {
            int k = 0;
            pst.setInt(++k, user.getId());
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                ticketList.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return ticketList;
    }

    @Override
    public List<Ticket> findByAllByCruise(Cruise cruise) throws DAOException {
        List<Ticket> ticketList = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_ALL_BY_CRUISE)) {
            int k = 0;
            pst.setInt(++k, cruise.getId());
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                ticketList.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return ticketList;
    }

    @Override
    public Ticket findById(int id) throws DAOException {
        Ticket ticket = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {
            int k = 0;
            pst.setInt(++k, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                ticket = map(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return ticket;
    }

    @Override
    public Ticket findByUserAndCruiseShip(User user, Cruise cruise) throws DAOException {
        Ticket ticket = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_USER_AND_CRUISE)) {
            int k = 0;
            pst.setInt(++k, user.getId());
            pst.setInt(++k, cruise.getId());
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                ticket = map(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return ticket;
    }


    @Override
    public List<Ticket> getAllTickets() throws DAOException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_TICKET)) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                tickets.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return tickets;
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
    public List<Ticket> getTicketsInOrderAndLimit(int orderBy, int limit, int offset) throws DAOException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_TICKETS_IN_ORDER_AND_LIMIT)) {
            int k = 0;
            pst.setInt(++k, orderBy);
            pst.setInt(++k, limit);
            pst.setInt(++k, offset);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                tickets.add(map(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return tickets;
    }

    @Override
    public void create(Ticket ticket, User user, Cruise cruise) throws DAOException {
        if (ticket.getId() != 0) {
            throw new DAOException("Ticket is already created, the ticket id is not zero");
        }
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            changeFreeSpace(cruise, con);
            changeBalance(user, cruise, con);
            pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            int k = 0;
            pst.setInt(++k, ticket.getClientId());
            pst.setInt(++k, ticket.getCruiseId());
            pst.setString(++k, ticket.getStatus());
            pst.setBlob(++k, ticket.getDocument());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating ticket failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new DAOException("Creating ticket failed, no generated key obtained.");
                }
                ticket.setId(generatedKeys.getInt(1));
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) rollback(con);
            throw new DAOException(e.getMessage());
        } finally {
            if (pst != null) closePst(pst);
            if (con != null) closeCon(con);
        }
    }


    @Override
    public void update(Ticket ticket) throws DAOException {
        if (ticket.getId() == 0) {
            throw new DAOException("Ticket is not created yet, the ticket id is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
            int k = 0;
            pst.setInt(++k, ticket.getClientId());
            pst.setInt(++k, ticket.getCruiseId());
            pst.setString(++k, ticket.getStatus());
            pst.setInt(++k, ticket.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating ticket failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void delete(Ticket ticket) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
            int k = 0;
            pst.setInt(++k, ticket.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting ticket failed, no rows affected.");
            }
            ticket.setId(0);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void changeStatus(Ticket ticket, String status) throws DAOException {
        if (ticket.getId() == 0) {
            throw new DAOException("Ticket is not created yet, the ticket id is zero");
        }
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CHANGE_STATUS)) {
            int k = 0;
            pst.setString(++k, status);
            pst.setInt(++k, ticket.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Changing status ticket failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    private Ticket map(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getInt("id"));
        ticket.setClientId(resultSet.getInt("user_id"));
        ticket.setCruiseId(resultSet.getInt("cruises_id"));
        ticket.setCruise(DAOFactory.getInstance().getCruiseDAO().findById(ticket.getCruiseId()));
        ticket.setStatus(resultSet.getString("status"));
        ticket.setDocument(resultSet.getBlob("document"));
        return ticket;
    }

    private void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    private void closeCon(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    private void closePst(PreparedStatement pst) {
        try {
            pst.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    private void changeFreeSpace(Cruise cruise, Connection con) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(SQL_CHANGE_FREE_SPACES)) {
            int k = 0;
            pst.setInt(++k, cruise.getFreeSpaces() - 1);
            pst.setInt(++k, cruise.getCruiseShip().getId());
            int affectedRowsFreeSpaces = pst.executeUpdate();
            if (affectedRowsFreeSpaces == 0) {
                throw new DAOException("Creating ticket failed, free spaces don't change.");
            }
        }
    }

    private void changeBalance(User user, Cruise cruise, Connection con) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(SQL_CHANGE_BALANCE)) {
            int k = 0;
            pst.setDouble(++k, user.getBalance() - cruise.getTicketPrice());
            pst.setInt(++k, user.getId());
            int affectedRowsBalance = pst.executeUpdate();
            if (affectedRowsBalance == 0) {
                throw new DAOException("Creating ticket failed, balance don't change.");
            }
        }
    }
}
