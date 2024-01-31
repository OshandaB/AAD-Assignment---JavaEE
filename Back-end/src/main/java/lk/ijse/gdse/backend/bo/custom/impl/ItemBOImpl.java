package lk.ijse.gdse.backend.bo.custom.impl;

import lk.ijse.gdse.backend.bo.custom.ItemBO;
import lk.ijse.gdse.backend.dao.DAOFactory;
import lk.ijse.gdse.backend.dao.custom.CustomerDAO;
import lk.ijse.gdse.backend.dao.custom.ItemDAO;
import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.entity.Customer;
import lk.ijse.gdse.backend.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ITEMDAO);
    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDAO.getAll(connection);
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item item : items) {
            allItems.add(new ItemDTO(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice()));
        }
        return allItems;
    }

    @Override
    public boolean saveItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(connection,new Item(dto.getCode(), dto.getDescription(), dto.getQtyOnHand(), dto.getUnitPrice()));
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(connection,new Item(dto.getCode(), dto.getDescription(), dto.getQtyOnHand(), dto.getUnitPrice()));
    }

    @Override
    public boolean deleteItem(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return  itemDAO.delete(connection,id);
    }

    @Override
    public ItemDTO searchItem(Connection connection, String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(connection, id);
        return  new ItemDTO(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice());
    }
}
