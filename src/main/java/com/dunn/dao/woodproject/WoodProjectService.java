package com.dunn.dao.woodproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WoodProjectService {

    @Autowired
    private IWoodProjectDAO woodProjectDAO;

}
