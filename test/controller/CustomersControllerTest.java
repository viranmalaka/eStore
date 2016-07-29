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
        int id = 0;
        String fName = "";
        String lName = "";
        String address = "";
        String telephone = "";
        boolean expResult = false;
        boolean result = CustomersController.saveCustomer(id, fName, lName, address, telephone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNextIndex method, of class CustomersController.
     */
    @Test
    public void testGetNextIndex() {
        System.out.println("getNextIndex");
        long expResult =6L;
        long result = CustomersController.getNextIndex();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
