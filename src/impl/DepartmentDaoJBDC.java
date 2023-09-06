package impl;

import Database.BDEXCEPTION;
import Database.DBConnector;
import Entities.Departament;
import Repository.DepartamentDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJBDC implements DepartamentDao {
    Connection conn;

    public DepartmentDaoJBDC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Departament obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into department values(default, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
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

        }catch (SQLException e){
            throw new BDEXCEPTION(e.getMessage());
        }finally {
            DBConnector.closeStatement(st);
        }
    }

    @Override
    public void update(Departament obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("Update department    " +
                    "set Name = ? "+
                    "where Id = ?");
            st.setString(1, obj.getName());
            st.setInt(2,obj.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new BDEXCEPTION(e.getMessage());
        } finally {
            DBConnector.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st= null;
        try{
            st = conn.prepareStatement("Delete from department where id = ?");
            st.setInt(1,id);
            st.executeUpdate();
        }catch (SQLException e){
            throw new BDEXCEPTION(e.getMessage());
        }finally {
            DBConnector.closeStatement(st);
        }
    }

    @Override
    public Departament findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select * from department where Id = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if (rs.next()) {
                return instantiateDepartament(rs);
            }
            return null ;
        } catch (SQLException e) {
            throw new BDEXCEPTION(e.getMessage());
        } finally {
            DBConnector.closeStatement(st);
            DBConnector.closeResult(rs);
        }
    }


    @Override
    public List<Departament> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select * from department");
            rs = st.executeQuery();
            List<Departament> list = new ArrayList<>();
            while (rs.next()) {
                Departament dep = instantiateDepartament(rs);
                list.add(dep);
            }
            return list;
        } catch (SQLException e) {
            throw new BDEXCEPTION(e.getMessage());
        } finally {
            DBConnector.closeStatement(st);
            DBConnector.closeResult(rs);
        }
    }

    private Departament instantiateDepartament(ResultSet resultSet) throws SQLException {
        Departament dep = new Departament();
        dep.setId(resultSet.getInt("Id"));
        dep.setName(resultSet.getString("Name"));
        return dep;
    }
}
