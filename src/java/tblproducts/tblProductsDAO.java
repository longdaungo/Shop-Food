/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblproducts;

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
public class tblProductsDAO {

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

    public int countProducts(String searchValue, String category) throws NamingException, SQLException {
        try {
            int count = 0;
            String sql = "Select count(*) as count from tblProducts where Status = 1 and Name like ? and Category like ? and Quantity > 0";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, "%" + searchValue + "%");
            pre.setString(2, "%" + category + "%");
            rs = pre.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            return count;
        } finally {
            closeConnection();
        }
    }

    public int countProductsByPrice(String searchValue, String category, int moneyFrom, int moneyTo) throws NamingException, SQLException {
        try {
            int count = 0;
            String sql = "Select count(*) as count from tblProducts where Status = 1 and Name like ? and Category like ? and Price>=? and Price <=? and Quantity > 0";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, "%" + searchValue + "%");
            pre.setString(2, "%" + category + "%");
            pre.setInt(3, moneyFrom);
            pre.setInt(4, moneyTo);
            rs = pre.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            return count;
        } finally {
            closeConnection();
        }

    }

    public void insertFood(String id, String name, int price, int quantity, boolean status, String image, String currentDate, String category, int quantity_purchased) throws NamingException, SQLException {
        String sql = "insert into tblProducts(ID, Name, Price, Quantity, Status, Image, CreateDate, Category, quantity_purchased) values(?,?,?,?,?,?,?,?,?)";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, id);
            pre.setString(2, name);
            pre.setInt(3, price);
            pre.setInt(4, quantity);
            pre.setBoolean(5, status);
            pre.setString(6, image);
            pre.setString(7, currentDate);
            pre.setString(8, category);
            pre.setInt(9, quantity_purchased);
            pre.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public int countAllProduct() throws NamingException, SQLException {
        try {
            int count = 0;
            String sql = "Select count(*) as count from tblProducts";
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

    public List<tblProductsDTO> getProducts(int pageIndex, String searchValue, String category) throws NamingException, SQLException {
        List<tblProductsDTO> list = null;
        String sql = "with x as "
                + "(select ROW_NUMBER() over(order by ID asc) as r, "
                + "* from tblProducts where Name like ? and Category like ? and Status = 1 and Quantity > 0) "
                + "select ID, Name, Quantity, Price, Image, CreateDate, Category from x where r between ? and ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, "%" + searchValue + "%");
            pre.setString(2, "%" + category + "%");
            pre.setInt(3, pageIndex * 3 - 2);
            pre.setInt(4, pageIndex * 3);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                int quantity = rs.getInt("Quantity");
                int price = rs.getInt("Price");
                String image = rs.getString("Image");
                String createdate = rs.getString("CreateDate");
                String Category = rs.getString("Category");
                tblProductsDTO dto = new tblProductsDTO(id, name, quantity, price, true, image, createdate, Category);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<tblProductsDTO> getProductsByPriceAndName(int pageIndex, String searchValue, int moneyFrom, int moneyTo, String category) throws NamingException, SQLException {
        List<tblProductsDTO> list = null;
        String sql = "with x as "
                + "(select ROW_NUMBER() over(order by ID asc) as r, "
                + "* from tblProducts where Name like ? and Category like ? and Price >= ? and Price <= ? and Status = 1 and Quantity > 0) "
                + "select ID, Name, Quantity, Price, Image, CreateDate, Category from x where r between ? and ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, "%" + searchValue + "%");
            pre.setString(2, "%" + category + "%");
            pre.setInt(3, moneyFrom);
            pre.setInt(4, moneyTo);
            pre.setInt(5, pageIndex * 3 - 2);
            pre.setInt(6, pageIndex * 3);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String name = rs.getString("Name");
                int quantity = rs.getInt("Quantity");
                int price = rs.getInt("Price");
                String image = rs.getString("Image");
                String createdate = rs.getString("CreateDate");
                String Category = rs.getString("Category");
                tblProductsDTO dto = new tblProductsDTO(ID, name, quantity, price, true, image, createdate, Category);
                list.add(dto);
            }

        } finally {
            closeConnection();
        }
        return list;
    }

    public List<String> getAllCategory() throws NamingException, SQLException {
        String sql = "select distinct Category from tblProducts";
        List<String> list = null;
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String category = rs.getString("Category");
                list.add(category);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public void deleteProduct(String id) throws NamingException, SQLException {
        String sql = "update tblProducts set status = 0 where ID = ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, id);
            pre.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public void updateProduct(String id, String Name, int price, int quantity, String image, String category) throws NamingException, SQLException {
        String sql = "Update tblProducts set Name = ?, Price = ?, Quantity = ?, Image = ?, Category = ? where ID = ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, Name);
            pre.setInt(2, price);
            pre.setInt(3, quantity);
            pre.setString(4, image);
            pre.setString(5, category);
            pre.setString(6, id);
            pre.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public int getQuantity(String idproduct) throws NamingException, SQLException {
        String sql = "select Quantity from tblProducts where ID = ?";
        int quantity = 0;
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, idproduct);
            rs = pre.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt("Quantity");
            }
        } finally {
            closeConnection();
        }
        return quantity;
    }

    public void updateQuantityProduct(String id, int quantity) throws NamingException, SQLException {
        String sql = "Update tblProducts set Quantity = ? where ID = ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setInt(1, quantity);
            pre.setString(2, id);
            pre.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public String getName(String IDProduct) throws NamingException, SQLException {
        String name = null;
        String sql = "Select Name from tblProducts where ID = ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, IDProduct);
            rs = pre.executeQuery();
            if (rs.next()) {
                name = rs.getString("Name");
            }
        } finally {
            closeConnection();
        }
        return name;
    }

    public void updateQuantityPurchased(String id, int quantity) throws NamingException, SQLException {
        try {
            String sql = "Update tblProducts set quantity_purchased =? Where ID =?";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setInt(1, quantity);
            pre.setString(2, id);
            pre.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public int getQuantityPurchased(String id) throws NamingException, SQLException {
        int quantityPurchased = 0;
        try {
            String sql = "Select quantity_purchased from tblProducts where ID = ?";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, id);
            rs = pre.executeQuery();
            if (rs.next()) {
                quantityPurchased = rs.getInt("quantity_purchased");
            }
        } finally {
            closeConnection();
        }
        return quantityPurchased;
    }

    public List<tblProductsDTO> getProductsByBestSeller(int pageIndex) throws NamingException, SQLException {
        List<tblProductsDTO> list = null;
        String sql = "with x as "
                + "(select ROW_NUMBER() over(order by quantity_purchased desc) as r, "
                + "* from tblProducts where Status = 1 and Quantity > 0) "
                + "select ID, Name, Quantity, Price, Image, CreateDate, Category from x where r between ? and ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setInt(1, pageIndex * 3 - 2);
            pre.setInt(2, pageIndex * 3);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String name = rs.getString("Name");
                int quantity = rs.getInt("Quantity");
                int price = rs.getInt("Price");
                String image = rs.getString("Image");
                String createdate = rs.getString("CreateDate");
                String Category = rs.getString("Category");
                tblProductsDTO dto = new tblProductsDTO(ID, name, quantity, price, true, image, createdate, Category);
                list.add(dto);
            }

        } finally {
            closeConnection();
        }
        return list;
    }

    public List<tblProductsDTO> getListRecommendProduct(String userID, int pageIndex) throws NamingException, SQLException {
        String sql = "with x as "
                + "(select ROW_NUMBER() over(order by ID asc) as r, * "
                + "from tblProducts "
                + "where Quantity > 0 and Status = 1 and Category in "
                + "	(select distinct Category "
                + "	from tblProducts "
                + "	where ID in "
                + "		(select distinct ProductID "
                + "		from tblOrderDetail "
                + "		where OrderID in "
                + "			(select OrderID from tblOrders where UserID = ?)))) "
                + "			select ID, Name, Quantity, Price, Image, CreateDate, Category from x where r between ? and ?";
        List<tblProductsDTO> list = null;
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, userID);
            pre.setInt(2, pageIndex * 3 - 2);
            pre.setInt(3, pageIndex * 3);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                int Quantity = rs.getInt("Quantity");
                int price = rs.getInt("Price");
                String image = rs.getString("Image");
                String createdate = rs.getString("CreateDate");
                String category = rs.getString("Category");
                tblProductsDTO dto = new tblProductsDTO(id, name, Quantity, price, true, image, createdate, category);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public int countRecommendProduct(String userID) throws NamingException, SQLException {
        int numberproduct = 0;
        String sql = "with x as "
                + "(select ROW_NUMBER() over(order by ID asc) as r, * "
                + "from tblProducts "
                + "where Quantity > 0 and Status = 1 and Category in "
                + "	(select distinct Category "
                + "	from tblProducts "
                + "	where ID in "
                + "		(select distinct ProductID "
                + "		from tblOrderDetail "
                + "		where OrderID in "
                + "			(select OrderID from tblOrders where UserID = ?)))) "
                + "			select count(*) as count from x";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, userID);
            rs = pre.executeQuery();
            if(rs.next()){
                numberproduct =rs.getInt("count");
            }
        } finally {
            closeConnection();
        }
        return numberproduct;
    }
    
    public List<tblProductsDTO> getProductsByAdmin(int pageIndex, String searchValue, String category) throws NamingException, SQLException {
        List<tblProductsDTO> list = null;
        String sql = "with x as "
                + "(select ROW_NUMBER() over(order by ID asc) as r, "
                + "* from tblProducts where Name like ? and Category like ? and Status = 1) "
                + "select ID, Name, Quantity, Price, Image, CreateDate, Category from x where r between ? and ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, "%" + searchValue + "%");
            pre.setString(2, "%" + category + "%");
            pre.setInt(3, pageIndex * 3 - 2);
            pre.setInt(4, pageIndex * 3);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                int quantity = rs.getInt("Quantity");
                int price = rs.getInt("Price");
                String image = rs.getString("Image");
                String createdate = rs.getString("CreateDate");
                String Category = rs.getString("Category");
                tblProductsDTO dto = new tblProductsDTO(id, name, quantity, price, true, image, createdate, Category);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public int countProductsByAdmin(String searchValue, String category) throws NamingException, SQLException {
        try {
            int count = 0;
            String sql = "Select count(*) as count from tblProducts where Status = 1 and Name like ? and Category like ?";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, "%" + searchValue + "%");
            pre.setString(2, "%" + category + "%");
            rs = pre.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            return count;
        } finally {
            closeConnection();
        }
    }
    
    public List<tblProductsDTO> getProductsByPriceAndNameByAdmin(int pageIndex, String searchValue, int moneyFrom, int moneyTo, String category) throws NamingException, SQLException {
        List<tblProductsDTO> list = null;
        String sql = "with x as "
                + "(select ROW_NUMBER() over(order by ID asc) as r, "
                + "* from tblProducts where Name like ? and Category like ? and Price >= ? and Price <= ? and Status = 1) "
                + "select ID, Name, Quantity, Price, Image, CreateDate, Category from x where r between ? and ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, "%" + searchValue + "%");
            pre.setString(2, "%" + category + "%");
            pre.setInt(3, moneyFrom);
            pre.setInt(4, moneyTo);
            pre.setInt(5, pageIndex * 3 - 2);
            pre.setInt(6, pageIndex * 3);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String name = rs.getString("Name");
                int quantity = rs.getInt("Quantity");
                int price = rs.getInt("Price");
                String image = rs.getString("Image");
                String createdate = rs.getString("CreateDate");
                String Category = rs.getString("Category");
                tblProductsDTO dto = new tblProductsDTO(ID, name, quantity, price, true, image, createdate, Category);
                list.add(dto);
            }

        } finally {
            closeConnection();
        }
        return list;
    }
    
    public int countProductsByPriceByAdmin(String searchValue, String category, int moneyFrom, int moneyTo) throws NamingException, SQLException {
        try {
            int count = 0;
            String sql = "Select count(*) as count from tblProducts where Status = 1 and Name like ? and Category like ? and Price>=? and Price <=?";
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, "%" + searchValue + "%");
            pre.setString(2, "%" + category + "%");
            pre.setInt(3, moneyFrom);
            pre.setInt(4, moneyTo);
            rs = pre.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            return count;
        } finally {
            closeConnection();
        }
}
}
