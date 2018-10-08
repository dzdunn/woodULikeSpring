package com.dunn.dao.user;

import com.dunn.model.user.WoodulikeUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserDAO implements IUserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public WoodulikeUser findByUserName(String username){
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<WoodulikeUser> criteria = cb.createQuery(WoodulikeUser.class);
        Root<WoodulikeUser> root = criteria.from(WoodulikeUser.class);
        criteria.select(root);
        criteria.where(cb.equal(root.get("username"), username));
        return sessionFactory.getCurrentSession().createQuery(criteria).uniqueResult();
    }


}
