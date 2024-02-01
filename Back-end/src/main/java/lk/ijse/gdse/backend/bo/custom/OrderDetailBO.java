package lk.ijse.gdse.backend.bo.custom;

import lk.ijse.gdse.backend.bo.SuperBO;
import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO extends SuperBO {
    ArrayList<OrderDetailDTO> getAllOrderDetails(Connection connection) throws SQLException, ClassNotFoundException;

}
