package com.dunn.dao;

import com.dunn.config.PersistenceJPAConfig;
import com.dunn.config.WebAppInitialiser;
import com.dunn.config.WebMvcConfig;
import com.dunn.model.Image;
import com.dunn.model.WoodProject;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebMvcConfig.class, PersistenceJPAConfig.class})
@Transactional
public class PostgresqlDAOIT {

@Autowired
private ApplicationContext applicationContext;

    @Autowired
    private PersistenceJPAConfig persistenceJPAConfig;

    @Autowired
    private PostgresqlDAO postgresqlDAO;

    private WoodProject woodProject;

    private List<Image> images;

    private Image image1;

    private Image image2;

    private Date date;

    @BeforeEach
    void setUp() throws ParseException {
        woodProject = new WoodProject();
        woodProject.setTitle("Test Title");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dateString = "1/1/2000 12:12:12";
        date = sdf.parse(dateString);
        woodProject.setDate(date);
        woodProject.setDescription("Test description");
        images = new ArrayList<Image>();

        image1 = new Image();
        image1.setImageName("Test image 1");
        image1.setPath("C:\\Users\\dzdun\\git\\woodULikeSpring\\src\\main\\webapp\\resources\\img\\carousel\\picard.jpg");

        image2 = new Image();
        image2.setImageName("Test image 2");
        image2.setPath("C:\\Users\\dzdun\\git\\woodULikeSpring\\src\\main\\webapp\\resources\\img\\carousel\\picard2.jpg");

        images.add(image1);
        images.add(image2);

        woodProject.setImages(images);
    }

    @Test
    public void testSessionFactoryNotNull(){


        assertNotNull(postgresqlDAO.getSessionFactory());
    }

    @Test
    public void testCreate(){
      //  sessionFactory.getCurrentSession().saveOrUpdate(woodProject);

       // WoodProject wpResult = (WoodProject) sessionFactory.getCurrentSession().get(WoodProject.class, woodProject.getId());

       // assertEquals(woodProject, wpResult);
    }

    @Test
    public void testProfile(){
        for(String s :applicationContext.getEnvironment().getActiveProfiles()){
            System.out.println(s);
        };

    }
}