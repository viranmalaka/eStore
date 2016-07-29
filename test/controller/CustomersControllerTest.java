/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
    
}
