package lk.ijse.gdse.backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.backend.bo.BOFactory;
import lk.ijse.gdse.backend.bo.custom.OrderDetailBO;
import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.dto.OrderDetailDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


@WebServlet(name = "OrderDetailServlet", value = "/orderDetail")
public class OrderDetailServlet extends HttpServlet {

    OrderDetailBO orderDetailBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERDETAILBO);
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/pos");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");
        resp.setContentType("application/json");
        Jsonb jsonb = JsonbBuilder.create();
        try (Connection connection = dataSource.getConnection()) {
            if (Objects.equals(option, "getAll")){
                ArrayList<OrderDetailDTO> allOrderDetails = orderDetailBO.getAllOrderDetails(connection);
                jsonb.toJson(allOrderDetails, resp.getWriter());
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
