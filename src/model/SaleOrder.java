/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Malaka
 */
@Entity
public class SaleOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 7,nullable = false,unique = true, updatable = false)
    private String saleOrderID;
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @OneToOne
    private Customer customer;
    
    private float overrollDiscount;
    private boolean paid;
    
    @OneToMany(mappedBy ="saleOrder", cascade = CascadeType.ALL)
    private List<SaleOrderItem> items = new ArrayList<>();

    public long getId() {
        return id;
    }

    public String getSaleOrderID() {
        return saleOrderID;
    }

    public void setSaleOrderID(String saleOrderID) {
        this.saleOrderID = saleOrderID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public float getOverrollDiscount() {
        return overrollDiscount;
    }

    public void setOverrollDiscount(float overrollDiscount) {
        this.overrollDiscount = overrollDiscount;
    }


    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public List<SaleOrderItem> getItems() {
        return items;
    }

    public void addItem(SaleOrderItem item) {
        this.items.add(item);
    }
    
}
