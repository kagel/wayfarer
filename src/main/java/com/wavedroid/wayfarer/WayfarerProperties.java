package com.wavedroid.wayfarer;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author DKhvatov
 */
@SuppressWarnings("UnusedDeclaration")
public class WayfarerProperties {

    static {
        ResourceBundle rb = ResourceBundle.getBundle("wayfarer");
        for (Field field : WayfarerProperties.class.getDeclaredFields()) {
            try {
                if (field.getType().isAssignableFrom(List.class)) {
                    List<String> list = new LinkedList<>();
                    String item;
                    try {
                        for (int i = 1; (item = rb.getString(field.getName() + "." + i)) != null; i++)
                            list.add(item);
                    } catch (MissingResourceException ignored) {
                    }
                    field.set(WayfarerProperties.class, list);
                } else
                    field.set(WayfarerProperties.class, rb.getString(field.getName()));
            } catch (IllegalAccessException iae) {
                iae.printStackTrace();
            }
        }
    }

    private static String debug;
    private static String clientId;
    private static String clientSecret;
    private static String accessToken;
    private static String redirectUrl;
    private static String startVenueId;
    private static String userId;
    private static List<String> homeVenues;

    public static boolean isDebug() {
        return Boolean.valueOf(debug);
    }

    public static String getClientId() {
        return clientId;
    }

    public static String getClientSecret() {
        return clientSecret;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static String getRedirectUrl() {
        return redirectUrl;
    }

    public static String getStartVenueId() {
        return startVenueId;
    }

    public static String getUserId() {
        return userId;
    }

    public static List<String> getHomeVenues() {
        return homeVenues;
    }

}
