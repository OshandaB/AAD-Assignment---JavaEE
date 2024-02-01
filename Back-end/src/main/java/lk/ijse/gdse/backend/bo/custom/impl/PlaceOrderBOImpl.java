package lk.ijse.gdse.backend.bo.custom.impl;

import lk.ijse.gdse.backend.bo.custom.PlaceOrderBO;
import lk.ijse.gdse.backend.dao.DAOFactory;
import lk.ijse.gdse.backend.dao.custom.ItemDAO;
import lk.ijse.gdse.backend.dao.custom.OrderDAO;
import lk.ijse.gdse.backend.dao.custom.OrderDetailDAO;
import lk.ijse.gdse.backend.dto.OrderDTO;
import lk.ijse.gdse.backend.dto.OrderDetailDTO;
import lk.ijse.gdse.backend.entity.Item;
import lk.ijse.gdse.backend.entity.Order;
import lk.ijse.gdse.backend.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    OrderDAO orderDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ORDERDAO);

    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ITEMDAO);

    OrderDetailDAO orderDetailDAO = DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ORDERDETAILDAO);
    @Override
    public boolean placeOrder(Connection connection, OrderDTO dto) throws SQLException, ClassNotFoundException {

        connection.setAutoCommit(false);
        Order order = new Order(dto.getOrderId(),dto.getDate(),dto.getCustomerId());

        boolean orderSaved = orderDAO.save(connection, order);
        if (!orderSaved){
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO orderDetail : dto.getOrderDetails()) {
            boolean orderDetailSaved = orderDetailDAO.save(connection, new OrderDetail(dto.getOrderId(), orderDetail.getCode(), orderDetail.getPrice(), orderDetail.getQty()));
            if (!orderDetailSaved) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            Item item = itemDAO.search(connection, orderDetail.getCode());

            item.setQtyOnHand(item.getQtyOnHand()-orderDetail.getQty());

            boolean isUpdated = itemDAO.update(connection, item);

            if (!isUpdated) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }



        }

        connection.commit();
        connection.setAutoCommit(true);

        return true;

    }

    @Override
    public String generateNextOrderId(Connection connection) throws SQLException, ClassNotFoundException {
      return  orderDAO.generateNextId(connection);
    }
}
