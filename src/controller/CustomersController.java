/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.HibernateController;
import hibernate.SessionManager;
import java.util.List;
import model.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;

/**
 *
 * @author Malaka
 */
public class CustomersController {
    
    public static boolean saveCustomer(int id, String fName, String lName, String address, String telephone){

        Customer customer = new Customer(id, fName, lName, telephone, address);
        boolean saveObject = HibernateController.saveObject(customer);
        return true;
    }
    
    public static long getNextIndex(){
        Session session = SessionManager.getInstance().getSession();
        
        Criteria criteria = session.createCriteria( Customer.class);
        Object maxID = criteria.setProjection(Projections.max("customerID")).uniqueResult();
        
        return Long.parseLong(maxID + "") + 1;
    }
}
