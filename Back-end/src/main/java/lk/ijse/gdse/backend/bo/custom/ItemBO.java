package lk.ijse.gdse.backend.bo.custom;

import lk.ijse.gdse.backend.bo.SuperBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {

    ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException;

    boolean saveItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteItem(Connection connection, String id) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(Connection connection, String id) throws  SQLException,ClassNotFoundException;
}
