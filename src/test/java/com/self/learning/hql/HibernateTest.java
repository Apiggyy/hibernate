package com.self.learning.hql;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
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
                .setString(1, "%E%")
                .setEntity(2, department)
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

    @Test
    public void testPageQuery() {
        final int pageNo = 5;
        final int pageSize = 10;
        List<Employee> employees = session.createQuery("from Employee ")
                .setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void testNamedQuery() {
        Query salaryEmps = session.getNamedQuery("salaryEmps");
        salaryEmps.setFloat("minSal", 7000);
        salaryEmps.setFloat("maxSal", 10000);
        List<Employee> emps = salaryEmps.list();
        for (Employee emp : emps) {
            System.out.println(emp);
            System.out.println(emp.getDept().getName());
        }
    }

    @Test
    public void testFieldQuery() {
        String hql = "select e.salary,e.email,e.dept from Employee e where e.dept = :dept";
        Query query = session.createQuery(hql);
        Department dept = new Department();
        dept.setId(80);
        List<Object[]> emps = query.setEntity("dept", dept).list();
        for (Object[] emp : emps) {
            System.out.println(Arrays.asList(emp));
        }
    }

    @Test
    public void testFieldQuery1() {
        String hql = "select new Employee(e.salary,e.email,e.dept) from Employee e where e.dept = :dept";
        Query query = session.createQuery(hql);
        Department dept = new Department();
        dept.setId(80);
        List<Employee> emps = query.setEntity("dept", dept).list();
        for (Employee emp : emps) {
            System.out.println(emp.getId() + "," + emp.getSalary() + "," + emp.getEmail() + "," + emp.getDept());
        }
    }

    @Test
    public void testGroupByQuery() {
        String hql = "select e.dept.id,e.dept.name,min(e.salary),max(e.salary) from Employee e " +
                "group by e.dept.id,e.dept.name having min(e.salary) > :minSal";
        List<Object[]> emps = session.createQuery(hql).setFloat("minSal", 7000).list();
        for (Object[] emp : emps) {
            System.out.println(Arrays.asList(emp));
        }
    }

    @Test
    public void testLeftJoinFetch() {
        String hql = "select distinct d from Department d left join fetch d.emps";
        List<Department> depts = session.createQuery(hql).list();
        System.out.println(depts.size());
        for (Department dept : depts) {
            System.out.println(dept);
        }
    }

    @Test
    public void testLeftJoin() {
        String hql = "select distinct d from Department d left join d.emps";
        List<Department> depts = session.createQuery(hql).list();
        for (Department dept : depts) {
            System.out.println(dept.getName() + " : " + dept.getEmps().size());
        }
    }

    @Test
    public void testQBC() {
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.add(Restrictions.like("email","D%"));
        criteria.add(Restrictions.gt("salary",7000F));
        List<Employee> employees = criteria.list();
        System.out.println(employees.size());
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void testQBC2() {
        Criteria criteria = session.createCriteria(Employee.class);

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("name","D%", MatchMode.ANYWHERE));
        Department dept = new Department();
        dept.setId(80);
        conjunction.add(Restrictions.eq("dept",dept));

        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.gt("salary",7000F));
        disjunction.add(Restrictions.isNotNull("email"));

        criteria.add(conjunction);
        criteria.add(disjunction);
        List<Employee> employees = criteria.list();
        System.out.println(employees.size());
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void testQBC3() {
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.setProjection(Projections.max("salary"));
        System.out.println(criteria.uniqueResult());
    }

    @Test
    public void testQBC4() {
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.addOrder(Order.desc("salary")).addOrder(Order.asc("name"));

        int pageNo = 5;
        int pageSize = 10;
        List<Employee> employees = criteria.setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
        System.out.println(employees.size());
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void testUpdate() {
        String hql = "delete from Department d where d.id = :id";
        session.createQuery(hql)
                .setInteger("id", 8000)
                .executeUpdate();
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

}
