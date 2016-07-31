/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.util.Date;
import model.Item;
import model.Supplier;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Malaka
 */
public class HibernateControllerTest {
    
    public HibernateControllerTest() {
    }

    /**
     * Test of saveObject method, of class HibernateController.
     */
    @Test
    public void testSaveObject() {
        Session s = SessionManager.getInstance().getSessionFactory().openSession();
        Supplier get = s.get(Supplier.class, 3);
        
        System.out.println("saveObject");
        Item obj = new Item();
        obj.setExpDate(new Date());
        obj.setItemID("I00003");
        obj.setLastPurchasePrice(156);
        obj.setLastSupplier(get);
        obj.setName("Parippu");
        obj.setQuantity(168);
        obj.setScale("m");
        obj.setSellingPrice(168);
        boolean expResult = true;
        boolean result = HibernateController.saveObject(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
