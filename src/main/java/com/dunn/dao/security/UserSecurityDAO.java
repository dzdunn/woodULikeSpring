package com.dunn.dao.security;

import com.dunn.model.user.PasswordResetToken;
import com.dunn.model.user.WoodulikeUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Transactional
@Repository
public class UserSecurityDAO implements IUserSecurityDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void savePasswordResetToken(PasswordResetToken passwordResetToken){
        sessionFactory.getCurrentSession().save(passwordResetToken);
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<PasswordResetToken> query = builder.createQuery(PasswordResetToken.class);
        Root<PasswordResetToken> root =  query.from(PasswordResetToken.class);
        query.select(root);
        query.where(builder.equal(root.get("token"), token));

        return sessionFactory.getCurrentSession().createQuery(query).uniqueResult();

    }

    @Override
    public void changeUserPassword(WoodulikeUser woodulikeUser, String newPassword) {
        woodulikeUser.setPassword(newPassword);
        sessionFactory.getCurrentSession().save(woodulikeUser);
    }
}
