package Repository;

import Database.DBConnector;
import impl.SellerDaoJBDC;

public class DaoFactory {
    public static SellerDao createSellerDao(){
        return new SellerDaoJBDC(DBConnector.getConnection());
    }
}
