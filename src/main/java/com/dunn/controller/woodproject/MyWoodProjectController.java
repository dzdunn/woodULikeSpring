package com.dunn.controller.woodproject;


import com.dunn.controller.path.views.ViewName;
import com.dunn.dao.woodproject.IWoodProjectDAO;
import com.dunn.model.Image;
import com.dunn.model.WoodProject;
import com.dunn.util.storage.CreateWoodProjectTempImageStorageService;
import com.dunn.util.storage.WoodProjectImageStorageService;
import com.dunn.model.user.WoodProjectDTO;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@SessionAttributes("woodProjectDTO")
public class MyWoodProjectController {

    @Autowired
    private IWoodProjectDAO woodProjectDAO;

    @Autowired
    private WoodProjectImageStorageService woodProjectImageStorageService;

    @Autowired
    private CreateWoodProjectTempImageStorageService createWoodProjectTempImageStorageService;

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
    public String saveWoodProject(SessionStatus status, @ModelAttribute("woodProjectDTO") WoodProjectDTO woodProjectDTO, RedirectAttributes redirectAttributes) throws IOException {

        Path targetDirectory = generateTargetDirectory(woodProjectDTO);
        boolean isCopySuccessful = copyToTargetDirectory(woodProjectDTO, targetDirectory);

        if(isCopySuccessful){

            WoodProject woodProject = updateWoodProjectImagePaths(woodProjectDTO, targetDirectory);
            woodProject.setDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            woodProject.setWoodulikeUser(((WoodulikeUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
            woodProjectDAO.createWoodProject(woodProject);

            createWoodProjectTempImageStorageService.deleteDirectory(woodProjectDTO.getTempDirectory());

            status.setComplete();
            return "redirect:" + ViewName.CREATE_WOOD_PROJECT;
        } else{

            //SHOULD IMPLEMENT REDIRECT TO RESUBMIT PROJECT WITH INFORMATION IF UNSUCCESFUL
            //status.setComplete();
            return "redirect:" + ViewName.CREATE_WOOD_PROJECT;
        }
    }


    @RequestMapping(value = ViewName.MANAGE_WOOD_PROJECTS, method = RequestMethod.GET)
    public String manageWoodProjects() {
        return ViewName.MANAGE_WOOD_PROJECTS;
    }

    @RequestMapping(value = ViewName.MY_PROFILE, method = RequestMethod.GET)
    public String myProfile() {
        return ViewName.MY_PROFILE;
    }

    private Path generateTargetDirectory(WoodProjectDTO woodProjectDTO){
        WoodProject woodProject = woodProjectDTO.getWoodProject();

        String username = woodProject.getWoodulikeUser() != null ? woodProject.getWoodulikeUser().getUsername() : ((WoodulikeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        String projectTitle = woodProjectDTO.getWoodProject().getTitle();

        return woodProjectImageStorageService.createWoodProjectPath(username, projectTitle);
    }


    private boolean copyToTargetDirectory(WoodProjectDTO woodProjectDTO, Path targetDirectory) {
        boolean isCopySuccessful = woodProjectImageStorageService.copy(woodProjectDTO.getTempDirectory(), targetDirectory);
        if (isCopySuccessful) {
            createWoodProjectTempImageStorageService.deleteDirectory(woodProjectDTO.getTempDirectory());
        }
        return isCopySuccessful;
    }

    private WoodProject updateWoodProjectImagePaths(WoodProjectDTO woodProjectDTO, Path targetDirectory) throws IOException {
        WoodProject woodProject = woodProjectDTO.getWoodProject();
        try(Stream<Path> newPaths = Files.walk(targetDirectory)) {
            woodProjectDTO.setImagePaths(newPaths.collect(Collectors.toList()));
            woodProjectDTO.getImagePaths().forEach(path -> {
                if (!Files.isDirectory(path)) {
                    Image image = new Image();
                    image.setImageName(path.getFileName().toString());
                    image.setPath(path.toString());
                    image.setStoredDirectory(path.toAbsolutePath().toString());
                    image.setWoodProject(woodProject);
                    woodProject.getImages().add(image);
                }
            });
        }
        return woodProject;

    }
}
