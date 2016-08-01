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
    long id;
    /*
    @Temporal(TemporalType.DATE)
    Date date;
    
    //@OneToOne
    public Customer customer;
    
    float overrollDiscount;
    double Total;
    boolean paid;
    
    @OneToMany(mappedBy ="saleOrder", cascade = CascadeType.PERSIST)
    public List<SaleOrderItem> items = new ArrayList<>();*/
    
}
