/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Malaka
 */
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 7, nullable = false, unique = true, updatable = false)
    private String itemID;
    @Column(length = 100, nullable = false)
    private String name;
    private double quantity;

    @ManyToOne
    @JoinColumn(name = "lastSupplierId")
    private Supplier lastSupplier;

    @Temporal(TemporalType.DATE)
    private Date expDate;
    private double lastPurchasePrice;
    private double SellingPrice;
    Units scale;

    @Column(length = 1)
    public String getScale() {
        return scale.toString().substring(0, 1);
    }

    public void setScale(String scale) {
        switch (scale) {
            case "k":
                this.scale = Units.kg;
                break;
            case "l":
                this.scale = Units.l;
                break;
            case "m":
                this.scale = Units.m;
                break;
            case "u":
                this.scale = Units.unit;
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setLastSupplier(Supplier lastSupplier) {
        this.lastSupplier = lastSupplier;
    }

    public Supplier getLastSupplier() {
        return lastSupplier;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public double getLastPurchasePrice() {
        return lastPurchasePrice;
    }

    public void setLastPurchasePrice(double lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double SellingPrice) {
        this.SellingPrice = SellingPrice;
    }

    public static enum Units {
        kg, l, m, unit
    }
}
