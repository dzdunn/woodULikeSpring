package com.dunn.util.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class HibernateHelper {

    @Autowired
    private static SessionFactory sessionFactory;

    private HibernateHelper(){
        throw new UnsupportedOperationException();
    }

    //needs work
    public static <T extends Class> CriteriaQuery<T> createQuery(Class<T> t){
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(t);
        return criteriaQuery;
    }

    public static <T extends Class<T>> Root<T> getRoot(T t){
        return createQuery(t).from(t);
    }

}
