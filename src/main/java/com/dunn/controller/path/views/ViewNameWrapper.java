package com.dunn.controller.path.views;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ViewNameWrapper {

    private final static List<String> publicViewUrls = new ArrayList<>();

    private final static List<String> ALL_STRINGS = new ArrayList<>();

    private String string;

    private boolean isPublic;


    public ViewNameWrapper(final String string){
        this.string = string;
    }

    public ViewNameWrapper(final String string, final boolean isPublic){
        this.string = string;
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getString() {
        return string;
    }

    public static List<String> getAllStrings(){
        if(ALL_STRINGS.isEmpty()) {
            Field[] fields = ViewName.class.getDeclaredFields();
            try {
                for (Field field : fields) {
                    if (Modifier.isPublic(field.getModifiers()) && field.getType().equals(String.class)) {
                        ALL_STRINGS.add((String)field.get(String.class));
                    }
                }
            } catch (IllegalAccessException e){

            }
        }
        return ALL_STRINGS;
    }


    public static List<String> getPublicViewUrls(){
        if(publicViewUrls.isEmpty()) {
            Field[] fields = ViewName.class.getDeclaredFields();
            try {
                for (Field field : fields) {
                    if (Modifier.isPublic(field.getModifiers()) && field.getType().equals(ViewNameWrapper.class)) {
                        if (((ViewNameWrapper) field.get(ViewNameWrapper.class)).isPublic()) {
                            publicViewUrls.add(((ViewNameWrapper) field.get(ViewNameWrapper.class)).getString());
                        }
                    }
                }
            } catch (IllegalAccessException e) {

            }
        }
        return publicViewUrls;
    }

    public static String[] getPublicViewUrlsArray(){
        String[] publicViewUrlsArray = new String[getPublicViewUrls().size()];
        publicViewUrlsArray = getPublicViewUrls().toArray(publicViewUrlsArray);
        return publicViewUrlsArray;
    }
}
