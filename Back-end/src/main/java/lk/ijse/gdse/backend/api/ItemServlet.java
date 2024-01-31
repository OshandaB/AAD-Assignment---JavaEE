package lk.ijse.gdse.backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.backend.api.util.ValidationController;
import lk.ijse.gdse.backend.bo.BOFactory;
import lk.ijse.gdse.backend.bo.custom.CustomerBO;
import lk.ijse.gdse.backend.bo.custom.ItemBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.dto.ItemDTO;

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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Objects;


@WebServlet(name = "ItemServlet", value = "/item")
public class ItemServlet extends HttpServlet {

    ItemBO itemBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEMBO);
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
        System.out.println("invoke get");
        String option = req.getParameter("option");
        System.out.println(option);
        String id = req.getParameter("id");
        resp.setContentType("application/json");
        Jsonb jsonb = JsonbBuilder.create();
        try (Connection connection = dataSource.getConnection()) {
            if (Objects.equals(option, "getAll")){
                ArrayList<ItemDTO> allItems = itemBO.getAllItems(connection);
                jsonb.toJson(allItems, resp.getWriter());
            } else if (Objects.equals(option, "search")){
                ItemDTO itemDTO = itemBO.searchItem(connection, id);
                System.out.println(itemDTO);
                jsonb.toJson(itemDTO, resp.getWriter());
            }


        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("invoke post");
        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
        if (!ValidationController.isValidItemIdFormat(itemDTO.getCode())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid Item ID format. Please use the pattern 'I00-001'."));
            return;
        }
        if (!ValidationController.isValidItemNameFormat(itemDTO.getDescription())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid Item Name format.Minimum 3 Characters"));
            return;
        }
        if (!ValidationController.isValidQtyFormat(String.valueOf(itemDTO.getQtyOnHand()))) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid Item qty format. Please use Minimum 1 Number"));
            return;
        }
        if (!ValidationController.isValidUnitPriceFormat(String.valueOf(itemDTO.getUnitPrice()))) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid price format. Please use the pattern '3000' or '3000.00'."));
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            boolean saveItem = itemBO.saveItem(connection, itemDTO);
            if (saveItem) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Item Added Successful!!!"));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Item Added Failed!!!"));
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT); // HTTP 409 Conflict
            resp.getWriter().write(jsonb.toJson("Duplicate ID. Item not added."));
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(jsonb.toJson("Database Error"));

        } catch (ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(jsonb.toJson("Internal Server Error"));
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
        if (!ValidationController.isValidItemIdFormat(itemDTO.getCode())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid Item ID format. Please use the pattern 'I00-001'."));
            return;
        }
        if (!ValidationController.isValidItemNameFormat(itemDTO.getDescription())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid Item Name format.Minimum 3 Characters"));
            return;
        }
        if (!ValidationController.isValidQtyFormat(String.valueOf(itemDTO.getQtyOnHand()))) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid Item qty format. Please use Minimum 1 Number"));
            return;
        }
        if (!ValidationController.isValidUnitPriceFormat(String.valueOf(itemDTO.getUnitPrice()))) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid price format. Please use the pattern '3000' or '3000.00'."));
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            boolean updateItem = itemBO.updateItem(connection, itemDTO);
            if (updateItem) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Item Added Successful!!!"));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Item Added Failed!!!"));
            }

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println(e);
            resp.getWriter().write(jsonb.toJson("Database Error"));

        } catch (ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(jsonb.toJson("Internal Server Error"));
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        try(Connection connection = dataSource.getConnection()) {
            boolean deleteCustomer = itemBO.deleteItem(connection,id);
            if (deleteCustomer) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                resp.setContentType("application/json");
                resp.getWriter().write("Item Delete Successful!!!");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write("Item Delete Failed!!!");
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database Error");;
        } catch (ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal Server Error");
        }

    }
}
