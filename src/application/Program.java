package application;

import Entities.Departament;
import Entities.Seller;
import Repository.DaoFactory;
import Repository.SellerDao;

import java.util.Date;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);


    }
}
