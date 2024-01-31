package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.custom.CustomerDAO;
import lk.ijse.gdse.backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.backend.entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean save(Connection connection, Customer entity) throws SQLException {
        String sql =  "INSERT INTO Customer VALUES(?, ?, ?, ?)";
       return SQLUtil.execute(connection,sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
    }

    @Override
    public boolean update(Connection connection, Customer entity) throws SQLException {

        String sql = "UPDATE Customer SET name=?, address=?, salary=? WHERE id=?";
        return SQLUtil.execute(connection,sql,entity.getName(),entity.getAddress(),entity.getSalary(),entity.getId());

    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException {
        String sql = "DELETE FROM Customer WHERE id=?";
        return SQLUtil.execute(connection,sql,id);
    }

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException {
        String sql =  "SELECT * FROM Customer";
        ResultSet resultSet = SQLUtil.execute(connection, sql);
        ArrayList<Customer> customers = new ArrayList<>();
        while (resultSet.next()){
            customers.add(new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
        }
        return customers;
    }

    @Override
    public Customer search(Connection connection, String id) throws SQLException {
        String sql =  "SELECT * FROM Customer WHERE id=?";
        ResultSet resultSet = SQLUtil.execute(connection, sql,id);

        if (resultSet.next()){
            return new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4) );
        }
        return null;
    }


}
