package com.dunn.controller.woodproject;

import com.dunn.controller.uipaths.views.ViewName;
import com.dunn.dao.woodproject.WoodProjectService;
import com.dunn.dto.ui.WoodProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WoodProjectController {

    @Autowired
    private WoodProjectService woodProjectService;

    @RequestMapping(value = ViewName.WOOD_PROJECT, method = RequestMethod.GET)
    public String showGenericWoodProject(){
        return ViewName.WOOD_PROJECT;
    }

    @RequestMapping(value = ViewName.WOOD_PROJECT + "/{wpId}", method = RequestMethod.GET)
    public ModelAndView showWoodProjectById(@PathVariable("wpId") Long id){

        WoodProjectDTO woodProjectDTO = woodProjectService.findWoodProjectDTOById(id);

        ModelAndView mav = new ModelAndView(ViewName.WOOD_PROJECT);
        mav.addObject(woodProjectDTO);
        return mav;
    }
}
