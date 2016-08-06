/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. */
package controller;

import hibernate.HibernateController;
import hibernate.SessionManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Item;
import model.PurchaseOrderItem;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import view.MainWindowController;
import view.UICommonController;
import view.items.FrmItemController;
import view.orders.FrmPurchaseOrderController;
import view.orders.FrmSaleOrderController;


/**
 *
 * @author Malaka
 */
public class ItemController {

    public static boolean openAddNewItemWindow() {
        String newItemId = CommonControllers.convertIndex(getNextIndex(), 'I');

        FXMLLoader createFXML = UICommonController.getInstance().createFXML("items/frmItem.fxml");
        Stage stage = UICommonController.getInstance().getStage(createFXML);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add New Item");
        ((FrmItemController) createFXML.getController()).initData(newItemId);

        stage.showAndWait();
        return ((FrmItemController) createFXML.getController()).isAdded();
    }

    public static boolean saveItem(String itemID, String name, Item.Units scale) {
        Item item = new Item(itemID, name, scale);
        return HibernateController.saveObject(item);
    }

    public static boolean updateItem(String itemID, String name, Item.Units scale) {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Item.class);
        Item item = (Item) criteria.add(Restrictions.eq("itemID", itemID)).uniqueResult();

        item.setName(name);
        item.setScale(scale);

        session.update(item);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public static long getNextIndex() {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Item.class);
        Object maxID = criteria.setProjection(Projections.max("id")).uniqueResult();
        session.close();
        if (maxID == null) {
            return 1;
        }
        return Long.parseLong(maxID + "") + 1;
    }

    public static void openEditItemWindow(String id) {

        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Item.class);
        Item item = (Item) criteria.add(Restrictions.eq("itemID", id)).uniqueResult();

        FXMLLoader createFXML = UICommonController.getInstance().createFXML("items/frmItem.fxml");
        Stage stage = UICommonController.getInstance().getStage(createFXML);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Edit Item");
        ((FrmItemController) createFXML.getController()).initData(
                item.getItemID(),
                item.getName(),
                item.getScale().toString());
        session.close();

        stage.showAndWait();

    }

    public static ArrayList<String> getAllUnits() {
        ArrayList<String> res = new ArrayList<>();
        for (Item.Units value : Item.Units.values()) {
            res.add(value.toString());
        }
        return res;
    }

    public static void refreshTable(MainWindowController controller, ItemColumns itemColumns, String arg) {
        List<Item> list = getFilterdItem(itemColumns, arg);
        Session session = SessionManager.getInstance().getSessionFactory().openSession();
        session.beginTransaction();

        if (list != null && list.size() > 0) {
            for (Item item : list) {

                /*PurchaseOrderItem get = (PurchaseOrderItem) session.createCriteria(PurchaseOrderItem.class)
                .add(Restrictions.eq("", item.getId()))
                .addOrder(Order.desc("id")).list().get(1);*/
                Query q = session.createQuery("from PurchaseOrderItem where item_id='" + item.getItemID() + "'");
                List<PurchaseOrderItem> getList = q.list();
                PurchaseOrderItem get;
                try {
                    get = getList.get(1);
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    // null or empty list
                    get = null;
                }
                double remainQty = getRemainQty(item, session);
                if (get != null) {
                    controller.tblItemAddItem(item.getItemID(),
                            item.getName(),
                            item.getScale().toString(),
                            remainQty,
                            get.getExpDate() == null ? "" : get.getExpDate().toString(),
                            get.getPurchasePrice(),
                            get.getLabeledSellingPrice(),
                            get.getPurchaseOrder().getSupplier() == null ? "" : get.getPurchaseOrder().getSupplier().getSupplierID()
                    );
                } else {
                    controller.tblItemAddItem(item.getItemID(),
                            item.getName(),
                            item.getScale().toString(),
                            remainQty,
                            "",
                            0,
                            0,
                            "");
                }

            }
        }
        controller.tblItemSetItems();
    }

    public static void setItemInPurchaseOrder(FrmPurchaseOrderController aThis, ItemColumns itemColumns, String text) {
        List<Item> list = getFilterdItem(itemColumns, text);
        if (list != null && !list.isEmpty()) {
            Item get = list.get(0);
            aThis.setItemValue(get.getItemID(),
                    get.getName(),
                    get.getScale().toString());
        }
    }

    public static List<Item> getFilterdItem(ItemColumns itemColumns, String arg) {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            switch (itemColumns) {
                case ItemID:
                    return session.createCriteria(Item.class).add(Restrictions.like("itemID", "%" + arg + "%")).list();
                case LastSupplier:
                    return session.createCriteria(Item.class).add(Restrictions.like("lastSupplierId", "%" + arg + "%")).list();
                case Name:
                    return session.createCriteria(Item.class).add(Restrictions.like("name", "%" + arg + "%")).list();
                default:
                    return null;
            }
        }
    }

    public static boolean matchItemValues(String id, String name) {
        return getFilterdItem(ItemColumns.ItemID, id).get(0).getName().equals(name);
    }

    public static void setItemInSalesOrder(FrmSaleOrderController aThis, ItemColumns itemColumns, String text) {
        List<Item> list = getFilterdItem(itemColumns, text);
        if (list != null && !list.isEmpty()) {
            Item get = list.get(0);
            Session s = SessionManager.getInstance().getSessionFactory().openSession();
            s.beginTransaction();

            aThis.setItemValue(get.getItemID(),
                    get.getName(),
                    get.getScale().toString(),
                    getRemainQty(get, s),
                    (Date) getLastPOrderDetails(get, s)[0],
                    (double) getLastPOrderDetails(get, s)[1]);
        }
    }

    private static double getRemainQty(Item i, Session s) {
        Object uniqueResult = s.createQuery("select sum(remainQty) from PurchaseOrderItem where item_id='" + i.getId() + "'").uniqueResult();
        return uniqueResult == null ? 0.0 : (double) uniqueResult;
    }

    private static Object[] getLastPOrderDetails(Item i, Session s) {
        List list = getPOIforItem(i, s);
        if (list.isEmpty()) {
            return new Object[]{null, 0.0};
        }
        PurchaseOrderItem p = (PurchaseOrderItem) list.get(0);
        return new Object[]{p.getExpDate(), p.getPurchasePrice()};
    }

    public static List<PurchaseOrderItem> getPOIforItem(Item i, Session s) {
        return  s.createQuery("FROM PurchaseOrderItem Where item_id=? AND remainQty > 0 ORDER BY id DESC")
                .setLong(0, i.getId())
                .list();
    }

    public static enum ItemColumns {
        ItemID, Name, LastSupplier
    }
}
