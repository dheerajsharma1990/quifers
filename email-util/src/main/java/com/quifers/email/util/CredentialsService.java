package com.quifers.email.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CredentialsService {

    public static File DEFAULT_DIR = new File("./target/credentials.json");

    private static Credentials credentials;

    public CredentialsService(File credentialFile, JsonParser jsonParser) throws IOException {
        if (!credentialFile.exists()) {
            throw new FileNotFoundException("Credentials file not present.Kindly generate it." + credentialFile.getName());
        }
        credentials = jsonParser.parse(FileUtils.readFileToString(credentialFile));
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials newCredentials) {
        credentials = newCredentials;
    }
}
