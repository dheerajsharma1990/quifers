package com.quifers.email.properties;

import java.util.Properties;

public class EmailUtilProperties {

    private final Properties properties;

    public EmailUtilProperties(Properties properties) {
        this.properties = properties;
    }

    public String getClientId() {
        return properties.getProperty("CLIENT_ID");
    }

    public String getClientSecretKey() {
        return properties.getProperty("CLIENT_SECRET_KEY");
    }

    public String getCallbackUrl() {
        return properties.getProperty("CALLBACK_URL");
    }

    public String getEmailAccount() {
        return properties.getProperty("EMAIL_ACCOUNT");
    }

    public String getActiveMqUrl() {
        return properties.getProperty("ACTIVEMQ_URL");
    }

    public String getEmailQueueName() {
        return properties.getProperty("EMAIL_QUEUE_NAME");
    }

    public String getRefreshToken() {
        return properties.getProperty("REFRESH_TOKEN");
    }

}
