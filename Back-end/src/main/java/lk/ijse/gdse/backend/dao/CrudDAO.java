package lk.ijse.gdse.backend.dao;

import java.sql.SQLException;

public interface CrudDAO<T,ID> {
      boolean save(T entity) throws SQLException;
      boolean update(T entity) throws SQLException;
      boolean delete(ID id) throws SQLException;


}
