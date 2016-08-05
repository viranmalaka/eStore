/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    private long id;

    @Column(length = 7, nullable = false, unique = true, updatable = false)
    private String itemID;
    @Column(length = 100, nullable = false)
    private String name;

    Units scale;

    public Item(String itemID, String name, Units scale) {
        this.itemID = itemID;
        this.name = name;
        this.scale = scale;
    }

    public Item() {
    }

    
    public Units getScale() {
        return scale;
    }

    public void setScale(Units scale) {
        this.scale = scale;
    }

    public long getId() {
        return id;
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



    public static enum Units {
        kg, l, m, unit
    }
}
