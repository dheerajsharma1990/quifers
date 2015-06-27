package com.quifers.authentication;

import com.quifers.domain.id.AdminId;
import com.quifers.domain.id.FieldExecutiveId;

import java.util.HashMap;
import java.util.Map;

public class AdminAuthenticationData {

    private static final Map<AdminId, String> adminAccessTokenMap = new HashMap<>();
    private static final Map<FieldExecutiveId, String> fieldExecutiveAccessTokenMap = new HashMap<>();

    public static void putAdminAccessToken(AdminId adminId, String accessToken) {
        adminAccessTokenMap.put(adminId, accessToken);
    }

    public static void putFieldExecutiveToken(FieldExecutiveId fieldExecutiveId, String accessToken) {
        fieldExecutiveAccessTokenMap.put(fieldExecutiveId, accessToken);
    }

    public static boolean isValidAdminAccessToken(AdminId adminId, String accessToken) {
        return adminAccessTokenMap.containsKey(adminId) && adminAccessTokenMap.get(adminId).equals(accessToken);
    }

    public static boolean isValidFieldExecutiveAccessToken(FieldExecutiveId fieldExecutiveId, String accessToken) {
        return fieldExecutiveAccessTokenMap.containsKey(fieldExecutiveId) && fieldExecutiveAccessTokenMap.get(fieldExecutiveId).equals(accessToken);
    }

}
