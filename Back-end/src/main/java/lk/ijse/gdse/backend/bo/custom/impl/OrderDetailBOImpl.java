package lk.ijse.gdse.backend.bo.custom.impl;

import lk.ijse.gdse.backend.bo.custom.OrderDetailBO;
import lk.ijse.gdse.backend.dao.DAOFactory;
import lk.ijse.gdse.backend.dao.custom.OrderDetailDAO;
import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.dto.OrderDetailDTO;
import lk.ijse.gdse.backend.entity.Item;
import lk.ijse.gdse.backend.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailBO {
    OrderDetailDAO orderDetailDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ORDERDETAILDAO);
    @Override
    public ArrayList<OrderDetailDTO> getAllOrderDetails(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> orderDetails = orderDetailDAO.getAll(connection);
        ArrayList<OrderDetailDTO> allItems = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            allItems.add(new OrderDetailDTO(orderDetail.getOId(), orderDetail.getCode(), orderDetail.getPrice(), orderDetail.getQty()));
        }
        return allItems;
    }
}
