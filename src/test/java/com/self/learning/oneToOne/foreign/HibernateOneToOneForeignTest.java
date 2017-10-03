package com.self.learning.oneToOne.foreign;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("Duplicates")
public class HibernateOneToOneForeignTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
                .buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @Test
    public void testSave() {
        Manager manager = new Manager();
        manager.setMgrName("MGR-AA");

        Deptment deptment = new Deptment();
        deptment.setDeptName("DEPT-AA");

        deptment.setManager(manager);
        manager.setDeptment(deptment);

        session.save(manager);
        session.save(deptment);
    }

    @Test
    public void testGet() {
        Deptment deptment = (Deptment) session.get(Deptment.class, 2);
        System.out.println(deptment.getDeptName());

        Manager manager = deptment.getManager();
        System.out.println(manager.getMgrName());
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
