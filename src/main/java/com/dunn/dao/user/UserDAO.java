package com.dunn.dao.user;

import com.dunn.model.user.WoodulikeUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Repository
@Transactional
public class UserDAO implements IUserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public WoodulikeUser findByUserName(String username){
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<WoodulikeUser> criteria = cb.createQuery(WoodulikeUser.class);
        Root<WoodulikeUser> root = criteria.from(WoodulikeUser.class);
        criteria.select(root);
        criteria.where(cb.equal(root.get("username"), username));
        return sessionFactory.getCurrentSession().createQuery(criteria).uniqueResult();
    }

    @Override
    public boolean register(WoodulikeUser woodulikeUser){

        //if(findByUserName(woodulikeUser.getUsername()) == null){
            sessionFactory.getCurrentSession().save(woodulikeUser);
            return true;
        //}
        //return false;
    }


}
