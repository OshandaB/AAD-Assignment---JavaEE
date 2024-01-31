package lk.ijse.gdse.backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.backend.api.util.ValidationController;
import lk.ijse.gdse.backend.bo.BOFactory;
import lk.ijse.gdse.backend.bo.custom.CustomerBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet(name = "CustomerServlet", value = "/customer")
public class CustomerServlet extends HttpServlet {

    CustomerBO customerBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMERBO);
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
                ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers(connection);
                jsonb.toJson(allCustomers, resp.getWriter());
            } else if (Objects.equals(option, "search")){
                CustomerDTO customerDTO = customerBO.searchCustomer(connection, id);
                System.out.println(customerDTO);
                jsonb.toJson(customerDTO, resp.getWriter());
            }


        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("invoke post");
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        if (!ValidationController.isValidCustomerIdFormat(customer.getId())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer ID format. Please use the pattern 'C00-001'."));
            return;
        }
        if (!ValidationController.isValidCustomerNameFormat(customer.getName())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer Name format.Minimum 5 Characters"));
            return;
        }
        if (!ValidationController.isValidCustomerAddressFormat(customer.getAddress())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer Address format. Please use Minimum 8 Characters"));
            return;
        }
        if (!ValidationController.isValidCustomerSalaryFormat(String.valueOf(customer.getSalary()))) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer Salary format. Please use the pattern '3000.00'."));
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            boolean saveCustomer = customerBO.saveCustomer(connection, customer);
            if (saveCustomer) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Customer Added Successful!!!"));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Customer Added Failed!!!"));
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT); // HTTP 409 Conflict
            resp.getWriter().write(jsonb.toJson("Duplicate ID. Customer not added."));
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
        CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        if (!ValidationController.isValidCustomerIdFormat(customer.getId())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer ID format. Please use the pattern 'C00-001'."));
            return;
        }
        if (!ValidationController.isValidCustomerNameFormat(customer.getName())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer Name format.Minimum 5 Characters"));
            return;
        }
        if (!ValidationController.isValidCustomerAddressFormat(customer.getAddress())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer Address format. Please use Minimum 8 Characters"));
            return;
        }
        if (!ValidationController.isValidCustomerSalaryFormat(String.valueOf(customer.getSalary()))) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400 Bad Request
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer Salary format. Please use the pattern '3000.00'."));
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            boolean updateCustomer = customerBO.updateCustomer(connection, customer);
            if (updateCustomer) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Customer Update Successful!!!"));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Customer Update Failed!!!"));
            }

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(jsonb.toJson("Database Error"));;
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
           boolean deleteCustomer = customerBO.deleteCustomer(connection,id);
           if (deleteCustomer) {
               resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
               resp.setContentType("application/json");
               resp.getWriter().write("Customer Delete Successful!!!");
           } else {
               resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
               resp.setContentType("application/json");
               resp.getWriter().write("Customer Delete Failed!!!");
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