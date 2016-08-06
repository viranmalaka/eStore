/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.SessionManager;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.print.Collation;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logger.LogController;
import model.Item;
import model.PurchaseOrderItem;
import model.SaleOrder;
import model.SaleOrderItem;
import org.apache.logging.log4j.Level;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import view.UICommonController;
import view.orders.FrmSaleOrderController;

/**
 *
 * @author Malaka
 */
public class SalesOrderController {

    private SaleOrder saleOrder;
    private Session session;

    public static void openCreateSalesOrderWindow() {
        String newSOId = CommonControllers.convertIndex(getNextIndex(), 'S');
        FXMLLoader createFXML = UICommonController.getInstance().createFXML("orders/frmSaleOrder.fxml");
        Stage stage = UICommonController.getInstance().getStage(createFXML);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add New Purchase Order");
        ((FrmSaleOrderController) createFXML.getController()).initData(newSOId);

        stage.showAndWait();
    }

    private static long getNextIndex() {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(SaleOrder.class
        );
        Object maxID = criteria.setProjection(Projections.max("id")).uniqueResult();
        session.close();
        if (maxID == null) {
            return 1;
        }
        return Long.parseLong(maxID + "") + 1;
    }

    public boolean createNewPurchaseOrder(String id, Date date, String cusID, boolean paid, double total) {
        try {
            session = null;
            session = SessionManager.getInstance().getSessionFactory().openSession();
            session.beginTransaction();

            saleOrder = new SaleOrder();

            saleOrder.setSaleOrderID(id);
            saleOrder.setDate(date);
            saleOrder.setCustomer(CustomersController.getFilterdCustomers(cusID, CustomersController.PersonColumns.CustomerID).get(0));
            saleOrder.setPaid(paid);
            session.persist(saleOrder);

        } catch (HibernateException hibernateException) {
            LogController.log(Level.ERROR, "Hibernate Error when creating Sales Order -> " + hibernateException.toString());
            return false;
        }
        return true;
    }

    public boolean addItemsToPurchaseOrder(boolean preWork, String id, Double qty, Double uPrice, Double discount) {
        if (preWork) {
            try {
                SaleOrderItem e = new SaleOrderItem();

                Item item = (Item) session.createCriteria(Item.class)
                        .add(Restrictions.eq("itemID", id)).uniqueResult();

                e.setItem(item);
                e.setQty(qty);

                List<PurchaseOrderItem> poIforItem = ItemController.getPOIforItem(item, session);
                Collections.sort(poIforItem, (o1, o2) -> Double.compare(o1.getId(), o2.getId()));

                for (PurchaseOrderItem purchaseOrderItem : poIforItem) {
                    if (purchaseOrderItem.getRemainQty() > 0) {
                        if (purchaseOrderItem.getRemainQty() >= qty) {
                            purchaseOrderItem.setRemainQty(purchaseOrderItem.getRemainQty()- qty);
                            break;
                        } else {
                            qty -= purchaseOrderItem.getRemainQty();
                            purchaseOrderItem.setRemainQty(0);
                        }
                    }
                }

                e.setDiscount(discount);
                e.setTotal(uPrice * qty * (100 - discount) / 100);
                e.setSaleOrder(saleOrder);
                e.setSellingPrice(uPrice);
                
                session.persist(e);
                saleOrder.addItem(e);
                
            } catch (HibernateException hibernateException) {
                LogController.log(Level.ERROR, "Hibernate Error when adding Items to Seles order -> " + hibernateException.toString());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean saveOrder(boolean preWork) {
        if (preWork) {
            session.getTransaction().commit();
            session.close();
            saleOrder = null;
            return true;
        } else {
            session.getTransaction().rollback();
            session.close();
            saleOrder = null;
            return false;
        }
    }
}
