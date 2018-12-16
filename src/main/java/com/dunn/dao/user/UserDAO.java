package com.dunn.dao.user;

import com.dunn.model.user.WoodulikeUser;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public WoodulikeUser createWoodulikeUser(WoodulikeUser woodulikeUser) {
        if(findWoodulikeUserByUsername(woodulikeUser.getUsername()) == null){
            woodulikeUser.setPassword(passwordEncoder.encode(woodulikeUser.getPassword()));
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

    @Override
    public WoodulikeUser findWoodulikeUserByEmailAddress(String emailAddress){
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<WoodulikeUser> query = builder.createQuery(WoodulikeUser.class);
        Root<WoodulikeUser> root = query.from(WoodulikeUser.class);
        query.select(root);
        query.where(builder.equal(root.get("emailAddress"), emailAddress));
        WoodulikeUser foundUser = null;
        try {
            foundUser = sessionFactory.getCurrentSession().createQuery(query).uniqueResult();
        } catch(NonUniqueObjectException exception){

        }
        return foundUser;
    }

    @Override
    public boolean isUsernameTaken(String username){
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<WoodulikeUser> query = builder.createQuery(WoodulikeUser.class);
        Root<WoodulikeUser> root = query.from(WoodulikeUser.class);
        query.select(root.get("username"));
        query.where(builder.equal(root.get("username"),username));
        List<WoodulikeUser> results = sessionFactory.getCurrentSession().createQuery(query).list();
        return results.size() > 0;
    }

    @Override
    public boolean isEmailRegistered(String emailAddress){
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<WoodulikeUser> query = builder.createQuery(WoodulikeUser.class);
        Root<WoodulikeUser> root = query.from(WoodulikeUser.class);
        query.select(root.get("emailAddress"));
        query.where(builder.equal(root.get("emailAddress"), emailAddress));
        List<WoodulikeUser> results = sessionFactory.getCurrentSession().createQuery(query).list();
        return results.size() > 0;
    }

    public boolean isUsernameAndPasswordCorrect(String username, String password){
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<WoodulikeUser> query = builder.createQuery(WoodulikeUser.class);
        Root<WoodulikeUser> root = query.from(WoodulikeUser.class);
        query.select(root);
        query.where(builder.equal(root.get("username"), username));
        WoodulikeUser result = sessionFactory.getCurrentSession().createQuery(query).uniqueResult();
        if(result != null && passwordEncoder.matches(password, result.getPassword())){
            return true;
        }
        return false;
    }

}
