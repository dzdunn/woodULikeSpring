package com.dunn.controller.path;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ViewNameWrapper {

    private final static List<String> publicViewUrls = new ArrayList<>();

    private String viewName;

    private boolean isPublic;


    public ViewNameWrapper(final String viewName){
        this.viewName = viewName;
    }

    public ViewNameWrapper(final String viewName, final boolean isPublic){
        this.viewName = viewName;
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getViewName() {
        return viewName;
    }


    public static List<String> getPublicViewUrls(){
        Field[] fields = ViewName.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (Modifier.isPublic(field.getModifiers()) && field.getType().equals(ViewNameWrapper.class)) {
                    if (((ViewNameWrapper) field.get(ViewNameWrapper.class)).isPublic()) {
                        publicViewUrls.add(((ViewNameWrapper) field.get(ViewNameWrapper.class)).getViewName());
                    }
                }
            }
        } catch(IllegalAccessException e){

        }
        return publicViewUrls;
    }

    public static String[] getPublicViewUrlsArray(){
        String[] publicViewUrlsArray = new String[getPublicViewUrls().size()];
        publicViewUrlsArray = getPublicViewUrls().toArray(publicViewUrlsArray);
        return publicViewUrlsArray;
    }
}
