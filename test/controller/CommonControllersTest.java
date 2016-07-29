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
    
}
