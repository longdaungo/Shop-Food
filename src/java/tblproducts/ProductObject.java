/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblproducts;
import cart.CartObject;
import cart.ItemOrder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;

/**
 *
 * @author IT
 */
public class ProductObject {
    Map<String,Integer> map;

    public ProductObject() {
        map = new HashMap<>();
    }
    
    
    public void getQuantityByids(CartObject cart) throws NamingException, SQLException{
        Map<String,ItemOrder> mapCart = cart.getMap();
        Set<String> ids = mapCart.keySet();
        
        
        for (String id : ids) {
            tblProductsDAO dao = new tblProductsDAO();
            int quantity = dao.getQuantity(id);
            map.put(id, quantity);
        }
    }
    
    public void compareQuantity(CartObject cart) throws Exception{
        // lay quantity va id product cart
        Map<String,ItemOrder> mapCart = cart.getMap();
        Set<String> idproductcarts = mapCart.keySet();
        
        //lay quantity dua vao id product cart
        for (String idproductcart : idproductcarts) {
            ItemOrder item = mapCart.get(idproductcart);
            int quantityproductcart = item.getQuantity();
            int quantityproduct = this.map.get(idproductcart);
            if(quantityproduct < quantityproductcart)
                throw new Exception("Quantity of "+item.getName()+" is out of stock");
        }
        
    }
    
    public void updateQuantity(CartObject cart) throws NamingException, SQLException {
        Map<String,ItemOrder> mapCart = cart.getMap();
        Set<String> idproductcarts = mapCart.keySet();
        for (String idproductcart : idproductcarts) {
            ItemOrder item = mapCart.get(idproductcart);
            int quantityproductcart = item.getQuantity();
            int quantityproduct = this.map.get(idproductcart);
            quantityproduct = quantityproduct-quantityproductcart;
            tblProductsDAO dao = new tblProductsDAO();
            dao.updateQuantityProduct(idproductcart, quantityproduct);
        }
    }
    
    public void updateQuantityPurchased(CartObject cart) throws NamingException, SQLException{
        Map<String,ItemOrder> mapCart = cart.getMap();
        Set<String> keys = mapCart.keySet();
        tblProductsDAO dao = new tblProductsDAO();
        for (String key : keys) {
            int quantityPurchased = dao.getQuantityPurchased(key);
            int updatequantityPurchased = quantityPurchased + mapCart.get(key).getQuantity();
            dao.updateQuantityPurchased(key, updatequantityPurchased);
        }
    }
}
