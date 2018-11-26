package com.dunn.controller.woodproject;


import com.dunn.controller.path.views.ViewName;
import com.dunn.dao.woodproject.IWoodProjectDAO;
import com.dunn.model.Image;
import com.dunn.model.WoodProject;
import com.dunn.model.storage.IStorageService;
import com.dunn.model.user.WoodProjectDTO;
import com.dunn.model.user.WoodulikeUser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("woodProjectDTO")
public class MyWoodProjectController {

    @Autowired
    private IWoodProjectDAO woodProjectDAO;

    @Autowired
    private IStorageService woodProjectImageStorageService;

    @Autowired
    private IStorageService createWoodProjectTempImageStorageService;

    @ModelAttribute("woodProjectDTO")
    public WoodProjectDTO createWoodProjectDTO() {

        return new WoodProjectDTO();
    }


    //@ScopeSessionAttributesToViews(sessionStatus = "status", allowedViewNames = {"redirect:" + ViewName.CREATE_WOOD_PROJECT}, sessionAttribute = "woodProjectDTO")
    @RequestMapping(value = {ViewName.CREATE_WOOD_PROJECT}, method = RequestMethod.GET)
    public ModelAndView showCreateWoodProject(SessionStatus status, Model model) {
        status.setComplete();

        ModelAndView mav = new ModelAndView("redirect:" + ViewName.EDIT_WOOD_PROJECT);
        return mav;
    }

    @RequestMapping(value = ViewName.EDIT_WOOD_PROJECT, method = RequestMethod.GET)
    public ModelAndView editCreateWoodProject() {
        ModelAndView mav = new ModelAndView(ViewName.CREATE_WOOD_PROJECT);
        return mav;
    }


    @RequestMapping(value = ViewName.SAVE_WOOD_PROJECT, method = RequestMethod.GET)
    public String saveWoodProject(SessionStatus status, @ModelAttribute("woodProjectDTO") WoodProjectDTO woodProjectDTO, RedirectAttributes redirectAttributes) {


        WoodProject woodProject = woodProjectDTO.getWoodProject();

        woodProjectDTO.getImageDirectories().forEach(x -> {
            Image image = new Image();
            Path path = Paths.get(x);
            image.setImageName(path.getFileName().toString());
            image.setPath(path.toString());

            image.setWoodProject(woodProject);
            woodProject.getImages().add(image);

        });

        woodProject.setWoodulikeUser((WoodulikeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        woodProject.setDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        woodProjectDAO.createWoodProject(woodProject);

        //redirectAttributes.addFlashAttribute("tempDir", woodProjectDTO.getTempDirectory());

        return "redirect:/saveCleanup";
    }

    @RequestMapping(value = "/saveCleanup", method = RequestMethod.GET)
    public String cleanUp(SessionStatus status, Model model, @SessionAttribute WoodProjectDTO woodProjectDTO){

//        Path tmpDir = (Path)model.asMap().get("tempDir");
        woodProjectImageStorageService.deleteDirectory(woodProjectDTO.getTempDirectory());
        status.setComplete();
        return "redirect:" + ViewName.CREATE_WOOD_PROJECT;

    }


    @RequestMapping(value = ViewName.MANAGE_WOOD_PROJECTS, method = RequestMethod.GET)
    public String manageWoodProjects() {
        return ViewName.MANAGE_WOOD_PROJECTS;
    }

    @RequestMapping(value = ViewName.MY_PROFILE, method = RequestMethod.GET)
    public String myProfile(@RequestParam(value = "delete", required = false) boolean delete) {
        createWoodProjectTempImageStorageService.deleteAll();

        try {
            if(!delete) {

                Files.createDirectory(Paths.get("testing"));
            } else if (delete){
                //FileUtils.cleanDirectory(Paths.get("testing").toFile());
                FileSystemUtils.deleteRecursively(Paths.get("testing").toFile());
                //Files.delete(Paths.get("testing"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ViewName.MY_PROFILE;
    }

}
