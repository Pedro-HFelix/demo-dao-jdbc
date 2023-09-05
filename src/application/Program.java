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
        System.out.println("=== Test 3: seller findByAll ===");
        List<Seller> list = sellerDao.findAll();
        for (Seller obj : list){
            System.out.println(obj);
        }
        System.out.println("=== Test 4: seller insert ===");
        Seller newseller = new Seller(null,"Greg","greg@gmail",new Date(), 3000D,departament);
        sellerDao.insert(newseller);
        System.out.println("Insert new id: " +newseller.getId());
        System.out.println("=== Test 5: seller update ===");
        seller  = sellerDao.findById(2);
        seller.setName("Matha Waine");
        sellerDao.update(seller);
        System.out.println("Updated complet");


    }
}
