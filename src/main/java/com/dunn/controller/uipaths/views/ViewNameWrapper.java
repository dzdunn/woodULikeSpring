package com.dunn.controller.uipaths.views;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ViewNameWrapper {

    private final static List<String> publicViewUrls = new ArrayList<>();

    private final static List<String> ALL_STRINGS = new ArrayList<>();

    private String viewName;

    private boolean isPublic;

    private boolean hasAllSubDirectoriesPublic;


    public ViewNameWrapper(final String viewName) {
        this.viewName = viewName;
    }

    public ViewNameWrapper(final String viewName, final boolean isPublic) {
        this.viewName = viewName;
        this.isPublic = isPublic;
    }

    public ViewNameWrapper(final String viewName, final boolean isPublic, final boolean hasAllSubDirectoriesPublic) {
        this.viewName = viewName;
        this.isPublic = isPublic;
        this.hasAllSubDirectoriesPublic = hasAllSubDirectoriesPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getViewName() {
        return viewName;
    }

    public boolean hasAllSubDirectoriesPublic() {
        return hasAllSubDirectoriesPublic;
    }

    public static List<String> getAllStrings() {
        if (ALL_STRINGS.isEmpty()) {
            Field[] fields = ViewName.class.getDeclaredFields();
            try {
                for (Field field : fields) {
                    if (Modifier.isPublic(field.getModifiers()) && field.getType().equals(String.class)) {
                        ALL_STRINGS.add((String) field.get(String.class));
                    }
                }
            } catch (IllegalAccessException e) {

            }
        }
        return ALL_STRINGS;
    }


    public static List<String> getPublicViewUrls() {
        if (publicViewUrls.isEmpty()) {
            Field[] fields = ViewName.class.getDeclaredFields();
            try {
                for (Field field : fields) {

                    if (Modifier.isPublic(field.getModifiers()) && field.getType().equals(ViewNameWrapper.class)) {
                        ViewNameWrapper viewNameWrapper = (ViewNameWrapper) field.get(ViewNameWrapper.class);
                        if (viewNameWrapper.isPublic()) {
                            String viewName = ((ViewNameWrapper) field.get(ViewNameWrapper.class)).getViewName();

                            publicViewUrls.add(viewName);
                            if (viewNameWrapper.hasAllSubDirectoriesPublic()){
                                publicViewUrls.add(viewName + "/**");
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {

            }
        }
        return publicViewUrls;
    }

    public static String[] getPublicViewUrlsArray() {
        String[] publicViewUrlsArray = new String[getPublicViewUrls().size()];
        publicViewUrlsArray = getPublicViewUrls().toArray(publicViewUrlsArray);
        return publicViewUrlsArray;
    }

}
