package com.dunn.controller.woodproject;


import com.dunn.aspects.sessionmanagement.ManageCreateWoodProjectSession;
import com.dunn.controller.path.ViewName;
import com.dunn.dao.woodproject.IWoodProjectDAO;
import com.dunn.model.WoodProject;
import com.dunn.model.user.WoodProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("woodProjectDTO")
public class MyWoodProjectController {

    @Autowired
    private IWoodProjectDAO woodProjectDAO;

    @ModelAttribute("woodProjectDTO")
    public WoodProjectDTO createWoodProjectDTO() {

        return new WoodProjectDTO();
    }


    @ManageCreateWoodProjectSession(sessionStatus = "status", allowedViewNames = {"redirect:" + ViewName.CREATE_WOOD_PROJECT})
    @RequestMapping(value = ViewName.CREATE_WOOD_PROJECT, method = RequestMethod.GET)
    public ModelAndView showCreateWoodProject(Model model, SessionStatus status) {
       // status.setComplete();
        //createWoodProjectDTO();


        //ModelAndView mav = new ModelAndView("redirect:" +ViewName.CREATE_WOOD_PROJECT + "?edit");
        ModelAndView mav = new ModelAndView(ViewName.CREATE_WOOD_PROJECT);

        return mav;
    }

    @RequestMapping(value = ViewName.CREATE_WOOD_PROJECT, method = RequestMethod.GET, params = {"edit"})
    public ModelAndView editCreateWoodProject() {


        ModelAndView mav = new ModelAndView(ViewName.CREATE_WOOD_PROJECT);

        return mav;
    }


    @RequestMapping(value = ViewName.CREATE_WOOD_PROJECT, method = RequestMethod.POST)
    public ModelAndView createWoodProject(@ModelAttribute("woodProjectDTO") WoodProjectDTO woodProjectDTO) {
        ModelAndView mav = new ModelAndView();

        WoodProject woodProject = woodProjectDTO.getWoodProject();

        woodProjectDAO.createWoodProject(woodProject);
        mav.setViewName(ViewName.CREATE_WOOD_PROJECT);
        return mav;
    }

    @RequestMapping(value = ViewName.MANAGE_WOOD_PROJECTS, method = RequestMethod.GET)
    public java.lang.String manageWoodProjects() {
        return ViewName.MANAGE_WOOD_PROJECTS;
    }

    @RequestMapping(value = ViewName.MY_PROFILE, method = RequestMethod.GET)
    public java.lang.String myProfile() {
        return ViewName.MY_PROFILE;
    }

}
