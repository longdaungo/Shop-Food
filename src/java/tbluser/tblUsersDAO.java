/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tbluser;

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
public class tblUsersDAO {

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

    public String checkLogin(String username, String password) throws NamingException, SQLException {
        String name = null;
        String sql = "select Name from tblUsers where UserID =? and Password =? and Status =1";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            rs = pre.executeQuery();
            if (rs.next()) {
                name = rs.getString("Name");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeConnection();
            return name;
        }
    }

    public boolean checkAdmin(String username, String password) throws SQLException, NamingException {
        boolean admin = false;
        String sql = "select Role from tblUsers where UserID =? and Password =? and Status =1";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            rs = pre.executeQuery();
            if (rs.next()) {
                admin = rs.getBoolean("Role");
            }
        } finally {
            closeConnection();
            return admin;
        }
    }

    public void insertAdressPhone(String id, String address, String phone) throws NamingException, SQLException {
        try {
            String sql = "update tblUsers set Address = ?, Phone = ? where UserID = ?";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, address);
            pre.setString(2, phone);
            pre.setString(3, id);
            pre.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public String getAddress(String userid) throws NamingException, SQLException {
        String address = null;
        try {
            String sql = "select Address from tblUsers where UserID = ?";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, userid);
            rs = pre.executeQuery();
            if (rs.next()) {
                address = rs.getString("Address");
            }
        } finally {
            closeConnection();
        }
        return address;
    }
    
    public void insertUserid(String UserID) throws SQLException, NamingException{
        try{
            String sql = "insert into tblUsers(UserID) values(?)";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, UserID);
            pre.executeUpdate();
        }finally{
            closeConnection();
        }
    }
    
    public boolean checkDuplicateUserId(String UserID) throws SQLException, NamingException{
        try{
            String sql = "select UserID from tblUsers where UserID = ?";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, UserID);
            rs = pre.executeQuery();
            if(rs.next()){
                return true;
            }
        }finally{
            closeConnection();
        }
        return false;
    }
}
