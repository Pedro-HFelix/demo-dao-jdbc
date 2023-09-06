package Repository;

import Database.DBConnector;
import impl.DepartmentDaoJBDC;
import impl.SellerDaoJBDC;

public class DaoFactory {
    public static SellerDao createSellerDao(){
        return new SellerDaoJBDC(DBConnector.getConnection());
    }
    public static DepartamentDao createDapartmetDao(){return new DepartmentDaoJBDC(DBConnector.getConnection());}
    }

