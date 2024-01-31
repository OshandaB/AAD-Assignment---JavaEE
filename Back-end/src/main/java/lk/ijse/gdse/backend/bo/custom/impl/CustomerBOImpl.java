package lk.ijse.gdse.backend.bo.custom.impl;

import lk.ijse.gdse.backend.bo.custom.CustomerBO;
import lk.ijse.gdse.backend.dao.DAOFactory;
import lk.ijse.gdse.backend.dao.custom.CustomerDAO;
import lk.ijse.gdse.backend.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.CUSTOMERDAO);
    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws ClassNotFoundException, SQLException {
        ArrayList<Customer> customers = customerDAO.getAll(connection);
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            allCustomers.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getSalary()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomer(Connection connection, CustomerDTO dto) throws ClassNotFoundException, SQLException {
      return customerDAO.save(connection,new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO dto) throws ClassNotFoundException, SQLException {
        return customerDAO.update(connection,new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
    }

    @Override
    public boolean deleteCustomer(Connection connection, String id) throws ClassNotFoundException, SQLException {
    return  customerDAO.delete(connection,id);
    }

    @Override
    public CustomerDTO searchCustomer(Connection connection, String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(connection, id);
        return  new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getSalary());
    }
}
