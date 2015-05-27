package com.quifers.email.helpers;

import com.quifers.email.util.Credentials;
import com.quifers.email.util.CredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.TimerTask;

public class CredentialsRefresherTask extends TimerTask {

    private final CredentialsRefresher refresher;
    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsRefresherTask.class);

    public CredentialsRefresherTask(CredentialsRefresher refresher) {
        this.refresher = refresher;
    }

    @Override
    public void run() {
        try {
            Credentials credentials = CredentialsService.SERVICE.getCredentials();
            Credentials newCredentials = refresher.getRefreshedCredentials(credentials);
            LOGGER.info("New Credentials: {}", credentials);
            CredentialsService.SERVICE.setCredentials(newCredentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
