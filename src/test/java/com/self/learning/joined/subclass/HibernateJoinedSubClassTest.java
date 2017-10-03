package com.self.learning.joined.subclass;

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
public class HibernateJoinedSubClassTest {

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
        Person person = new Person();
        person.setName("weizhiming");
        person.setAge(32);
        session.save(person);

        Student student = new Student();
        student.setName("xieyonghong");
        student.setAge(30);
        student.setSchool("亚信");
        session.save(student);
    }

    @Test
    public void testQuery() {
        List<Person> personList = session.createQuery("from Person ").list();
        System.out.println(personList.size());

        List<Student> students = session.createQuery("from Student ").list();
        System.out.println(students.size());
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

}
