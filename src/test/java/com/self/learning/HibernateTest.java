package com.self.learning;

import com.self.learning.entity.News;
import com.self.learning.entity.Pay;
import com.self.learning.entity.Worker;
import com.self.learning.mayToOne.Customers;
import com.self.learning.mayToOne.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

@SuppressWarnings("Duplicates")
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

    /**
     * 测试Session缓存
     */
    @Test
    public void testSessionCache() {
        News news = (News) session.get(News.class, 2);
        System.out.println(news);

        News news1 = (News) session.get(News.class, 2);
        System.out.println(news1);

        //News news = new News();
        //News.setTitle("test");
        //News.setAuthor("weizhiming");
        //session.save(News);
    }

    /**
     * 测试Session的Flush方法  缓存 -> 数据库
     */
    @Test
    public void testSessionFlush() throws Exception {
        News news = (News) session.get(News.class, 1);
        news.setAuthor("wei");

        News news2 = (News) session.createCriteria(News.class).uniqueResult();
        System.out.println(news2);
    }

    /**
     * 测试Session的Flush方法  缓存 -> 数据库
     */
    @Test
    public void testSessionFlush2() {
        News news = new News("Java", "Sun", new Date());
        session.save(news);
        System.out.println(news);
    }

    /**
     * 测试缓存刷新   数据库 -> 缓存
     */
    @Test
    public void testSessionRefresh() {
        News news = (News) session.get(News.class, 1);
        System.out.println(news);
        session.refresh(news);
        System.out.println(news);
    }

    /**
     * 测试session.clear() 缓存清理
     */
    @Test
    public void testSessionClear() {
        News news = (News) session.get(News.class, 1);
        session.clear();
        News news1 = (News) session.get(News.class, 1);
    }

    @Test
    public void testSave() {
        News news = new News("Java", "Sun", new Date());
        news.setId(10000);
        System.out.println(news);
        session.save(news);

        System.out.println(news);
    }

    @Test
    public void testPersist() {
        News news = new News("C++", "Microsoft", new Date());
        news.setId(100);
        System.out.println(news);

        session.persist(news);
        System.out.println(news);
    }

    @Test
    public void testDoWork() {
        session.doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                System.out.println(connection);
            }
        });
    }

    @Test
    public void testGet() {
        News news = (News) session.get(News.class, 3);
        System.out.println(news.getClass().getName());
        //System.out.println(news);
    }

    @Test
    public void testLoad() {
        News news = (News) session.load(News.class, 3);
        System.out.println(news.getClass().getName());
        //System.out.println(news);
    }

    @Test
    public void testUpdate() {
        News news = (News) session.get(News.class, 3);
        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        //news.setAuthor("Oracle");
        News news2 = (News) session.get(News.class, 3);
        session.update(news);
        System.out.println(news);
    }

    @Test
    public void testUpdate2() {
        News news = (News) session.get(News.class, 3);
        transaction.commit();
        session.close();

        news.setId(100);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        session.update(news);
    }

    @Test
    public void testDynamicUpdate() {
        News news = (News) session.get(News.class, 3);
        news.setAuthor("Oracle");
    }

    @Test
    public void testGeneratorIdentifier() {
        News news = new News("Java", "Oracle", new Date());
        session.save(news);
    }

    @Test
    public void testProperty() {
        News news = (News) session.get(News.class, 1);
        news.setTitle("Hibernate");
        System.out.println(news);
    }

    @Test
    public void testComponent() {
        Worker worker = new Worker();
        worker.setName("xieyonghong");
        Pay pay = new Pay();
        pay.setMonthlySalary(10000);
        pay.setYearlySalary(150000);
        pay.setVocationDays(5);
        worker.setPay(pay);

        session.save(worker);
    }

    //@Test
    //public void testManyToOneSave() {
    //    Customers customers = new Customers();
    //    customers.setName("weizhiming");
    //
    //    Orders orders1 = new Orders();
    //    orders1.setName("thinkpad T460p");
    //    orders1.setCustomer(customers);
    //
    //    Orders orders2 = new Orders();
    //    orders2.setName("MacBookPro");
    //    orders2.setCustomer(customers);
    //
    //    session.save(customers);
    //    session.save(orders1);
    //    session.save(orders2);
    //}

    @Test
    public void testManyToOneUpdate() {
        Orders orders = (Orders) session.get(Orders.class, 1);
        orders.getCustomer().setName("xieyonghong");
        System.out.println(orders);
    }

    @Test
    public void testManyToOneGet() {
        Orders orders = (Orders) session.get(Orders.class, 1);
        System.out.println(orders);

        //延迟加载
        Customers customers = orders.getCustomer();
        System.out.println(customers.getClass().getName());
        System.out.println(customers.getName());
    }

    @Test
    public void testManyToOneDelete() {
        Customers customers = (Customers) session.get(Customers.class, 1);
        session.delete(customers);
    }

    @Test
    public void testManyToOneBothSave() {
        com.self.learning.manyToOneBoth.Customers customers = new com.self.learning.manyToOneBoth.Customers();
        customers.setName("weizhiming");

        com.self.learning.manyToOneBoth.Orders orders1 = new com.self.learning.manyToOneBoth.Orders();
        orders1.setName("thinkpad T460p");
        orders1.setCustomer(customers);

        com.self.learning.manyToOneBoth.Orders orders2 = new com.self.learning.manyToOneBoth.Orders();
        orders2.setName("MacBookPro");
        orders2.setCustomer(customers);

        customers.getOrders().add(orders1);
        customers.getOrders().add(orders2);

        session.save(customers);
        session.save(orders1);
        session.save(orders2);
    }

    @Test
    public void testOneToManyBothGet() {
        com.self.learning.manyToOneBoth.Customers customers = (com.self.learning.manyToOneBoth.Customers) session.get
                (com.self.learning.manyToOneBoth.Customers
                .class, 1);
        System.out.println(customers.getName());
        //hibernate内置的集合类型，该类型有延迟加载和存放代理对象的功能
        System.out.println(customers.getOrders().getClass().getName());
    }

    @Test
    public void testManyToOneBothDelete() {
        com.self.learning.manyToOneBoth.Customers customers = (com.self.learning.manyToOneBoth.Customers) session.get
                (com.self.learning.manyToOneBoth.Customers
                .class, 1);
        session.delete(customers);
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

}
