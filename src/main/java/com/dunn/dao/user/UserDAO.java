package com.dunn.dao.user;

import com.dunn.model.user.WoodulikeUser;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional
@Repository
public class UserDAO  implements IUserDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public WoodulikeUser createWoodulikeUser(WoodulikeUser woodulikeUser) {
        if(findWoodulikeUserByUsername(woodulikeUser.getUsername()) == null){
            sessionFactory.getCurrentSession().save(woodulikeUser);
        }
        //should throw exception if user exists
        return woodulikeUser;
    }

    @Override
    public WoodulikeUser findWoodulikeUser(WoodulikeUser woodulikeUser) {
        return null;
    }

    @Override
    public WoodulikeUser updateWoodulikeUser(WoodulikeUser woodulikeUser) {
        return null;
    }

    @Override
    public void deleteWoodulikeUser(WoodulikeUser woodulikeUser) {

    }

    @Override
    public List<WoodulikeUser> allWoodulikeUsers() {
        return null;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return null;
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {

    }

    @Override
    public WoodulikeUser findWoodulikeUserByUsername(String username){
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<WoodulikeUser> query = builder.createQuery(WoodulikeUser.class);
        Root<WoodulikeUser> root = query.from(WoodulikeUser.class);
        query.select(root);
        query.where(builder.equal(root.get("username"), username));
        WoodulikeUser foundUser = null;
        try {
            foundUser = sessionFactory.getCurrentSession().createQuery(query).uniqueResult();
        } catch(NonUniqueObjectException exception){

        }
        return foundUser;
    }
}
