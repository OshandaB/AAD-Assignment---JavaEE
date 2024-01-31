package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.custom.ItemDAO;
import lk.ijse.gdse.backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.backend.entity.Customer;
import lk.ijse.gdse.backend.entity.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean save(Connection connection, Item entity) throws SQLException {
        String sql =  "INSERT INTO Item VALUES(?, ?, ?, ?)";
        return SQLUtil.execute(connection,sql,entity.getCode(),entity.getDescription(),entity.getQtyOnHand(),entity.getUnitPrice());
    }

    @Override
    public boolean update(Connection connection, Item entity) throws SQLException {
        String sql = "UPDATE Item SET description=?, qtyOnHand=?, unitPrice=? WHERE code=?";
        return SQLUtil.execute(connection,sql,entity.getDescription(),entity.getQtyOnHand(),entity.getUnitPrice(),entity.getCode());
    }

    @Override
    public boolean delete(Connection connection, String code) throws SQLException {
        String sql = "DELETE FROM Item WHERE code=?";
        return SQLUtil.execute(connection,sql,code);
    }

    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException {
        String sql =  "SELECT * FROM Item";
        ResultSet resultSet = SQLUtil.execute(connection, sql);
        ArrayList<Item> items = new ArrayList<>();
        while (resultSet.next()){
            items.add(new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getBigDecimal(4)));
        }
        return items;
    }

    @Override
    public Item search(Connection connection, String code) throws SQLException {
        String sql =  "SELECT * FROM Item WHERE code=?";
        ResultSet resultSet = SQLUtil.execute(connection, sql,code);

        if (resultSet.next()){
            return new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getBigDecimal(4));
        }
        return null;
    }
}
