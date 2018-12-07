package com.dunn.controller.mywoodprojects;

import com.dunn.controller.uipaths.PathHelper;
import com.dunn.controller.uipaths.views.ViewName;
import com.dunn.util.storage.TempWoodProjectImageStorageService;
import com.dunn.util.storage.StorageFileNotFoundException;
import com.dunn.dto.ui.WoodProjectDTO;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;

@Controller
public class FileUploadController implements HandlerExceptionResolver {

    private final TempWoodProjectImageStorageService tempWoodProjectImageStorageService;

    @Autowired
    public FileUploadController(TempWoodProjectImageStorageService tempWoodProjectImageStorageService) {
        this.tempWoodProjectImageStorageService = tempWoodProjectImageStorageService;
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
            modelAttribute.setImagePaths(sessionAttribute.getImagePaths());
        }
        return modelAttribute;
    }

    private WoodProjectDTO storeImageToSessionDirectory(WoodProjectDTO woodProjectDTO) {
        if (woodProjectDTO.getImageFile() != null && !woodProjectDTO.getImageFile().isEmpty()) {
            WoodulikeUser woodulikeUser = (WoodulikeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(woodProjectDTO.getTempDirectory() == null) {
                Path tempDir = tempWoodProjectImageStorageService.createTempWoodProjectPath(woodulikeUser.getUsername());
                woodProjectDTO.setTempDirectory(tempDir);
            }
            if(woodProjectDTO.getTempDirectory() != null){
                tempWoodProjectImageStorageService.store(woodProjectDTO.getImageFile(), woodProjectDTO.getTempDirectory());
            }
        }
        //Add validation and error message if empty
        return woodProjectDTO;
    }

    private WoodProjectDTO addImageDirectoryToWoodProjectSession(WoodProjectDTO woodProjectDTO) {
        String filename = woodProjectDTO.getImageFile().getOriginalFilename();
        if (filename != null && filename.matches(".*\\S.*")) {
            Path imagePath = PathHelper.getFileNamePath(woodProjectDTO.getImageFile().getOriginalFilename(), woodProjectDTO.getTempDirectory());
            if(!woodProjectDTO.getImagePaths().contains(imagePath)){
                woodProjectDTO.addImagePath(imagePath);
            }
        }
        return woodProjectDTO;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView(ViewName.EDIT_WOOD_PROJECT);
        if (ex instanceof MaxUploadSizeExceededException) {
            modelAndView.getModel().put("message", "File size exceeds limit!");
        }
        return modelAndView;
    }
}
