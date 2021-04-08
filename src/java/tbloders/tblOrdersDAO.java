package tbloders;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author IT
 */
public class tblOrdersDAO {
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
    
    public int coutRowOders() throws NamingException, SQLException{
        int row = 0;
        try {
            String sql = "select count(*) as count from tblOrders";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            if(rs.next()){
                row = rs.getInt("count");
            }
        } finally {
            closeConnection();
        }
        return row;
    }
    
    public void insertOrder(String idOrder, String idUser, int total, String date,boolean paypal) throws NamingException, SQLException{
        String sql = "insert into tblOrders (OrderID, UserID, total, date, Paypal) values(?,?,?,?,?)";
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, idOrder);
            pre.setString(2,idUser);
            pre.setInt(3, total);
            pre.setString(4, date);
            pre.setBoolean(5, paypal);
            pre.executeUpdate();
        }finally{
            closeConnection();
        }
    }
    
    public List<tblordersDTO> getListOrder(String iduser) throws NamingException, SQLException{
        List<tblordersDTO> list = null;
        String sql = "Select OrderID, total, date from tblOrders where UserID = ?";
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, iduser);
            pre.executeQuery();
            list = new ArrayList<>();
            rs = pre.executeQuery();
            while(rs.next()){
                String orderID = rs.getString("OrderID");
                int total = rs.getInt("total");
                String date = rs.getString("date");
                tblordersDTO dto = new tblordersDTO(orderID, total, date);
                list.add(dto);
            }
        }finally{
            closeConnection();
        }
        return list;
    }
    
}
