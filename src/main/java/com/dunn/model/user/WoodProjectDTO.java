package com.dunn.model.user;

import com.dunn.model.WoodProject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class WoodProjectDTO {

    private WoodProject woodProject;

    private List<MultipartFile> tmpImages;

    public List<MultipartFile> getTmpImages() {
        return tmpImages;
    }

    public void setTmpImages(List<MultipartFile> tmpImages) {
        this.tmpImages = tmpImages;
    }

    public WoodProject getWoodProject() {
        return woodProject;
    }

    public void setWoodProject(WoodProject woodProject) {
        this.woodProject = woodProject;
    }
}
