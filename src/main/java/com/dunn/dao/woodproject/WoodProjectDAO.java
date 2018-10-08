package com.dunn.dao.woodproject;

import com.dunn.dao.woodproject.IWoodProjectDAO;
import com.dunn.model.WoodProject;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class WoodProjectDAO implements IWoodProjectDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Value("${upload.location}")
    private String uploadLocation;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public WoodProject createWoodProject(WoodProject woodProject) {
        sessionFactory.getCurrentSession().saveOrUpdate(woodProject);
        return woodProject;
    }

    @Override
    public WoodProject findWoodProject(WoodProject woodProject) {
        return sessionFactory.getCurrentSession().get(WoodProject.class, woodProject.getId());
    }

    @Override
    public WoodProject findWoodProjectByTitle(String title){
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<WoodProject> criteria = builder.createQuery(WoodProject.class);
        Root<WoodProject> root = criteria.from(WoodProject.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("title"), title));
        return sessionFactory.getCurrentSession().createQuery(criteria).uniqueResult();
    }

    @Override
    public WoodProject updateWoodProject(WoodProject woodProject) {
        sessionFactory.getCurrentSession().saveOrUpdate(woodProject);
        return woodProject;
    }

    @Override
    public void deleteWoodProject(WoodProject woodProject) {
        sessionFactory.getCurrentSession().delete(woodProject);
    }

    @Override
    public List<WoodProject> allWoodProjects() {
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<WoodProject> criteria = builder.createQuery(WoodProject.class);
        Root<WoodProject> root = criteria.from(WoodProject.class);
        criteria.select(root);
        return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
    }

//    @Transactional
//    public Image readeImage(WoodProject woodProject){
//
//        List<Image> images = woodProject.getImages();
//        byte[] image = images.get(0).getBlob();
//
//        ImageIO.
//
//        Image img = new Image();
//        img.setImageName("Test");
//        img.setPath("test");
//
//        try{
//            ByteInputStream byteInputStream = new ByteInputStream();
//            byteInputStream.read(image);
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//
//    }
}
