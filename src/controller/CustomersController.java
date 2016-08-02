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
import model.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import view.MainWindowController;
import view.UICommonController;
import view.customers.FrmCustomerController;

/**
 *
 * @author Malaka
 */
public class CustomersController {

    public static boolean saveCustomer(String customerID, String fName, String lName, String address, String telephone) {
        Customer customer = new Customer(customerID, fName, lName, telephone, address);
        return HibernateController.saveObject(customer);
    }

    public static boolean updateCustomer(String customerID, String fName, String lName, String address, String telephone) {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Customer.class);
        Customer customer = (Customer) criteria.add(Restrictions.eq("customerID", customerID)).uniqueResult();

        customer.setAddress(address);
        customer.setFirstName(fName);
        customer.setLastName(lName);
        customer.setTelephone(telephone);

        session.update(customer);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public static long getNextIndex() {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Customer.class);
        Object maxID = criteria.setProjection(Projections.max("id")).uniqueResult();
        session.close();
        if (maxID == null) {
            return 1;
        }
        return Long.parseLong(maxID + "") + 1;
    }

    public static void openAddNewCustomerWindow() {
        String newCustomerId = CommonControllers.convertIndex(getNextIndex(), 'C');
        
        FXMLLoader createFXML = UICommonController.getInstance().createFXML("customers/frmCustomer.fxml");
        Stage stage = UICommonController.getInstance().getStage(createFXML);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add New Customer");
        ((FrmCustomerController)createFXML.getController()).initData(newCustomerId);
        
        stage.showAndWait();
    }

    public static void openEditCustomerWindow(String id) {

        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Customer.class);
        Customer customer = (Customer) criteria.add(Restrictions.eq("customerID", id)).uniqueResult();

        FXMLLoader createFXML = UICommonController.getInstance().createFXML("customers/frmCustomer.fxml");
        Stage stage = UICommonController.getInstance().getStage(createFXML);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Edit Customer");
        ((FrmCustomerController)createFXML.getController()).initData(
                        customer.getCustomerID(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getTelephone(),
                        customer.getAddress());
        session.close();
        stage.showAndWait();
    }

    public static void refreshTable(MainWindowController controller, PersonColumns col, String arg) {
        List<Customer> list = getFilterdCustomers(arg, col);

        if (list != null && list.size() > 0) {
            for (Customer customer : list) {
                controller.tblCustomerAddItem(customer.getCustomerID(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getAddress(),
                        customer.getTelephone());
            }
        }
        controller.tblCustomerSetItems();
    }

    public static List<Customer> getFilterdCustomers(String arg, PersonColumns p) {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Customer> list = null;

        switch (p) {
            case Address:
                list = session.createCriteria(Customer.class).add(Restrictions.like("address", "%" + arg + "%")).list();
                break;
            case CustomerID:
                list = session.createCriteria(Customer.class).add(Restrictions.like("customerID", "%" + arg + "%")).list();
                break;
            case Name:
                list = session.createCriteria(Customer.class)
                        .add(Restrictions.or(Restrictions.like("firstName", "%" + arg + "%").ignoreCase(),
                                Restrictions.like("lastName", "%" + arg + "%").ignoreCase())).list();
                break;
            case Telephone:
                list = session.createCriteria(Customer.class).add(Restrictions.like("telephone", "%" + arg + "%")).list();
                break;
        }
        session.close();
        return list;
    }

    public static ArrayList getFilteredListWithProjection(String arg, PersonColumns p) {
        List<Customer> filterdCustomers = getFilterdCustomers(arg, p);
        ArrayList<String> res = new ArrayList<String>();
        for (Customer filterdCustomer : filterdCustomers) {
            switch (p) {
                case Address:
                    res.add(filterdCustomer.getAddress());
                    break;
                case CustomerID:
                    res.add(filterdCustomer.getCustomerID());
                    break;
                case Name:
                    res.add(filterdCustomer.getFirstName() + " " + filterdCustomer.getLastName());
                    break;
                case Telephone:
                    res.add(filterdCustomer.getTelephone());
                    break;
            }
        }
        return res;
    }
    

    public static enum PersonColumns {
        CustomerID, Name, Address, Telephone
    };
}
