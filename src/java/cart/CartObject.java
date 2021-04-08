/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.naming.NamingException;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import tblorderdetail.tblOrderDetailDAO;


/**
 *
 * @author IT
 */
public class CartObject {
    private HashMap<String,ItemOrder> map;

    public void addYourCart(ItemOrder newitem){
        if(this.map == null){
            map = new HashMap<>();
        }
        if(map.containsKey(newitem.getIdproduct())){
            ItemOrder item = map.get(newitem.getIdproduct());
            int quantity = item.getQuantity()+1;
            item.setQuantity(quantity);
            int price = item.getPrice();
            item.setTotal(price*quantity);
        }
        else{
            map.put(newitem.getIdproduct(), newitem);
        }
    }
    
    public void removeYourCart(String id){
        if(map.containsKey(id)){
            map.remove(id);
        }
        if(map.size() == 0){
            map = null;
        }
    }
    
    public void setQuantity(String id, int quantity){
        ItemOrder item = map.get(id);
        item.setQuantity(quantity);
        int price = item.getPrice();
        int total = price*quantity;
        item.setTotal(total);
    }

    public HashMap<String, ItemOrder> getMap() {
        return map;
    }

    public void setMap(HashMap<String, ItemOrder> map) {
        this.map = map;
    }
    
    public void insertProduct(String iddetail, String idOrder) throws NamingException, SQLException{
        Set<String> idProducts = map.keySet();
        tblOrderDetailDAO detailDAO = new tblOrderDetailDAO();
            for (String idProduct : idProducts) {
                int intquantity = Integer.parseInt(iddetail);
                intquantity++;
                iddetail = String.valueOf(intquantity);
                ItemOrder item = map.get(idProduct);
                //get quantity
                int quantity = item.getQuantity();
                //get price
                int priceOfPruduct = item.getPrice();
                int price = priceOfPruduct*quantity;
                detailDAO.insertOrderDetail(iddetail, idOrder, idProduct, quantity, price);
            }
    }
    public List<ItemOrder> getListItem(){
        List<ItemOrder> list = new ArrayList<>();
        Set<String> keys = this.map.keySet();
        for (String key : keys) {
            ItemOrder item = this.map.get(key);
            list.add(item);
        }
        return list;
    }
}
