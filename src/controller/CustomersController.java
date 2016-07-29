/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.HibernateController;
import hibernate.SessionManager;
import javafx.stage.Modality;
import model.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
    
    public static boolean updateCustomer(String customerID,String fName, String lName, String address, String telephone){
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
        ((FrmCustomerController) UICommonController.getInstance().
                openFXMLWindow("customers/frmCustomer.fxml",
                        Modality.APPLICATION_MODAL, false,"Add New Customer")).initData(newCustomerId);
    }
    
    public static void openEditCustomerWindow(String id){
        
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Customer.class);
        Customer customer = (Customer) criteria.add(Restrictions.eq("customerID", id)).uniqueResult();
        
        ((FrmCustomerController) UICommonController.getInstance().
                openFXMLWindow("customers/frmCustomer.fxml",
                        Modality.APPLICATION_MODAL, false,"Add New Customer")).initData(
                                customer.getCustomerID(), 
                                customer.getFirstName(), 
                                customer.getLastName(), 
                                customer.getTelephone(), 
                                customer.getAddress());
    }
}
