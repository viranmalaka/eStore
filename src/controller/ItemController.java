/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. */
package controller;

import hibernate.HibernateController;
import hibernate.SessionManager;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Item;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import view.MainWindowController;
import view.UICommonController;
import view.items.FrmItemController;
import view.orders.FrmPurchaseOrderController;

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
        if (list != null && list.size() > 0) {
            for (Item item : list) {
                controller.tblItemAddItem(item.getItemID(),
                        item.getName(),
                        item.getScale().toString(),
                        item.getQuantity(),
                        item.getExpDate() == null ? "" : item.getExpDate().toString(),
                        item.getLastPurchasePrice(),
                        item.getSellingPrice(),
                        item.getLastSupplier() == null ? "" : item.getLastSupplier().getSupplierID());
            }
        }
        controller.tblItemSetItems();
    }

    public static void setItemInPurchaseOrder(FrmPurchaseOrderController aThis, ItemColumns itemColumns, String text) {
        List<Item> list = getFilterdItem(itemColumns, text);
        if (list != null) {
            Item get = list.get(0);
            aThis.setItemValue(get.getItemID(),
                    get.getName(),
                    get.getScale().toString());
        }
    }

    public  static List<Item> getFilterdItem(ItemColumns itemColumns, String arg) {
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

    public static enum ItemColumns {
        ItemID, Name, LastSupplier
    }
}
