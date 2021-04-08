/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblupdateproduct;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author IT
 */
public class tblUpdateProductDAO {

    Connection con;
    PreparedStatement pre;
    ResultSet rs;

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pre != null) {
            pre.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public int countRows() throws NamingException, SQLException {
        try {
            int rows = 0;
            String sql = "select count(*) as count from tblUpdateProduct";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                rows = rs.getInt("count");
            }
            return rows;
        } finally {
            closeConnection();
        }
    }

    public void insertUpdatedProduct(String idUpdateProduct, String idProduct, String userid, String date, String crud) throws NamingException, SQLException {
        try {
            String sql = "insert into tblUpdateProduct(IDUpdateProduct, IDProduct, UserID, Date, Crud) values(?,?,?,?,?)";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, idUpdateProduct);
            pre.setString(2, idProduct);
            pre.setString(3, userid);
            pre.setString(4, date);
            pre.setString(5, crud);
            pre.executeUpdate();
        } finally {
            closeConnection();
        }
    }
}
