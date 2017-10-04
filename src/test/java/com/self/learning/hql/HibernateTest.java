package com.self.learning.hql;

import com.self.learning.entity.Department;
import com.self.learning.entity.Employee;
import org.hibernate.Query;
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

@SuppressWarnings({"Duplicates", "unchecked"})
public class HibernateTest {

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
    public void testHql() {
        String hql = "from Employee e where e.salary > ? and email like ? and dept = ? order by e.salary";
        Department department = new Department();
        department.setId(80);
        Query query = session.createQuery(hql);
        List<Employee> employees = query.setFloat(0, 7000)
                                        .setString(1,"%E%")
                                        .setEntity(2,department)
                                        .list();
        System.out.println(employees.size());
    }

    @Test
    public void testHqlNamedParameter() {
        String hql = "from Employee e where e.salary > :salary and email like :email";
        Query query = session.createQuery(hql);
        List<Employee> employees = query.setFloat("salary", 7000).setString("email", "%E").list();
        System.out.println(employees.size());
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

}
