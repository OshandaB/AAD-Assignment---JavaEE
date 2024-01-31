package lk.ijse.gdse.backend.dao;

import lk.ijse.gdse.backend.bo.BOFactory;
import lk.ijse.gdse.backend.dao.custom.impl.CustomerDAOImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory==null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        CUSTOMERDAO
    }

    public <T extends SuperDAO> T getDao(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMERDAO:
                return (T) new CustomerDAOImpl();
        }
        return null;
    }

}