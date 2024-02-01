package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.CrudDAO;
import lk.ijse.gdse.backend.dao.custom.OrderDetailDAO;
import lk.ijse.gdse.backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.backend.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean save(Connection connection, OrderDetail entity) throws SQLException {
        return SQLUtil.execute(connection, "INSERT INTO OrderDetail VALUES(?, ?, ?,?)",entity.getOId(), entity.getCode(), entity.getPrice(),entity.getQty());

    }

    @Override
    public boolean update(Connection connection, OrderDetail entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<OrderDetail> getAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public OrderDetail search(Connection connection, String s) throws SQLException {
        return null;
    }
}
