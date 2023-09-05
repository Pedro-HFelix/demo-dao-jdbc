package application;

import model.Departament;
import model.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Departament obj = new Departament(1,"books");
        Seller seller = new Seller(1,"Pedro", "felix@gmail",new Date(), 3000D, obj);
        System.out.println(obj);
        System.out.println(seller);
    }
}
