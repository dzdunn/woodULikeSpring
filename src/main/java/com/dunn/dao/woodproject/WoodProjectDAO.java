package com.dunn.dao.woodproject;

import com.dunn.controller.uipaths.resources.ResourceProperties;
import com.dunn.dto.ui.WoodProjectDTO;
import com.dunn.model.user.WoodulikeUser;
import com.dunn.model.woodproject.Image;
import com.dunn.model.woodproject.WoodProject;
import com.dunn.model.woodproject.WoodProject_;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
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
    public WoodProject findWoodProjectById(Long id) {

        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<WoodProject> criteria = builder.createQuery(WoodProject.class);
        Root<WoodProject> woodProjectRoot = criteria.from(WoodProject.class);
        criteria.where(builder.equal(woodProjectRoot.get(WoodProject_.id), id));

        Fetch<WoodProject, WoodulikeUser> woodProjectUser = woodProjectRoot.fetch(WoodProject_.woodulikeUser);
        Fetch<WoodProject, Image> woodProjectImages = woodProjectRoot.fetch(WoodProject_.images);

        List<WoodProject> woodProjects = sessionFactory.getCurrentSession().createQuery(criteria).list();

        return woodProjects.get(0);
    }

    public WoodProjectDTO findWoodProjectDTOById(Long id){
        WoodProject woodProject = (WoodProject)sessionFactory.getCurrentSession().get(WoodProject.class, id);
        WoodProjectDTO woodProjectDTO = new WoodProjectDTO(woodProject, ResourceProperties.WOOD_PROJECT_IMAGE_PROPERTIES);
        return woodProjectDTO;
    }

    //WORK IN PROGRESS

//    public WoodProjectDisplayDTO findWoodProjectByIdDisplayDTO(Long id) {
//        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
//        CriteriaQuery<WoodProjectDisplayDTO> woodProjectDisplayDTOQuery = builder.createQuery(WoodProjectDisplayDTO.class);
//        Root<WoodProject> woodProjectRoot = woodProjectDisplayDTOQuery.from(WoodProject.class);
//        woodProjectDisplayDTOQuery.where(builder.equal(woodProjectRoot.get(WoodProject_.id), id));
//        woodProjectDisplayDTOQuery.multiselect(
//                woodProjectRoot.get(WoodProject_.title),
//                woodProjectRoot.get(WoodProject_.woodulikeUser).get(WoodulikeUser_.username),
//                woodProjectRoot.get(WoodProject_.description),
//                woodProjectRoot.get(WoodProject_.images)
//        );
//
//
//        List<WoodProjectDisplayDTO> woodProjects = sessionFactory.getCurrentSession().createQuery(woodProjectDisplayDTOQuery).list();
//
//        return woodProjects.get(0);
//    }

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
