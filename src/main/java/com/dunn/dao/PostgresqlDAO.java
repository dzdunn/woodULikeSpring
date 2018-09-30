package com.dunn.dao;

import com.dunn.model.WoodProject;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@Transactional
public class PostgresqlDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void createWoodProject(WoodProject woodProject){
        sessionFactory.getCurrentSession().saveOrUpdate(woodProject);
    }

}
