/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Malaka
 */
public class SaleOrderItem {
    @Id @GeneratedValue
    public long id;
     /*
    @ManyToOne
    public SaleOrder saleOrder;
    
    @OneToOne
    public Item item;
    
    public SaleOrderItem(SaleOrder s, Item i) {
    saleOrder = s;
    item = i;
    }
    
    
    
    double qty;
    double sellingPrice;
    double total;
    float discount;*/
}
