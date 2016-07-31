/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.HibernateController;
import hibernate.SessionManager;
import java.util.List;
import javafx.stage.Modality;
import model.Supplier;
import model.Supplier;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import view.MainWindowController;
import view.UICommonController;
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
        ((FrmSupplierController) UICommonController.getInstance().
                openFXMLWindow("suppliers/frmSupplier.fxml",
                        Modality.APPLICATION_MODAL, false, "Add New Supplier")).initData(newSupplierId);
    }
    
    public static void openEditSupplierWindow(String id) {

        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Supplier.class);
        Supplier supplier = (Supplier) criteria.add(Restrictions.eq("supplierID", id)).uniqueResult();

        ((FrmSupplierController) UICommonController.getInstance().
                openFXMLWindow("suppliers/frmSupplier.fxml",
                        Modality.APPLICATION_MODAL, false, "Add New Supplier")).initData(
                        supplier.getSupplierID(),
                        supplier.getFirstName(),
                        supplier.getLastName(),
                        supplier.getTelephone(),
                        supplier.getAddress());
        session.close();
    }

    public static void refreshTable(MainWindowController controller, PersonColumns personColumns, String arg) {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Supplier> list = null;
        switch (personColumns) {
            case Address:
                list = session.createCriteria(Supplier.class).add(Restrictions.like("address", "%" + arg + "%")).list();
                break;
            case SupplierID:
                list = session.createCriteria(Supplier.class).add(Restrictions.like("supplierID", "%" + arg + "%")).list();
                break;
            case Name:
                list = session.createCriteria(Supplier.class)
                        .add(Restrictions.or(Restrictions.like("firstName", "%" + arg + "%").ignoreCase(),
                                Restrictions.like("lastName", "%" + arg + "%").ignoreCase())).list();
                break;
            case Telephone:
                list = session.createCriteria(Supplier.class).add(Restrictions.like("telephone", "%" + arg + "%")).list();
                break;
        }

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
        session.close();
    }


    public static enum PersonColumns {
        SupplierID, Name, Address, Telephone
    };
}
