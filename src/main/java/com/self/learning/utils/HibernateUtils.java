package com.self.learning.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtils {

    private SessionFactory sessionFactory;
    private Session session;

    private HibernateUtils() {
    }

    private static HibernateUtils instance = new HibernateUtils();

    public static HibernateUtils getInstance() {
        return instance ;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
                    .buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    public Session getSession() {
        return getSessionFactory().getCurrentSession();
    }
}
