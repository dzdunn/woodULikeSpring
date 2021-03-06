package com.dunn.dao.woodproject;

import com.dunn.model.WoodProject;
import org.hibernate.SessionFactory;

import java.util.List;

public interface IWoodProjectDAO {

    WoodProject createWoodProject(WoodProject woodProject);

    WoodProject findWoodProject(WoodProject woodProject);

    WoodProject findWoodProjectByTitle(String title);

    WoodProject updateWoodProject(WoodProject woodProject);

    void deleteWoodProject(WoodProject woodProject);

    List<WoodProject> allWoodProjects();

    SessionFactory getSessionFactory();

    void setSessionFactory(SessionFactory sessionFactory);

}
