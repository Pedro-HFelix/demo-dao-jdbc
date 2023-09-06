package application;

import Entities.Departament;
import Repository.DaoFactory;
import Repository.DepartamentDao;
import impl.DepartmentDaoJBDC;

import java.util.List;

public class Program2 {
    public static void main(String[] args) {
        DepartamentDao departamentDao = DaoFactory.createDapartmetDao();
        System.out.println("=== Test 1: seller findAll ===");
        List<Departament> list = departamentDao.findAll();
        for (Departament l : list){
            System.out.println(l);
        }
        System.out.println("=== Test 2: seller findbyID ===");
        System.out.println(departamentDao.findById(4));

        System.out.println("=== Test 3: department delete ===");
        departamentDao.deleteById(7);
        System.out.println("Department deletede");
        System.out.println("=== Test 4: department update ===");
        Departament  dp = departamentDao.findById(6);
        dp.setName("Update");
        departamentDao.update(dp);
        System.out.println("Updated complet");
        System.out.println("=== Test 5: department insert ===");
        Departament departament = new Departament(null, "Insert");
        departamentDao.insert(departament);
        System.out.println("Insert new id: " +departament.getId());

    }
}
