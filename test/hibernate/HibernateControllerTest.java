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
import org.omg.PortableServer.POA;

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

        System.out.println("saveObject");
        Item obj = new Item("I002", "parippu", Item.Units.l);

        boolean expResult = true;
        boolean result = HibernateController.saveObject(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    @Test
    public void testSaveObject2() {
        Session s = SessionManager.getInstance().getSessionFactory().openSession();
        s.beginTransaction();
        
        System.out.println("saveObject Purchase Order");
        PurchaseOrder obj = new PurchaseOrder();

        obj.setDate(new Date());
        obj.setSupplier(s.get(Supplier.class, 2L));
        obj.setPaid(true);
        obj.setPurchaseOrderID("P00010");

        Item get = s.get(Item.class, 3L);
        //s.update(get);

        PurchaseOrderItem e = new PurchaseOrderItem();
        e.setItem(get);
        e.setPurchaseOrder(obj);
        e.setQty(100);
        e.setRemainQty(100);
        e.setPurchasePrice(140);
        e.setLabeledSellingPrice(150);
        e.setExpDate(new Date());

        Item get1 = s.get(Item.class, 4L);
        //s.update(get1);

        PurchaseOrderItem e1 = new PurchaseOrderItem();
        e1.setItem(get1);
        e1.setPurchaseOrder(obj);
        e1.setQty(200);
        e1.setRemainQty(200);
        e1.setPurchasePrice(240);
        e1.setLabeledSellingPrice(250);
        e1.setExpDate(new Date());

        obj.addItems(e);
        obj.addItems(e1);
        
        s.getTransaction().commit();
        s.close();
        boolean expResult = true;
        boolean result = HibernateController.saveObject(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    @Test
    public void testUpdatingObject() {
        System.out.println("update a purchase order");

        Session s = SessionManager.getInstance().getSessionFactory().openSession();
        s.beginTransaction();
        
        PurchaseOrder p = (PurchaseOrder)s.get(PurchaseOrder.class, 11L);
        
        for (PurchaseOrderItem item : p.getItems()) {
            System.out.println(item);
        }
        p.getItems().remove(0);
        
        s.getTransaction().commit();
        s.close();
        
        assertEquals(true, true);
    }

}
