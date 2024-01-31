package lk.ijse.gdse.backend.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T,ID> extends SuperDAO {
      boolean save(Connection connection, T entity) throws SQLException;
      boolean update(Connection connection, T entity) throws SQLException;
      boolean delete(Connection connection,ID id) throws SQLException;
      ArrayList<T> getAll(Connection connection) throws SQLException;

      T search(Connection connection,ID id) throws  SQLException;

}
