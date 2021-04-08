/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblorderdetail;

/**
 *
 * @author IT
 */
public class tblOrderDeteilDTO {
    private String iddetail;
    private String OrderID;
    private String productid;
    private int quantity;
    private int price;

    public tblOrderDeteilDTO(String iddetail, String productid, int quantity, int price) {
        this.iddetail = iddetail;
        this.productid = productid;
        this.quantity = quantity;
        this.price = price;
    }

    
    public String getIddetail() {
        return iddetail;
    }

    public void setIddetail(String iddetail) {
        this.iddetail = iddetail;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String OrderID) {
        this.OrderID = OrderID;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    
}
