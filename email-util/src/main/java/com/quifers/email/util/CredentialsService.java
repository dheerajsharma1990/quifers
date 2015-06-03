package com.quifers.email.util;

public class CredentialsService {

    private Credentials credentials;

    public static CredentialsService SERVICE = new CredentialsService();

    private CredentialsService() {
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
