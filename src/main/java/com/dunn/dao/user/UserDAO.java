package com.dunn.dao.user;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UserDAO implements IUserDAO {

    @Autowired
    private SessionFactory sessionFactory;


}
