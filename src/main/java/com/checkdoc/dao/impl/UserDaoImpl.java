package com.checkdoc.dao.impl;

import com.checkdoc.dao.UserDao;
import com.checkdoc.domain.User;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import static org.hibernate.criterion.Restrictions.eq;


@Repository
@Transactional
public class UserDaoImpl extends GenericDAOImpl<User, Long> implements UserDao {


    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public User findByUserName(String userName) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(eq("userName", userName));
        return (User) criteria.uniqueResult();
    }
}