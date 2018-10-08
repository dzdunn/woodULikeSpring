package com.dunn.controller;

import com.dunn.dao.woodproject.IWoodProjectDAO;
import com.dunn.model.Image;
import com.dunn.model.WoodProject;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Controller
public class FileUploadController {

    @Autowired
    private IWoodProjectDAO woodProjectDAO;

    @Value("${upload.location}")
    private String uploadLocation;

    @RequestMapping(value="/uploadFile", method=RequestMethod.GET)
    public String uploadFile(MultipartFile multipartFile){

        return "uploadFile";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ModelAndView submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("fileUploadView");
        mav.addObject("file", file);



        try {
            WoodProject wp = new WoodProject();
            wp.setTitle("TESTING IMAGE");
            wp.setDescription("TESTING IMAGE DESC");
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
            String date = "2001-05-24";
            try {
                wp.setDate(sdf.parse(date));
            }catch(ParseException e){
                e.printStackTrace();
            }
            Image i = new Image();
            i.setImageName(file.getName());

            i.setPath("test path");

            Blob blob = new SerialBlob(file.getBytes());
         //   i.setFile(blob);

            wp.setImages(Arrays.asList(i));

            woodProjectDAO.createWoodProject(wp);
//////////////////////////////////////////////
//            File testFile = new File(uploadLocation + "resources/test.jpg");
//
//            testFile.createNewFile();
//
//
//
//
//            OutputStream outstream = new FileOutputStream(testFile);
//            outstream.write(file.getBytes());
//            outstream.close();
//            mav.addObject("fileDirectory", "resources/img/test.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SerialException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return mav;
    }

    @RequestMapping(value="/fileUploadView", method = RequestMethod.GET)
    public String fileUploadView(ModelMap modelMap){
        return "fileUploadView";
    }

    @RequestMapping(value="/image")
    public void getImage(HttpServletResponse response, ModelAndView mav) throws IOException{

        WoodProject wp = woodProjectDAO.allWoodProjects().get(1);
        Image image = wp.getImages().get(0);

        byte[] data = image.getBlob();
        InputStream in = new ByteArrayInputStream(data);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());

    }



}
