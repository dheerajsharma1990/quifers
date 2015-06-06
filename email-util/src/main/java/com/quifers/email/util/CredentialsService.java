package com.quifers.email.util;

public class CredentialsService {

    private static Credentials credentials;

    private CredentialsService() {
    }

    public static Credentials getCredentials() {
        return credentials;
    }

    public static void setCredentials(Credentials newCredentials) {
        credentials = newCredentials;
    }
}
