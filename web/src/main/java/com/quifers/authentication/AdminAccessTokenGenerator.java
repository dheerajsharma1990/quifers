package com.quifers.authentication;

import com.quifers.domain.AdminAccount;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdminAccessTokenGenerator {

    public String generateAccessToken(AdminAccount adminAccount) throws NoSuchAlgorithmException {
        String combined = adminAccount.getUserId() + "" + adminAccount.getPassword();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(combined.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
