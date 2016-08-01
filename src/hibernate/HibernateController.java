/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import logger.LogController;
import org.apache.logging.log4j.Level;
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
            session.persist(obj);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            // add log
            LogController.log(Level.ERROR, e.toString());
            return false;
        }
        return true;
    }

}
