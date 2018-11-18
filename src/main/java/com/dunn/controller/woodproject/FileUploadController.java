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

@Controller
public class FileUploadController {


    private final IStorageService fileSystemStorageService;

    @Autowired
    public FileUploadController(IStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }


    @RequestMapping(value = "/fileUploadProcess", method = RequestMethod.POST)
    public java.lang.String fileUploadProcess(@ModelAttribute("woodProjectDTO") WoodProjectDTO woodProjectDTO, @SessionAttribute("woodProjectDTO") WoodProjectDTO sessionWoodProjectDTO, HttpSession httpSession, RedirectAttributes redirectAttributes) {


        WoodulikeUser woodulikeUser = (WoodulikeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        woodProjectDTO.setTempDirectory(sessionWoodProjectDTO.getTempDirectory());

        woodProjectDTO.setTempDirectory(
                fileSystemStorageService.storeToTempDirectory(
                        woodProjectDTO.getImageHolder(), woodulikeUser.getUsername(), woodProjectDTO.getTempDirectory()
                )
        );

        woodProjectDTO.addImageDirectory(
                woodProjectDTO.getTempDirectory().resolve(
                        woodProjectDTO.getImageHolder().getOriginalFilename()).toUri().toString().replaceAll(".*upload-dir", ("/userUploadedImages")
                )
        );


        redirectAttributes.addFlashAttribute("message", "File uploaded successfully");
        redirectAttributes.addFlashAttribute("woodProjectDTO", woodProjectDTO);
        //  sessionStatus.setComplete();

        return "redirect:" + ViewName.CREATE_WOOD_PROJECT + "?edit";
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


//    @Autowired
//    private IWoodProjectDAO woodProjectDAO;
//
//    @Value("${upload.location}")
//    private ViewName uploadLocation;
//
//    @RequestMapping(value="/uploadFile", method=RequestMethod.GET)
//    public ViewName uploadFile(MultipartFile multipartFile){
//
//        return "uploadFile";
//    }
//
//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    public ModelAndView submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
//
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("fileUploadView");
//        mav.addObject("file", file);
//
//
//
//        try {
//            WoodProject wp = new WoodProject();
//            wp.setTitle("TESTING IMAGE");
//            wp.setDescription("TESTING IMAGE DESC");
//            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
//            ViewName date = "2001-05-24";
//            try {
//                wp.setDate(sdf.parse(date));
//            }catch(ParseException e){
//                e.printStackTrace();
//            }
//            Image i = new Image();
//            i.setImageName(file.getName());
//
//            i.setPath("test path");
//
//            Blob blob = new SerialBlob(file.getBytes());
//         //   i.setFile(blob);
//
//            wp.setImages(Arrays.asList(i));
//
//            woodProjectDAO.createWoodProject(wp);
////////////////////////////////////////////////
////            File testFile = new File(uploadLocation + "resources/test.jpg");
////
////            testFile.createNewFile();
////
////
////
////
////            OutputStream outstream = new FileOutputStream(testFile);
////            outstream.write(file.getBytes());
////            outstream.close();
////            mav.addObject("fileDirectory", "resources/img/test.jpg");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SerialException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//
//        return mav;
//    }
//
//    @RequestMapping(value="/fileUploadView", method = RequestMethod.GET)
//    public ViewName fileUploadView(ModelMap modelMap){
//        return "fileUploadView";
//    }
//
////    @RequestMapping(value="/image")
////    public void getImage(HttpServletResponse response, ModelAndView mav) throws IOException{
////
////        WoodProject wp = woodProjectDAO.allWoodProjects().get(1);
////        Image image = wp.getImages().get(0);
////
////        byte[] data = image.getBlob();
////        InputStream in = new ByteArrayInputStream(data);
////        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
////        IOUtils.copy(in, response.getOutputStream());
////
////    }


}
