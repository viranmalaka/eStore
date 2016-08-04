/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.HibernateController;
import hibernate.SessionManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logger.LogController;
import model.Item;
import model.PurchaseOrder;
import model.PurchaseOrderItem;
import org.apache.logging.log4j.Level;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import view.UICommonController;
import view.orders.FrmPurchaseOrderController;

/**
 *
 * @author Malaka
 */
public class PurchaseOrderController {

    private PurchaseOrder purchaseOrder;
    private Session session;

    public static void openCreatePurchaseOrderWindow() {
        String newPOId = CommonControllers.convertIndex(getNextIndex(), 'P');
        FXMLLoader createFXML = UICommonController.getInstance().createFXML("orders/frmPurchaseOrder.fxml");
        Stage stage = UICommonController.getInstance().getStage(createFXML);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add New Purchase Order");
        ((FrmPurchaseOrderController) createFXML.getController()).initData(newPOId);

        stage.showAndWait();
    }

    private static long getNextIndex() {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(PurchaseOrder.class);
        Object maxID = criteria.setProjection(Projections.max("id")).uniqueResult();
        session.close();
        if (maxID == null) {
            return 1;
        }
        return Long.parseLong(maxID + "") + 1;
    }

    //<editor-fold defaultstate="collapsed" desc="Adding Purchase Order">
    public boolean  createNewPurchaseOrder(String text, Date value, String SupId,boolean paid, double total) {
        try {
            session = null;
            session = SessionManager.getInstance().getSessionFactory().openSession();
            session.beginTransaction();
            
            purchaseOrder = new PurchaseOrder();
            
            purchaseOrder.setPurchaseOrderID(text);
            purchaseOrder.setDate(value);
            purchaseOrder.setSupplier(SupplierController.getFilterdSupplier(SupplierController.PersonColumns.SupplierID, SupId).get(0));
            purchaseOrder.setPaid(paid);
            purchaseOrder.setTotal(total);
            session.persist(purchaseOrder);
        } catch (HibernateException hibernateException) {
            LogController.log(Level.ERROR, "Hibernate Error when creating Purchase Order -> " + hibernateException.toString());
            return false;
        }
        return true;
    }
    
    public boolean addItemsToPurchaseOrder(boolean preWorks, String id, Double qty, Double purchasePrice, Double labelPrice, String expDate) {
        if (preWorks) {
            try {
                PurchaseOrderItem e = new PurchaseOrderItem();
                
                Item item = (Item) session.createCriteria(Item.class).add(Restrictions.eq("itemID", id)).uniqueResult();
                item.setQuantity(item.getQuantity() + qty);
                item.setLastSupplier(purchaseOrder.getSupplier());
                try {
                    item.setExpDate(new SimpleDateFormat("yyyy-MM-dd").parse(expDate));
                } catch (ParseException ex) {
                    item.setExpDate(null);
                }
                item.setLastPurchasePrice(purchasePrice);
                item.setSellingPrice(labelPrice);
                
                e.setItem(item);
                try {
                    e.setExpDate(new SimpleDateFormat("yyyy-MM-dd").parse(expDate));
                } catch (ParseException ex) {
                    e.setExpDate(null);
                }
                e.setLabeledSellingPrice(labelPrice);
                e.setPurchaseOrder(purchaseOrder);
                e.setPurchasePrice(purchasePrice);
                e.setQty(qty);
                e.setRemainQty(qty);
                
                session.persist(e);
                
                purchaseOrder.addItems(e);
                
            } catch (HibernateException hibernateException) {
                LogController.log(Level.ERROR, "Hibernate Error when adding Items to purchase order -> " + hibernateException.toString());
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
    
    public boolean saveOrder(boolean preWork) {
        if (preWork) {
            session.getTransaction().commit();
            session.close();
            return true;
        }else{
            session.getTransaction().rollback();
            session.close();
            return false;
        }
    }
    
//</editor-fold>
    
}
