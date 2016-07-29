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
public class CommonControllersTest {
    
    public CommonControllersTest() {
    }

    /**
     * Test of convertIndex method, of class CommonControllers.
     */
    @Test
    public void testConvertIndex() {
        System.out.println("convertIndex");
        long id = 100L;
        char c = 'C';
        String expResult = "C00100";
        String result = CommonControllers.convertIndex(id, c);
        assertEquals(expResult, result);
    }

    /**
     * Test of isName method, of class CommonControllers.
     */
    @Test
    public void testIsName() {
        System.out.println("isName");
        String name = "malaka dfgsdfg Mr.";
        boolean expResult = true;
        boolean result = CommonControllers.isName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of isTelephoneNumber method, of class CommonControllers.
     */
    @Test
    public void testIsTelephoneNumber() {
        System.out.println("isTelephoneNumber");
        String num = "923-1234506";
        boolean expResult = true;
        boolean result = CommonControllers.isTelephoneNumber(num);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
