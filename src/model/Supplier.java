/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Malaka
 */

@Entity
public class Supplier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 7, nullable = false, unique = true, updatable = false)
    private String supplierID; // 'S' + [5*0]id
    @Column(length = 100, nullable = false)
    private String firstName;
    @Column(length = 100)
    private String lastName;
    @Column(length = 12)
    private String telephone;
    private String address;

    public Supplier(String SupplierID, String firstName, String lastName, String telephone, String address) {
        this.supplierID = SupplierID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.address = address;
    }

    public Supplier() {
    }
    
    
    /**
     * @return the customerID
     */
    public String getSupplierID() {
        return supplierID;
    }

    /**
     * @param supplierID
     */
    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
