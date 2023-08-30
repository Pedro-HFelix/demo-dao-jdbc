import Database.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testData {
    public static void main(String[] args) {
        Connection conn = null; // conexão com o banco
        Statement st = null; // consulta sql
        ResultSet rs = null; // resultado da consulta
        try {
            conn = DBConnector.getConnection();
            st = conn.createStatement();

            rs = st.executeQuery("select * from department");
            while (rs.next()) {
                System.out.println(rs.getInt("Id") + ": " + rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnector.closeStatement(st);
            DBConnector.closeResult(rs);
            DBConnector.closeConnection();
        }

    }
}
