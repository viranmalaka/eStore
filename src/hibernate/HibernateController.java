/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import org.hibernate.Session;

/**
 *
 * @author Malaka
 */
public class HibernateController {
    public static boolean saveObject(Object obj){
        try {
            Session session = SessionManager.getInstance().getSession();
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
