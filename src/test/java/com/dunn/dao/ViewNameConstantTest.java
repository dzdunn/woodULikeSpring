package com.dunn.dao;

import com.dunn.config.webapp.WebMvcConfig;
import com.dunn.controller.path.resources.ResourceHandler;
import com.dunn.controller.path.resources.ResourceProperties;
import com.dunn.controller.path.resources.ResourcePropertiesHolder;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@RunWith(BlockJUnit4ClassRunner.class)
@SpringJUnitWebConfig(classes = {WebMvcConfig.class})
public class ViewNameConstantTest {

    private final Path tempDirectory = Paths.get("createWoodProjectTemp");

    private final Path tempCreate = Paths.get("tempCreate/resources");

    private final Path tempWebjars = Paths.get("tempCreate/webjars");

    private final Path testPath = Paths.get("C:\\Users\\dzdun\\JAVA_RESOURCES\\apache-tomcat-9.0.12\\bin\\TEST");

    @Test
    public void testResource() throws IOException {

        Path resourcesPath = getFileNamePath("test1.jpg", tempCreate);

        Path webjarsPath = getFileNamePath("test1.jpg", tempWebjars);

        Path createWebProjectTempPath = getFileNamePath("test1.jpg", tempDirectory);

        System.out.println(replaceRootWithResourceHandler(resourcesPath, ResourceProperties.STATIC_PROPERTIES.getResourcePropertiesHolder()));

        System.out.println(replaceRootWithResourceHandler(webjarsPath, ResourceProperties.STATIC_PROPERTIES.getResourcePropertiesHolder()));

        System.out.println(
                replacebackSlashWithForwardSlash(
                        replaceRootWithResourceHandler(createWebProjectTempPath, ResourceProperties.CREATE_WOOD_PROJECT_TEMP_PROPERTIES.getResourcePropertiesHolder())
                )
        );

        File f = getFileNamePath("test2.jpg", tempCreate).toFile();
        System.out.println(f.toURI());


    }


    private Path getFileNamePath(String fileName, Path rootPath){
        return rootPath.resolve(fileName);
    }

    private Path replaceRootWithResourceHandler(Path pathToReplace, ResourcePropertiesHolder resourcePropertiesHolder){

        String[] resourceLocations = resourcePropertiesHolder.getResourceLocations();

        StringBuilder regexBuilder = new StringBuilder();
        regexBuilder.append(".*");

        for(int i = 0; i < resourceLocations.length; i++){
            String location = resourceLocations[i].replaceAll("/", "");
            regexBuilder.append(location);
            if(resourceLocations.length > 1 && i != resourceLocations.length-1){
                regexBuilder.append("|.*");
            }
        }
        //System.out.println("Regex: " + regexBuilder.toString());
        String resourceHandlerReplacement = resourcePropertiesHolder.getResourceHandler().getResourceHandlerString().replaceAll("/|\\*", "");
        String path = pathToReplace.toString().replaceAll(regexBuilder.toString(), resourceHandlerReplacement);

        System.out.println(path);

        return Paths.get(pathToReplace.toString().replaceAll(regexBuilder.toString(), resourceHandlerReplacement));
    }

    public static String replacebackSlashWithForwardSlash(Path pathToReplace){
        return pathToReplace.toString().replaceAll("\\\\", "/");
    }

    @Test
    public void testDirectoryDelete() throws IOException, InterruptedException {
        Path path = Files.createDirectories(testPath);
        System.out.println(path.toAbsolutePath());

      //  Thread.sleep(3000);

        Files.delete(path);

    }

//    private void getDirectories(Path path, Path tempCreate) throws IOException {
////        System.out.println("Raw path: " + path);
////        System.out.println("Absolute path: " + path.toAbsolutePath());
////        System.out.println("URI: " + path.toUri());
////        System.out.println("Parent: " + path.getParent());
////        System.out.println("Root: " + path.getRoot());
////        System.out.println("Filename: " + path.getFileName());
////        System.out.println();
////        System.out.println("******************************************************");
////        Files.walk(path.toAbsolutePath()).forEach(x -> System.out.println("Files.walk: " + x));
////        System.out.println();
////        System.out.println("******************************************************");
////        Files.walk(path).forEach(x -> System.out.println("FILES.WALK: " + x));
////
////        System.out.println("******************************************************");
//
////        Arrays.stream(ResourceProperties.CREATE_WOOD_PROJECT_TEMP_PROPERTIES.getResourceLocations()).forEach(x -> System.out.println(x));
//
//        for(String x : ResourceProperties.CREATE_WOOD_PROJECT_TEMP_PROPERTIES.getResourceLocations()){
//            Path tmpPath = Paths.get(x);
//            System.out.println("tmpPath: " + tmpPath);
//            System.out.println("tmpPath URI: " + tmpPath.toUri());
//            System.out.println("tmpPath Absolute: " + tmpPath.toAbsolutePath());
//
//        }
//
//    }
//
//    private String resolveDirectory(String fileName, String rootDirectory, ResourcePropertiesHolder resourcePropertiesHolder){
//        StringBuilder regexBuilder = new StringBuilder();
//        regexBuilder.append(".*");
//        String[] directories = resourcePropertiesHolder.getResourceLocations();
//
//        for (int i = 0; i < directories.length; i++){
//            regexBuilder.append(directories[i]);
//            if (directories.length > 1 && i != directories.length-1){
//
//                regexBuilder.append("|");
//                regexBuilder.append(".*");
//            }
//        }
//
//        System.out.println("Regex: " + regexBuilder.toString());
//
//        String root = removePatternMatcher(resourcePropertiesHolder.getResourceHandler());
//
//        System.out.println("Root: " + root);
//
//
//        System.out.println(Paths.get(root).resolve(fileName).toUri().toString());
//
//        String result = Paths.get(root).resolve(fileName).toUri().toString().replaceAll(regexBuilder.toString(), root);
//
//        System.out.println("Result: " + result);
//
//
//        return Paths.get(rootDirectory).resolve(fileName).toUri().toString().replaceAll(".*upload-dir", ("/" + root));
//    }
//
//    private String removePatternMatcher(ResourceHandler resourceHandler){
//        return resourceHandler.getResourceHandlerString().replaceAll("/\\*\\*", "");
//    }
//
//    //Step 1) Resolve full directory given Mapped resource and filename
//
//    private String resolveFullDirectory(ResourceProperties resourceProperties, String fileName){
//        String[] resourceLocations = resourceProperties.getResourcePropertiesHolder().getResourceLocations();
//        return resourceLocations.toString();
//    }
}
