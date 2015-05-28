package com.quifers.authentication;

import java.util.HashMap;
import java.util.Map;

public class AdminAuthenticationData {

    private static final Map<String, String> accessTokenMap = new HashMap<>();

    public static void putAccessToken(String userId, String accessToken) {
        accessTokenMap.put(userId, accessToken);
    }

    public static boolean isValidAccessToken(String userId, String accessToken) {
        return accessTokenMap.containsKey(userId) && accessTokenMap.get(userId).equals(accessToken);
    }

}
