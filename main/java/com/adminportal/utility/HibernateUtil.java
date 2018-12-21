package com.adminportal.utility;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class HibernateUtil {
    private static SessionFactory sessionFactory ;
    static {
    	File f = new File("C:\\Users\\HP\\Workspace2\\adminportal\\src\\main\\resources\\hibernate.cfg.xml");
    	SessionFactory sessionFactory = new Configuration().configure(f).buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
} 