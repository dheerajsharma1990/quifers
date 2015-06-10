package com.quifers.email.helpers;

import com.quifers.email.util.Credentials;
import com.quifers.email.util.CredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.TimerTask;

public class CredentialsRefresherTask extends TimerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsRefresherTask.class);

    private final CredentialsService credentialsService;
    private final CredentialsRefresher refresher;

    public CredentialsRefresherTask(CredentialsService credentialsService, CredentialsRefresher refresher) {
        this.credentialsService = credentialsService;
        this.refresher = refresher;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("Refreshing credentials...");
            Credentials credentials = credentialsService.getCredentials();
            Credentials newCredentials = refresher.getRefreshedCredentials(credentials);
            credentialsService.setCredentials(newCredentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
