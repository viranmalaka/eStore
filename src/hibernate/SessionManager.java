/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Malaka
 */
public class SessionManager {

    private static SessionManager instance;
    private SessionFactory sessionFactory;

    private SessionManager() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static SessionManager getInstance(){
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}
