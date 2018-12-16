package com.dunn.config.webapp;

import com.dunn.controller.uipaths.views.ViewName;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

//@WebServlet(value = ViewName.FILE_UPLOAD_PROCESS, name = "fileUploadServlet", loadOnStartup = 2)
//@MultipartConfig(location = "/", maxFileSize = 500000, maxRequestSize = 500000, fileSizeThreshold = 0)
public class FileUploadServlet extends HttpServlet {

    @Value("${storage.maxfilesize}")
    private long maxFileSize;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String protocol = req.getProtocol();

        String msg = "GETTING";
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String protocol = req.getProtocol();
        if(req.getParts() != null){
            for(Part p : req.getParts()){
                if(p.getSize() > 500){
                    resp.sendRedirect(ViewName.HOME);
                    return;
                }
            }
        }
        String msg = "blah";
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }


}
