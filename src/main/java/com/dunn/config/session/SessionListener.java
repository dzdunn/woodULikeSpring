package com.dunn.config.session;

import com.dunn.dto.ui.WoodProjectDTO;
import org.springframework.util.FileSystemUtils;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;

public class SessionListener implements HttpSessionListener {


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(60);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        cleanTempWoodProjectDirectory(se);
    }

    private void cleanTempWoodProjectDirectory(HttpSessionEvent se){
        if (se.getSession().getAttribute("woodProjectDTO") != null) {
            WoodProjectDTO woodProjectDTO = (WoodProjectDTO) se.getSession().getAttribute("woodProjectDTO");
            if (woodProjectDTO.getTempDirectory() != null) {
                try {
                    FileSystemUtils.deleteRecursively(woodProjectDTO.getTempDirectory());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
