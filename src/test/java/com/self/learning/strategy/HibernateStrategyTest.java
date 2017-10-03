package com.self.learning.strategy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

@SuppressWarnings("Duplicates")
public class HibernateStrategyTest {

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
        Customers customers = new Customers();
        customers.setName("weizhiming");

        Orders orders1 = new Orders();
        orders1.setName("Thinkpad T470");

        Orders orders2 = new Orders();
        orders2.setName("MacBook Pro");

        orders1.setCustomer(customers);
        orders2.setCustomer(customers);

        customers.getOrders().add(orders1);
        customers.getOrders().add(orders2);

        session.save(customers);
        session.save(orders1);
        session.save(orders2);
    }

    @Test
    public void testClassLevelStrategy() {
        Customers customers = (Customers) session.load(Customers.class, 1);
        System.out.println(customers.getClass().getName());
    }

    @Test
    public void testSetBatchSize() {
        //List<Customers> customers = session.createQuery("from Customers").list();
        //for (Customers customer : customers) {
        //    if (customer != null) {
        //        System.out.println(customer.getOrders().size());
        //    }
        //}

        Customers customers = (Customers) session.get(Customers.class, 1);
        if (customers != null) {
            System.out.println(customers.getOrders().size());
        }
    }

    @Test
    public void testOneToManyOrManyToManyFetch() {
        //Customers customers = (Customers) session.get(Customers.class, 1);
        //System.out.println(customers.getName());
        //System.out.println(customers.getOrders().size());
        List<Customers> customers = session.createQuery("from Customers ").list();
        for (Customers customer : customers) {
            System.out.println(customer.getOrders().size());
        }
    }

    @Test
    public void testManyToOneOrOneToOneFetch() {
        Orders orders = (Orders) session.get(Orders.class, 1);
        System.out.println(orders.getName());
        //List<Orders> orders = session.createQuery("from Orders ").list();
        //for (Orders order : orders) {
        //    System.out.println(order.getName());
        //}
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

}
