/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.util.Date;
import model.Customer;
import model.Item;
import model.PurchaseOrder;
import model.PurchaseOrderItem;
import model.SaleOrder;
import model.SaleOrderItem;
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
        obj.setItemID("I00004");
        obj.setLastPurchasePrice(156);
        obj.setLastSupplier(get);
        obj.setName("Parippu");
        obj.setQuantity(168);
        obj.setScale("u");
        obj.setSellingPrice(168);
        boolean expResult = true;
        boolean result = HibernateController.saveObject(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testSaveObject2() {
        Session s = SessionManager.getInstance().getSessionFactory().openSession();

        
        System.out.println("saveObject Purchase Order");
        PurchaseOrder obj = new PurchaseOrder();

        PurchaseOrderItem i1 = new PurchaseOrderItem();
        i1.setItem(s.get(Item.class, 1));
        i1.setPurchaseOrder(obj);
        
        PurchaseOrderItem i2 = new PurchaseOrderItem();
        i2.setItem(s.get(Item.class, 3));
        i2.setPurchaseOrder(obj);
        
        obj.getItems().add(i1);
        obj.getItems().add(i2);
        
        boolean expResult = true;
        boolean result = HibernateController.saveObject(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
 
    @Test
    public void testSaveObject3() {
        /*System.out.println("saveObject Sales Order");
        
        Session s = SessionManager.getInstance().getSessionFactory().openSession();
        
        SaleOrder obj = new SaleOrder();
        obj.customer = s.get(Customer.class, 1);
        obj.items.add(new SaleOrderItem(obj, s.get(Item.class, 2)));
        obj.items.add(new SaleOrderItem(obj, s.get(Item.class, 1)));
        obj.items.add(new SaleOrderItem(obj, s.get(Item.class, 3)));
        
        
        boolean expResult = true;
        boolean result = HibernateController.saveObject(obj);
        assertEquals(expResult, result);*/
        // TODO review the generated test code and remove the default call to fail.
    }
}
