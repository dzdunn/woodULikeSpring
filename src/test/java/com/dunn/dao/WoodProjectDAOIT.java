package com.dunn.dao;

import com.dunn.config.webapp.WebMvcConfig;
import com.dunn.controller.uipaths.views.ViewNameWrapper;
import com.dunn.dao.woodproject.IWoodProjectDAO;
import com.dunn.model.woodproject.Image;
import com.dunn.model.woodproject.WoodProject;
import com.dunn.util.storage.TempWoodProjectImageStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitWebConfig(classes = WebMvcConfig.class)
@Transactional
public class WoodProjectDAOIT {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private IWoodProjectDAO woodProjectDAO;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private TempWoodProjectImageStorageService tempWoodProjectImageStorageService;

    private WoodProject woodProject;

    private List<Image> images;

    private Image image1;

    private Image image2;

    private Date date;

    @Value("${upload.location}")
    private String uploadLocation;


    @BeforeEach
    void setUp() throws ParseException, IOException {
        woodProject = new WoodProject();
        woodProject.setTitle("Test Title");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dateString = "1/1/2000 12:12:12";
        date = sdf.parse(dateString);
        woodProject.setDate(date);
        woodProject.setDescription("Test description");
        images = new ArrayList<Image>();
//
//        Image image1 = Image.createImageFromPath("C:\\Users\\dzdun\\Pictures\\graduation.jpg");
//
//        Image image2 = Image.createImageFromPath(image1.getPath());

        images.add(image1);
        images.add(image2);

        woodProject.setImages(images);
    }

    @Test
    public void testSessionFactoryNotNull() {
        assertNotNull(woodProjectDAO.getSessionFactory());
    }

    @Test
    @Commit
    public void testCreate() {
        woodProjectDAO.createWoodProject(woodProject);
    }

    @Test
    public void testCreateAndRead() {
        woodProjectDAO.createWoodProject(woodProject);
        WoodProject result = woodProjectDAO.findWoodProject(woodProject);
        assertEquals(result, woodProject);
    }

    @Test
    public void testCreateAndReadByTitle() {
        woodProjectDAO.createWoodProject(woodProject);
        WoodProject result = woodProjectDAO.findWoodProjectByTitle(woodProject.getTitle());
        assertEquals(result, woodProject);
    }

    @Test
    public void testCreateAndDelete() {
        woodProjectDAO.createWoodProject(woodProject);
        woodProjectDAO.deleteWoodProject(woodProject);
        WoodProject result = woodProjectDAO.findWoodProject(woodProject);
        assertNull(result);
    }

    @Test
    public void testCreateAndUpdate() throws ParseException {
        String updatedTitle = "UPDATED TITLE";
        String updatedDescription ="UPDATED DESCRIPTION";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
        Date newDate = sdf.parse("2000-12-01");



        woodProjectDAO.createWoodProject(woodProject);
        woodProject.setTitle(updatedTitle);
        woodProject.setDescription(updatedDescription);
        woodProject.setDate(newDate);


       //Image updatedImage = Image.createImageFromPath(image1.getPath());

       //woodProject.setImages(Arrays.asList(updatedImage));

        woodProjectDAO.updateWoodProject(woodProject);

        WoodProject result = woodProjectDAO.findWoodProject(woodProject);

        assertEquals(result, woodProject);
    }

    @Test
    public void testGetAllWoodProjects(){
        woodProjectDAO.createWoodProject(woodProject);
        WoodProject wp2 = new WoodProject();
        woodProjectDAO.createWoodProject(wp2);
        WoodProject wp3 = new WoodProject();
        woodProjectDAO.createWoodProject(wp3);

        List<WoodProject> result = woodProjectDAO.allWoodProjects();

        assertTrue(result.containsAll(Arrays.asList(woodProject, wp2, wp3)));
    }

    @Test
    public void testEmail(){

        assertNotNull(javaMailSender);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("ngdunn@gmail.com");
        message.setSubject("Xmas Present");
        message.setText("Can I have £2000 for a new PC please?");
        javaMailSender.send(message);

    }

    @Test
    public void testReflectionList() throws IllegalAccessException {
//
//        Field[] fields = ViewName.class.getDeclaredFields();
//        List<ViewName> urls = new ArrayList<>();
//
//        for(Field field : fields){
//            if(Modifier.isPublic(field.getModifiers())){
//                urls.add((ViewName)field.get(ViewName.class));
//            }
//        }
//
//        for(ViewName url : urls){
//            System.out.println(url);
//        }
        for(String url : ViewNameWrapper.getPublicViewUrls()){
            System.out.println(url);
        }
    }

    @Test
    public void testDirectory(){
        assertNotNull(tempWoodProjectImageStorageService);
        tempWoodProjectImageStorageService.init();
    }

    @Value("${storage.location.createwoodproject.temp}")
    private String testTempDir;

    @Test
    public void testDirectoryPropertyNotNull(){
        assertNotNull(testTempDir);
        System.out.println(testTempDir);
    }

}