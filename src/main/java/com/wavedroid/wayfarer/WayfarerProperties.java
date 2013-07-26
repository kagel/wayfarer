package com.wavedroid.wayfarer;

import java.lang.reflect.Field;
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
}
