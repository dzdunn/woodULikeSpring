package com.dunn.dao.woodproject;

import com.dunn.dto.hibernate.WoodProjectDisplayDTO;
import com.dunn.dto.ui.WoodProjectDTO;
import com.dunn.model.woodproject.WoodProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WoodProjectService {

    @Autowired
    private WoodProjectDAO woodProjectDAO;

    public WoodProject findWoodProjectById(Long id){
        return woodProjectDAO.findWoodProjectById(id);
    }

    public WoodProjectDTO findWoodProjectDTOById(Long id){
        return woodProjectDAO.findWoodProjectDTOById(id);
    }

//    public WoodProjectDisplayDTO findWoodProjectDisplayDTOById(Long id){
//        return  woodProjectDAO.findWoodProjectByIdDisplayDTO(id);
//    }



}
