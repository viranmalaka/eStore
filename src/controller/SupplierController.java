/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.HibernateController;
import hibernate.SessionManager;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Supplier;
import model.Supplier;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import view.MainWindowController;
import view.UICommonController;
import view.orders.FrmPurchaseOrderController;
import view.suppliers.FrmSupplierController;
import view.suppliers.FrmSupplierController;

/**
 *
 * @author Malaka
 */
public class SupplierController {

    public static boolean saveSupplier(String supplierID, String fName, String lName, String address, String telephone) {
        Supplier supplier = new Supplier(supplierID, fName, lName, telephone, address);
        return HibernateController.saveObject(supplier);
    }

    public static boolean updateSupplier(String supplierID, String fName, String lName, String address, String telephone) {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Supplier.class);
        Supplier supplier = (Supplier) criteria.add(Restrictions.eq("supplierID", supplierID)).uniqueResult();

        supplier.setAddress(address);
        supplier.setFirstName(fName);
        supplier.setLastName(lName);
        supplier.setTelephone(telephone);

        session.update(supplier);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public static long getNextIndex() {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Supplier.class);
        Object maxID = criteria.setProjection(Projections.max("id")).uniqueResult();
        session.close();
        if (maxID == null) {
            return 1;
        }
        return Long.parseLong(maxID + "") + 1;
    }

    public static void openAddNewSupplierWindow() {
        String newSupplierId = CommonControllers.convertIndex(getNextIndex(), 'S');

        FXMLLoader createFXML = UICommonController.getInstance().createFXML("suppliers/frmSupplier.fxml");
        Stage stage = UICommonController.getInstance().getStage(createFXML);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add New Supplier");
        ((FrmSupplierController) createFXML.getController()).initData(newSupplierId);

        stage.showAndWait();
    }

    public static void openEditSupplierWindow(String id) {

        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Supplier.class);
        Supplier supplier = (Supplier) criteria.add(Restrictions.eq("supplierID", id)).uniqueResult();

        FXMLLoader createFXML = UICommonController.getInstance().createFXML("suppliers/frmSupplier.fxml");
        Stage stage = UICommonController.getInstance().getStage(createFXML);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Edit Supplier");
        ((FrmSupplierController) createFXML.getController()).initData(
                supplier.getSupplierID(),
                supplier.getFirstName(),
                supplier.getLastName(),
                supplier.getTelephone(),
                supplier.getAddress());
        session.close();
        stage.showAndWait();
    }

    public static void refreshTable(MainWindowController controller, PersonColumns personColumns, String arg) {
        List<Supplier> list = getFilterdSupplier(personColumns, arg);
        if (list != null && list.size() > 0) {
            for (Supplier supplier : list) {
                controller.tblSupplierAddItem(supplier.getSupplierID(),
                        supplier.getFirstName(),
                        supplier.getLastName(),
                        supplier.getAddress(),
                        supplier.getTelephone());
            }
        }
        controller.tblSupplierSetItems();
    }

    public static List<Supplier> getFilterdSupplier(PersonColumns personColumns, String arg) {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            switch (personColumns) {
                case Address:
                    return session.createCriteria(Supplier.class).add(Restrictions.like("address", "%" + arg + "%")).list();
                case SupplierID:
                    return session.createCriteria(Supplier.class).add(Restrictions.like("supplierID", "%" + arg + "%")).list();
                case Name:
                    return session.createCriteria(Supplier.class)
                            .add(Restrictions.or(Restrictions.like("firstName", "%" + arg + "%").ignoreCase(),
                                    Restrictions.like("lastName", "%" + arg + "%").ignoreCase())).list();
                case Telephone:
                    return session.createCriteria(Supplier.class).add(Restrictions.like("telephone", "%" + arg + "%")).list();
                default:
                    return null;
            }
        } finally {
            session.close();
        }
    }
    
    public static void setSupInPurchaseOrder(FrmPurchaseOrderController controller, PersonColumns column, String arg){
        Supplier get = getFilterdSupplier(column, arg).get(0);
        controller.setSupplierValues(get.getSupplierID(), 
                get.getFirstName() + " " + get.getLastName(),
                get.getAddress(),
                get.getTelephone());
    }

    public static enum PersonColumns {
        SupplierID, Name, Address, Telephone
    };
}
