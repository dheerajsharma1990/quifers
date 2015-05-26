package com.quifers.email.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CredentialServiceFactory {

    private final JsonParser jsonParser;

    public CredentialServiceFactory(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }


}
