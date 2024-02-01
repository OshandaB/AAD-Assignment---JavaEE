package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.custom.OrderDAO;
import lk.ijse.gdse.backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.backend.entity.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(Connection connection, Order entity) throws SQLException {
        return SQLUtil.execute(connection, "INSERT INTO Orders VALUES(?, ?, ?)", entity.getOId(), entity.getDate(), entity.getCustomerId());

    }

    @Override
    public boolean update(Connection connection, Order entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Order> getAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public Order search(Connection connection, String s) throws SQLException {
        return null;
    }

    @Override
    public String generateNextId(Connection connection) throws SQLException {
        String sql = "SELECT oId FROM Orders ORDER BY oId DESC LIMIT 1";
        ResultSet resultSet = SQLUtil.execute(connection,sql);


        if (resultSet.next()) {
            String id = resultSet.getString(1);
            return id;
        }
        return null;
    }
}
