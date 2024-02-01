package lk.ijse.gdse.backend.bo.custom;

import lk.ijse.gdse.backend.bo.SuperBO;
import lk.ijse.gdse.backend.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {

    boolean placeOrder(Connection connection, OrderDTO dto) throws SQLException, ClassNotFoundException;

    String generateNextOrderId(Connection connection) throws SQLException, ClassNotFoundException;

}
