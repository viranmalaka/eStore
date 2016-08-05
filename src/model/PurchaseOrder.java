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
import javax.persistence.FetchType;
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
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 7, nullable = false, unique = true, updatable = false)
    private String purchaseOrderID;
    @Temporal(TemporalType.DATE)
    private Date date;
    private boolean paid;

    @OneToOne
    Supplier supplier;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<PurchaseOrderItem> items = new ArrayList<>();

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public List<PurchaseOrderItem> getItems() {
        return items;
    }

    public void addItems(PurchaseOrderItem i) {
        this.items.add(i);
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public String getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(String purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

    public void clearItems() {
        items = new ArrayList<>();
    }

    public double getTotal() {
        double total = 0;
        for (PurchaseOrderItem item : items) {
            total += item.getQty() * item.getPurchasePrice();
        }
        return total;
    }

}
