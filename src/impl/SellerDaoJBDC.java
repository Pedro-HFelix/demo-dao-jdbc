package impl;

import Database.BDEXCEPTION;
import Database.DBConnector;
import Entities.Departament;
import Entities.Seller;
import Repository.SellerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJBDC implements SellerDao {

    private Connection conn;

    public SellerDaoJBDC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        
    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Seller id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try{
            st = conn.prepareStatement("select " +
                    "seller.*, department.Name as DepName " +
                    "from seller  " +
                    "Inner join department on (seller.departmentId = department.Id)" +
                    " where seller.Id = ?");
            st.setInt(1,id);
            resultSet = st.executeQuery();
            //pois começa em 0 e 0 não tem objeto
            if(resultSet.next()){
                Departament dep = new Departament();
                dep.setId(resultSet.getInt("DepartmentId"));
                dep.setName(resultSet.getString("DepName"));
                Seller sl = new Seller();
                sl.setId(resultSet.getInt("Id"));
                sl.setName(resultSet.getString("Name"));
                sl.setEmail(resultSet.getString("Email"));
                sl.setBithDate(resultSet.getDate("BirthDate"));
                sl.setBaseSalary(resultSet.getDouble("BaseSalary"));
                sl.setDepartament(dep);
                return sl;
            }
            return null;
        } catch (SQLException e) {
            throw new BDEXCEPTION(e.getMessage());
        }finally {
            DBConnector.closeResult(resultSet);
            DBConnector.closeStatement(st);
        }

    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
