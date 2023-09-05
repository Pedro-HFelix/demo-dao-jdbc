package application;

import Entities.Departament;
import Entities.Seller;
import Repository.DaoFactory;
import Repository.SellerDao;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== Test 1: seller findById ===");

        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("=== Test 2: seller findByDepartment ===");
        Departament departament = new Departament(2, null);
        List<Seller> lis = sellerDao.findByDepartment(departament);
        for (Seller obj : lis){
            System.out.println(obj);
        }


    }
}
