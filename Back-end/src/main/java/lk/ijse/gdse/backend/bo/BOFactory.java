package lk.ijse.gdse.backend.bo;

import lk.ijse.gdse.backend.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.backend.bo.custom.impl.ItemBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}

    public static BOFactory getBoFactory(){
        return (boFactory==null) ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOTypes{
        CUSTOMERBO,ITEMBO
    }
    public <T extends SuperBO>T getBO(BOTypes boTypes){

        switch (boTypes){
            case CUSTOMERBO:
                return (T) new CustomerBOImpl();
            case ITEMBO:
                return (T) new ItemBOImpl();
        }
        return null;
    }
}
