package Repository;

import Entities.Departament;
import Entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller obj);
    void update (Seller obj);
    void deleteById(Seller id);
    Seller findById(Integer id);
    List<Seller> findAll ();
    List<Seller> findByDepartment(Departament departament);
}
