package com.dunn.dao.woodproject;

import com.dunn.model.WoodProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WoodProjectService {

    @Autowired
    private IWoodProjectDAO woodProjectDAO;

    public WoodProject findWoodProjectById(Long id){
        return woodProjectDAO.findWoodProjectById(id);
    }

}
