/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tbloders;

/**
 *
 * @author IT
 */
public class tblordersDTO {
    String id;
    String userid;
    int total;
    String date;

    public tblordersDTO(String id, int total, String date) {
        this.id = id;
        this.total = total;
        this.date = date;
    }

    public tblordersDTO(String id, String userid, int total, String date) {
        this.id = id;
        this.userid = userid;
        this.total = total;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
