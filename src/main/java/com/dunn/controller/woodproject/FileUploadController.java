package com.dunn.controller.woodproject;

import com.dunn.controller.path.PathHelper;
import com.dunn.controller.path.resources.ResourceProperties;
import com.dunn.controller.path.views.ViewName;
import com.dunn.util.storage.CreateWoodProjectTempImageStorageService;
import com.dunn.util.storage.StorageFileNotFoundException;
import com.dunn.model.user.WoodProjectDTO;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class FileUploadController {

    private final CreateWoodProjectTempImageStorageService createWoodProjectTempImageStorageService;

    @Autowired
    public FileUploadController(CreateWoodProjectTempImageStorageService createWoodProjectTempImageStorageService) {
        this.createWoodProjectTempImageStorageService = createWoodProjectTempImageStorageService;
    }

    @RequestMapping(value = ViewName.FILE_UPLOAD_PROCESS, method = RequestMethod.POST)
    public String fileUploadProcess(@ModelAttribute("woodProjectDTO") WoodProjectDTO woodProjectDTO, @SessionAttribute("woodProjectDTO") WoodProjectDTO sessionWoodProjectDTO, @RequestParam(value = "save", required = false) boolean isSave, RedirectAttributes redirectAttributes) {


        //Update model with tempDirectory and ImageDirectoryList from session so not lost
        updateModelFromSessionModel(woodProjectDTO, sessionWoodProjectDTO);

        //Store Image to the tempDirectory stored in session (or create new tempDirectory if session object's tempDirectory is null)
        storeImageToSessionDirectory(woodProjectDTO);

        //Add the new image directory to the list
        addImageDirectoryToWoodProjectSession(woodProjectDTO);

        redirectAttributes.addFlashAttribute("message", "File uploaded successfully");
        redirectAttributes.addFlashAttribute("woodProjectDTO", woodProjectDTO);

        if (isSave) {
            return "redirect:" + ViewName.SAVE_WOOD_PROJECT;
        }
        return "redirect:" + ViewName.EDIT_WOOD_PROJECT;
    }

    private WoodProjectDTO updateModelFromSessionModel(WoodProjectDTO modelAttribute, WoodProjectDTO sessionAttribute) {
        if (sessionAttribute.getTempDirectory() != null) {
            modelAttribute.setTempDirectory(sessionAttribute.getTempDirectory());
            modelAttribute.setImageDirectories(sessionAttribute.getImageDirectories());
        }
        return modelAttribute;
    }

    private WoodProjectDTO storeImageToSessionDirectory(WoodProjectDTO woodProjectDTO) {
        if (woodProjectDTO.getImageFile() != null & !woodProjectDTO.getImageFile().isEmpty()) {
            WoodulikeUser woodulikeUser = (WoodulikeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(woodProjectDTO.getTempDirectory() == null) {
                Path tempDir = createWoodProjectTempImageStorageService.createTempWoodProjectPath(woodulikeUser.getUsername());
                woodProjectDTO.setTempDirectory(tempDir);
            }
            if(woodProjectDTO.getTempDirectory() != null){
                createWoodProjectTempImageStorageService.store(woodProjectDTO.getImageFile(), woodProjectDTO.getTempDirectory());
            }
        }
        //Add validation and error message if empty
        return woodProjectDTO;
    }

    private WoodProjectDTO addImageDirectoryToWoodProjectSession(WoodProjectDTO woodProjectDTO) {
        String filename = woodProjectDTO.getImageFile().getOriginalFilename();
        if (filename != null && filename.matches(".*\\S.*")) {
            Path imagePath = PathHelper.getFileNamePath(woodProjectDTO.getImageFile().getOriginalFilename(), woodProjectDTO.getTempDirectory());
            String newRelativePath = PathHelper.replaceRootWithResourceHandlerWithForwardSlash(imagePath, ResourceProperties.CREATE_WOOD_PROJECT_TEMP_PROPERTIES.getResourcePropertiesHolder());
            if (!woodProjectDTO.getImageDirectories().contains(newRelativePath)) {
                woodProjectDTO.addImageDirectory(newRelativePath);
            }
        }
        return woodProjectDTO;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
