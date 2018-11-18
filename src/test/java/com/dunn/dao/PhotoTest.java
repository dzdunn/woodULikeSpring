package com.dunn.dao;

import com.dunn.config.webapp.WebMvcConfig;
import com.dunn.model.Photo;


import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitWebConfig(classes = {WebMvcConfig.class})
@Transactional
public class PhotoTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testCountryList(){
//        ViewName[] locales = Locale.getISOCountries();
//        for(ViewName locale : locales){
//            System.out.println(locale);
//
//            Locale country = new Locale("", locale);
//            System.out.println(country.getCountry() + " --- " + country.getDisplayCountry());
//        }

        List<String> countries = Arrays.stream(Locale.getISOCountries()).map(x -> new Locale("", x).getDisplayCountry()).collect(Collectors.toList());

        for (String c : countries){
            System.out.println(c);
        }
    }

    @Test
    @Commit
    @Ignore
    public void photoTest() throws IOException {
        assertNotNull(sessionFactory);
        Photo p = new Photo();


        File file = new File("C:\\Users\\dzdun\\Pictures\\picard2.jpg");

        FileInputStream fis = new FileInputStream(file);




        p.setName(file.getName());
        p.setImage(Files.readAllBytes(file.toPath()));

        sessionFactory.getCurrentSession().saveOrUpdate(p);
    }

    @Test
    @Ignore
    public void readPhotoTest() throws IOException {
        Photo p = (Photo)sessionFactory.getCurrentSession().get(Photo.class, 8L);
        byte[] image = p.getImage();

        BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(image));
        File outputFile = new File("C:\\Users\\dzdun\\Pictures\\outputtedGraduation.jpg");
        ImageIO.write(buffImage, "jpg", outputFile);

    }

    @Test
    @Ignore
    public void photoDemoTest() throws IOException {



            byte[] imageInByte;
            BufferedImage originalImage = ImageIO.read(new File(
                    "C:\\Users\\dzdun\\Pictures\\Danny_Dunnn_Passport.jpg"));

            // convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();

            // convert byte array back to BufferedImage
            InputStream in = new ByteArrayInputStream(imageInByte);
            BufferedImage bImageFromConvert = ImageIO.read(in);

            ImageIO.write(bImageFromConvert, "jpg", new File(
                    "C:\\Users\\dzdun\\Pictures\\TESTING.jpg"));


    }

}

