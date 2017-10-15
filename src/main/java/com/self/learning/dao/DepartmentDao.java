package com.self.learning.dao;

import com.self.learning.hql.Department;
import com.self.learning.utils.HibernateUtils;
import org.hibernate.Session;

public class DepartmentDao {

    public void save(Department dept) {
        Session session = HibernateUtils.getInstance().getSession();
        System.out.println(session.hashCode());
        session.save(dept);
    }
}
