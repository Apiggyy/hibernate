package com.self.learning.manyToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

@SuppressWarnings("Duplicates")
public class HibernateManyToManyTest {

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
        Categorys categorys1 = new Categorys();
        categorys1.setName("C-AA");
        Categorys categorys2 = new Categorys();
        categorys2.setName("C-BB");

        Items items1 = new Items();
        items1.setName("I-AA");
        Items items2 = new Items();
        items2.setName("I-BB");

        categorys1.getItems().add(items1);
        categorys1.getItems().add(items2);
        categorys2.getItems().add(items1);
        categorys2.getItems().add(items2);

        items1.getCategorys().add(categorys1);
        items1.getCategorys().add(categorys2);
        items2.getCategorys().add(categorys1);
        items2.getCategorys().add(categorys2);

        session.save(items1);
        session.save(items2);
        session.save(categorys1);
        session.save(categorys2);
    }

    @Test
    public void testGet() {
        Categorys categorys = (Categorys) session.get(Categorys.class, 1);
        System.out.println(categorys.getName());

        Set<Items> items = categorys.getItems();
        System.out.println(items.size());
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
