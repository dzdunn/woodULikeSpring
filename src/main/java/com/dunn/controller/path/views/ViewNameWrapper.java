package com.dunn.controller.path.views;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ViewNameWrapper {

    private final static List<java.lang.String> publicViewUrls = new ArrayList<>();

    private final static List<java.lang.String> ALL_STRINGS = new ArrayList<>();

    private java.lang.String string;

    private boolean isPublic;


    public ViewNameWrapper(final java.lang.String string){
        this.string = string;
    }

    public ViewNameWrapper(final java.lang.String string, final boolean isPublic){
        this.string = string;
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public java.lang.String getString() {
        return string;
    }

    public static List<java.lang.String> getAllStrings(){
        if(ALL_STRINGS.isEmpty()) {
            Field[] fields = ViewName.class.getDeclaredFields();
            try {
                for (Field field : fields) {
                    if (Modifier.isPublic(field.getModifiers()) && field.getType().equals(java.lang.String.class)) {
                        ALL_STRINGS.add((java.lang.String)field.get(java.lang.String.class));
                    }
                }
            } catch (IllegalAccessException e){

            }
        }
        return ALL_STRINGS;
    }


    public static List<java.lang.String> getPublicViewUrls(){
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

    public static java.lang.String[] getPublicViewUrlsArray(){
        java.lang.String[] publicViewUrlsArray = new java.lang.String[getPublicViewUrls().size()];
        publicViewUrlsArray = getPublicViewUrls().toArray(publicViewUrlsArray);
        return publicViewUrlsArray;
    }
}
