package com.dunn.dao;

import com.dunn.model.WoodProject;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public class PostgresqlDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void createWoodProject(WoodProject woodProject){
        sessionFactory.getCurrentSession().saveOrUpdate(woodProject);
    }

}
