package application;

import Entities.Departament;
import Entities.Seller;
import Repository.DaoFactory;
import Repository.SellerDao;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Departament obj = new Departament(1,"books");
        Seller seller = new Seller(1,"Pedro", "felix@gmail",new Date(), 3000D, obj);
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(obj);
        System.out.println(seller);

    }
}
