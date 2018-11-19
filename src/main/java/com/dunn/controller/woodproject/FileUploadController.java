package com.dunn.controller.woodproject;

import com.dunn.controller.path.ViewName;
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

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {


    private final IStorageService fileSystemStorageService;

    @Autowired
    public FileUploadController(IStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }


    @RequestMapping(value = "/fileUploadProcess", method = RequestMethod.POST)
    public java.lang.String fileUploadProcess(@ModelAttribute("woodProjectDTO") WoodProjectDTO woodProjectDTO, @SessionAttribute("woodProjectDTO") WoodProjectDTO sessionWoodProjectDTO, @RequestParam(value = "save", required = false) boolean isSave, RedirectAttributes redirectAttributes) {

        updateModelFromSessionModel(woodProjectDTO, sessionWoodProjectDTO);

        storeImageToSessionDirectory(woodProjectDTO);

        addImageDirectoryToWoodProjectSession(woodProjectDTO);

        redirectAttributes.addFlashAttribute("message", "File uploaded successfully");
        redirectAttributes.addFlashAttribute("woodProjectDTO", woodProjectDTO);

        if(isSave){
            return "redirect:" + ViewName.SAVE_WOOD_PROJECT;
        }
        return "redirect:" + ViewName.EDIT_WOOD_PROJECT;
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    private WoodProjectDTO updateModelFromSessionModel(WoodProjectDTO modelAttribute, WoodProjectDTO sessionAttribute) {
        modelAttribute.setTempDirectory(sessionAttribute.getTempDirectory());

        if(sessionAttribute.getTempDirectory() != null) {

            try {
                modelAttribute.setImageDirectories(Files.list(sessionAttribute.getTempDirectory()).map(x -> resolveRelativeUserUploadTempDirectory(sessionAttribute.getTempDirectory(), x.getFileName())).collect(Collectors.toList()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return modelAttribute;
    }

    private WoodProjectDTO storeImageToSessionDirectory(WoodProjectDTO woodProjectDTO) {
        if(woodProjectDTO.getImageHolder() != null & !woodProjectDTO.getImageHolder().isEmpty()) {
            WoodulikeUser woodulikeUser = (WoodulikeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            woodProjectDTO.setTempDirectory(
                    fileSystemStorageService.storeToTempDirectory(
                            woodProjectDTO.getImageHolder(), woodulikeUser.getUsername(), woodProjectDTO.getTempDirectory()
                    )
            );
        }
    //Add validation and error message if empty
        return woodProjectDTO;
    }

    private WoodProjectDTO addImageDirectoryToWoodProjectSession(WoodProjectDTO woodProjectDTO) {
        if(woodProjectDTO.getImageHolder().getOriginalFilename() != null && !woodProjectDTO.getImageHolder().getOriginalFilename().equals("")) {
            String newPath = resolveRelativeUserUploadTempDirectory(woodProjectDTO.getTempDirectory(), Paths.get(woodProjectDTO.getImageHolder().getOriginalFilename()));
            if (!woodProjectDTO.getImageDirectories().contains(newPath)) {
                woodProjectDTO.addImageDirectory(newPath);
            }
        }
        return woodProjectDTO;
    }

    private String resolveRelativeUserUploadTempDirectory(Path tempDirectory, Path fileName){
        return tempDirectory.resolve(fileName).toUri().toString().replaceAll(".*upload-dir", ("/userUploadedImages"));
    }
}
