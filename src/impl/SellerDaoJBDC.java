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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try {
            st = conn.prepareStatement("select " +
                    "seller.*, department.Name as DepName " +
                    "from seller  " +
                    "Inner join department on (seller.departmentId = department.Id)" +
                    " where seller.Id = ?");
            st.setInt(1, id);
            resultSet = st.executeQuery();
            //pois começa em 0 e 0 não tem objeto
            if (resultSet.next()) {
                Departament dep = instantiateDepartament(resultSet);
                Seller sl = instantiateSeller(resultSet, dep);
                return sl;
            }
            return null;
        } catch (SQLException e) {
            throw new BDEXCEPTION(e.getMessage());
        } finally {
            DBConnector.closeResult(resultSet);
            DBConnector.closeStatement(st);
        }

    }

    private Seller instantiateSeller(ResultSet resultSet, Departament dep) throws SQLException {
        Seller sl = new Seller();
        sl.setId(resultSet.getInt("Id"));
        sl.setName(resultSet.getString("Name"));
        sl.setEmail(resultSet.getString("Email"));
        sl.setBithDate(resultSet.getDate("BirthDate"));
        sl.setBaseSalary(resultSet.getDouble("BaseSalary"));
        sl.setDepartament(dep);
        return sl;
    }

    private Departament instantiateDepartament(ResultSet resultSet) throws SQLException {
        Departament dep = new Departament();
        dep.setId(resultSet.getInt("DepartmentId"));
        dep.setName(resultSet.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }

    @Override
    public List<Seller> findByDepartment(Departament departament) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.prepareStatement("select seller.*, department.Name as DepName " +
                    "from Seller" +
                    " inner join department on seller.DepartmentId = department.Id " +
                    "where seller.DepartmentId = ? " +
                    "order by Name");
            statement.setInt(1, departament.getId());
            resultSet = statement.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Departament> map = new HashMap<>();
            while (resultSet.next()) {
                Departament dep = map.get(resultSet.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartament(   resultSet);
                    map.put(resultSet.getInt("DepartmentId"), dep);
                }
                Seller sel = instantiateSeller(resultSet, dep);
                list.add(sel);
            }
            return list;

        } catch (SQLException e) {
            throw new BDEXCEPTION(e.getMessage());
        } finally {
            DBConnector.closeResult(resultSet);
            DBConnector.closeStatement(statement);
        }
    }
}
