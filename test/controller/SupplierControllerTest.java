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
public class SupplierControllerTest {
    
    public SupplierControllerTest() {
    }

    /**
     * Test of saveSupplier method, of class SupplierController.
     */
    @Test
    public void testSaveSupplier() {
        System.out.println("saveSupplier");
        String supplierID = "S00001";
        String fName = "sdf";
        String lName = "sdfg";
        String address = "sdfgsdf,gsdfg,sdf";
        String telephone = "34545";
        boolean expResult = true;
        boolean result = SupplierController.saveSupplier(supplierID, fName, lName, address, telephone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of updateSupplier method, of class SupplierController.
     */
    @Test
    public void testUpdateSupplier() {
        System.out.println("updateSupplier");
        String supplierID = "S00001";
        String fName = "Viran";
        String lName = "Malaka";
        String address = "Nakkavita";
        String telephone = "077-9988420";
        boolean expResult = true;
        boolean result = SupplierController.updateSupplier(supplierID, fName, lName, address, telephone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getNextIndex method, of class SupplierController.
     */
    @Test
    public void testGetNextIndex() {
        System.out.println("getNextIndex");
        long expResult = 2L;
        long result = SupplierController.getNextIndex();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
