package impl;

import Database.BDEXCEPTION;
import Database.DBConnector;
import Entities.Departament;
import Entities.Seller;
import Repository.SellerDao;

import java.sql.*;
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
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("Insert into seller " +
                    "(Name,Email,BirthDate,BaseSalary,DepartmentId) " +
                    "values(?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBithDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartament().getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DBConnector.closeResult(rs);
            } else {
                throw new BDEXCEPTION("No rows affected");
            }
        } catch (SQLException e) {
            throw new BDEXCEPTION(e.getMessage());
        } finally {
            DBConnector.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("Update seller " +
                    "set Name = ?, Email = ?, BirthDate =  ?, BaseSalary = ?,DepartmentId = ? " +
                    "where Id = ?");
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBithDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartament().getId());
            st.setInt(6, obj.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new BDEXCEPTION(e.getMessage());
        } finally {
            DBConnector.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("Delete from Seller where id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new BDEXCEPTION(e.getMessage());
        } finally {
            DBConnector.closeStatement(st);
        }

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
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.prepareStatement("Select " +
                    "seller.*,department.Name as Depname" +
                    " from seller " +
                    "inner join department on seller.DepartmentId = department.Id" +
                    " order by Name");
            resultSet = statement.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Departament> map = new HashMap<>();
            while (resultSet.next()) {
                Departament dep = map.get(resultSet.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartament(resultSet);
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
                // caso não exista essa chave retorna nulo
                Departament dep = map.get(resultSet.getInt("DepartmentId"));

                if (dep == null) {
                    // caso seja nulo coloca o valor
                    dep = instantiateDepartament(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), dep);
                }
                // aqui recebe o dep com o valor do departamento
                Seller sel = instantiateSeller(resultSet, dep);
                list.add(sel);
                // o intuito desse uso é ter certeza que não vai criar mais um objeto de departament
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
