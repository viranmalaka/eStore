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
import org.hibernate.criterion.Projections;
import view.UIControllCommon;
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

    public static long getNextIndex() {
        Session session = SessionManager.getInstance().getSession();
        Criteria criteria = session.createCriteria(Customer.class);
        Object maxID = criteria.setProjection(Projections.max("id")).uniqueResult();
        if (maxID == null) {
            return 1;
        }
        return Long.parseLong(maxID + "") + 1;
    }

    public static void openAddNewCustomerWindow() {
        String newCustomerId = CommonControllers.convertIndex(getNextIndex(), 'C');
        ((FrmCustomerController) UIControllCommon.getInstance().
                openFXMLWindow("customers/frmCustomer.fxml",
                        Modality.APPLICATION_MODAL, false,"Add New Customer")).initData(newCustomerId);
    }
    
    public static void openEditCustomerWindow(){
        
    }
}
