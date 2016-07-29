/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.SessionManager;
import model.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Malaka
 */
public class CustomersControllerTest {
    
    public CustomersControllerTest() {
    }

    /**
     * Test of saveCustomer method, of class CustomersController.
     */
    @Test
    public void testSaveCustomer() {
        System.out.println("saveCustomer");
        String customerID = "C001";
        String fName = "test first name";
        String lName = "test last name";
        String address = "test address";
        String telephone = "test tele";
        boolean expResult = true;
        boolean result = CustomersController.saveCustomer(customerID, fName, lName, address, telephone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getNextIndex method, of class CustomersController.
     */
    @Test
    public void testGetNextIndex() {
        System.out.println("getNextIndex");
        long expResult =3L;
        long result = CustomersController.getNextIndex();
        System.out.println(result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of updateCustomer method, of class CustomersController.
     */
    @Test
    public void testUpdateCustomer() {
        System.out.println("updateCustomer");
        String customerID = "C00003";
        String fName = "Kashuni";
        String lName = "Tharika";
        String address = "Nakkavita";
        String telephone = "077-9944558";
        boolean expResult = true;
        boolean result = CustomersController.updateCustomer(customerID, fName, lName, address, telephone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }



    
}
