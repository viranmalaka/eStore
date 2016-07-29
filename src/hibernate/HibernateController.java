/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Malaka
 */
public class HibernateController {
    public static boolean saveObject(Object obj){
        try {
            SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            // add log
            return false;
        }
        return true;
    }
    
    
}
