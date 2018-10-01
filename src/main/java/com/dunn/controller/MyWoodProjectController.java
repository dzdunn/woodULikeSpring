package com.dunn.controller;

import com.dunn.dao.PostgresqlDAO;
import com.dunn.model.WoodProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/myWoodProjects")
public class MyWoodProjectController {

    @Autowired
    private PostgresqlDAO postgresqlDAO;

    @RequestMapping(value="/createWoodProject", method = RequestMethod.GET)
    public ModelAndView createWoodProject(ModelAndView mav){
        String viewName = "myWoodProjects/createWoodProject";
        mav.setViewName(viewName);
        mav.addObject("woodProject", new WoodProject());
        return mav;
    }

    @RequestMapping(value="/createWoodProject", method= RequestMethod.POST)
    public ModelAndView createWoodProject(@ModelAttribute("woodProject") WoodProject woodProject, ModelAndView mav){

        postgresqlDAO.createWoodProject(woodProject);
        String viewName = "myWoodProjects/createWoodProject";
        mav.setViewName(viewName);
        return mav;
    }
}
