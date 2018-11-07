package com.dunn.controller;

import com.dunn.controller.path.ViewName;
import com.dunn.dao.woodproject.IWoodProjectDAO;
import com.dunn.model.WoodProject;
import com.dunn.model.user.WoodProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
public class MyWoodProjectController {

    @Autowired
    private IWoodProjectDAO woodProjectDAO;

    @RequestMapping(value = ViewName.CREATE_WOOD_PROJECT, method = RequestMethod.GET)
    public ModelAndView createWoodProject(Model model) {

        ModelAndView mav = new ModelAndView(ViewName.CREATE_WOOD_PROJECT);
        if(!model.containsAttribute("woodProjectDTO")) {
            mav.addObject("woodProjectDTO", new WoodProjectDTO());
        }



        return mav;
    }

    @RequestMapping(value = ViewName.CREATE_WOOD_PROJECT, method = RequestMethod.POST)
    public ModelAndView createWoodProject(@ModelAttribute("woodProjectDTO") WoodProjectDTO woodProjectDTO) {
        ModelAndView mav = new ModelAndView();

        WoodProject woodProject = woodProjectDTO.getWoodProject();
        //woodProject.set

        woodProjectDAO.createWoodProject(woodProject);
        mav.setViewName(ViewName.CREATE_WOOD_PROJECT);
        return mav;
    }

    @RequestMapping(value = ViewName.MANAGE_WOOD_PROJECTS, method = RequestMethod.GET)
    public String manageWoodProjects() {
        return ViewName.MANAGE_WOOD_PROJECTS;
    }

    @RequestMapping(value = ViewName.MY_PROFILE, method = RequestMethod.GET)
    public String myProfile() {
        return ViewName.MY_PROFILE;
    }

}
