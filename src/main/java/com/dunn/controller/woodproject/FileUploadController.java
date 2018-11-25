package com.dunn.controller.woodproject;

import com.dunn.controller.path.PathHelper;
import com.dunn.controller.path.resources.ResourceProperties;
import com.dunn.controller.path.resources.ResourcePropertiesHolder;
import com.dunn.controller.path.views.ViewName;
import com.dunn.model.storage.IStorageService;
import com.dunn.model.storage.StorageFileNotFoundException;
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
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {


    private final IStorageService createWoodProjectTempImageStorageService;

    @Autowired
    public FileUploadController(IStorageService createWoodProjectTempImageStorageService) {
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
        modelAttribute.setTempDirectory(sessionAttribute.getTempDirectory());

        if (sessionAttribute.getTempDirectory() != null) {

            try {
                modelAttribute.setImageDirectories(
                        Files.list(sessionAttribute.getTempDirectory())
                                .map(x -> PathHelper
                                        .replaceRootWithResourceHandlerWithForwardSlash(
                                                x,
                                                ResourceProperties.CREATE_WOOD_PROJECT_TEMP_PROPERTIES.getResourcePropertiesHolder()).toString())
                                .collect(Collectors.toList()
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return modelAttribute;
    }

    private WoodProjectDTO storeImageToSessionDirectory(WoodProjectDTO woodProjectDTO) {
        if (woodProjectDTO.getImageHolder() != null & !woodProjectDTO.getImageHolder().isEmpty()) {
            WoodulikeUser woodulikeUser = (WoodulikeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            woodProjectDTO.setTempDirectory(
                    createWoodProjectTempImageStorageService.storeToUniqueDirectory(
                            woodProjectDTO.getImageHolder(),
                            woodulikeUser.getUsername(),
                            woodProjectDTO.getTempDirectory()
                    )
            );
        }
        //Add validation and error message if empty
        return woodProjectDTO;
    }

    private WoodProjectDTO addImageDirectoryToWoodProjectSession(WoodProjectDTO woodProjectDTO) {
        if (woodProjectDTO.getImageHolder().getOriginalFilename() != null
                && !woodProjectDTO.getImageHolder().getOriginalFilename().equals("")) {
            //String newPath = resolveRelativeUserUploadTempDirectory(woodProjectDTO.getTempDirectory(), Paths.get(woodProjectDTO.getImageHolder().getOriginalFilename()));

            Path imagePath = PathHelper.getFileNamePath(woodProjectDTO.getImageHolder().getOriginalFilename(), woodProjectDTO.getTempDirectory());
            String newRelativePath = PathHelper.replaceRootWithResourceHandlerWithForwardSlash(imagePath, ResourceProperties.CREATE_WOOD_PROJECT_TEMP_PROPERTIES.getResourcePropertiesHolder()).toString();

            if (!woodProjectDTO.getImageDirectories().contains(newRelativePath)) {
                woodProjectDTO.addImageDirectory(newRelativePath);
            }
        }
        return woodProjectDTO;
    }
//
//    private String resolveRelativeUserUploadTempDirectory(Path tempDirectory, Path fileName) {
//        return tempDirectory.resolve(fileName).toUri().toString().replaceAll(".*upload-dir", ("/userUploadedImages"));
//    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


}
