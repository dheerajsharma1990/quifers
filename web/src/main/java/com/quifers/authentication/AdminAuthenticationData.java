package com.quifers.authentication;

import java.util.HashMap;
import java.util.Map;

public class AdminAuthenticationData {

    private static final Map<String, String> adminAccessTokenMap = new HashMap<>();
    private static final Map<String, String> fieldExecutiveAccessTokenMap = new HashMap<>();

    public static void putAdminAccessToken(String userId, String accessToken) {
        adminAccessTokenMap.put(userId, accessToken);
    }

    public static void putFieldExecutiveToken(String userId, String accessToken) {
        fieldExecutiveAccessTokenMap.put(userId, accessToken);
    }

    public static boolean isValidAdminAccessToken(String userId, String accessToken) {
        return adminAccessTokenMap.containsKey(userId) && adminAccessTokenMap.get(userId).equals(accessToken);
    }

    public static boolean isValidFieldExecutiveAccessToken(String userId, String accessToken) {
        return fieldExecutiveAccessTokenMap.containsKey(userId) && fieldExecutiveAccessTokenMap.get(userId).equals(accessToken);
    }

}
