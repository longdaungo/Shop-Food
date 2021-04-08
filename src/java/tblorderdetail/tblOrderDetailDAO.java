/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblorderdetail;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author IT
 */
public class tblOrderDetailDAO {

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

    public int countOrderDetail() throws NamingException, SQLException {
        int count = 0;
        try {    
            String sql = "select count(*) as count from tblOrderDetail";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            return count;
        } finally {
            closeConnection();
        }
    }

    public void insertOrderDetail(String detailID, String orderID, String productID, int quantity, int price) throws NamingException, SQLException {
        String sql = "insert into tblOrderDetail (DetailID, OrderID, ProductID, quantity, price) values(?,?,?,?,?)";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, detailID);
            pre.setString(2, orderID);
            pre.setString(3, productID);
            pre.setInt(4, quantity);
            pre.setInt(5, price);
            pre.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public List<tblOrderDeteilDTO> getListOrderDetail(String idorder) throws NamingException, SQLException {
        List<tblOrderDeteilDTO> list = null;
        String sql = "Select DetailID, ProductID, quantity, price from tblOrderDetail where OrderID = ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, idorder);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String detailID = rs.getString("DetailID");
                String productid = rs.getString("ProductID");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                tblOrderDeteilDTO dto = new tblOrderDeteilDTO(detailID, productid, quantity, price);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }
}
