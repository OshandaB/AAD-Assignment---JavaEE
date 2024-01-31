package lk.ijse.gdse.backend.bo.custom;

import lk.ijse.gdse.backend.bo.SuperBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

    ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException;

    boolean saveCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(Connection connection, String id) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(Connection connection, String id) throws  SQLException,ClassNotFoundException;
}
