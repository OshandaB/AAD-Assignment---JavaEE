package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.CrudDAO;
import lk.ijse.gdse.backend.dao.custom.OrderDetailDAO;
import lk.ijse.gdse.backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.backend.entity.Item;
import lk.ijse.gdse.backend.entity.OrderDetail;

import java.sql.Connection;
import java.sql.ResultSet;
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
        String sql =  "SELECT * FROM OrderDetail";
        ResultSet resultSet = SQLUtil.execute(connection, sql);
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        while (resultSet.next()){
            orderDetails.add(new OrderDetail(resultSet.getString(1),resultSet.getString(2),resultSet.getBigDecimal(3),resultSet.getInt(4)));
        }
        return orderDetails;
    }

    @Override
    public OrderDetail search(Connection connection, String s) throws SQLException {
        return null;
    }
}
