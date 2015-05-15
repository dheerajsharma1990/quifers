package com.quifers;

import com.quifers.utils.ParametersBuilder;
import org.h2.tools.Server;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.quifers.runners.DatabaseRunner.runDatabaseServer;
import static com.quifers.runners.JettyRunner.runJettyServer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EndToEndWebTest {

    private Server server;

    @Test
    public void shouldSaveAndGetOrder() throws Exception {
        //given
        HttpURLConnection connection = getConnection("http://localhost:9111/bookOrder");
        String request = buildRequest();

        //when
        int responseCode = sendRequest(connection, request);

        //then
        assertThat(responseCode, is(200));
    }

    private String buildRequest() throws UnsupportedEncodingException {
        return new ParametersBuilder().add("client_name", "Dheeraj Sharma")
                .add("mobile_number", "9999770595")
                .add("email", "dheerajsharma1990@gmail.com")
                .add("from_adddress", "House Number 1234, Faridabad")
                .add("to_address", "Houser Number 4567, Gurgaon")
                .add("booking_date", "22/09/1990 10:20:30").build();
    }

    private int sendRequest(HttpURLConnection connection, String request) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(request);
        outputStream.flush();
        outputStream.close();
        return connection.getResponseCode();
    }

    private HttpURLConnection getConnection(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        return connection;
    }

    @BeforeClass
    public void startServerAndDatabase() throws Exception {
        server = runDatabaseServer();
        runJettyServer(9111);

    }

    @AfterClass
    public void shutDownDatabase() {
        server.stop();
        server.shutdown();
    }
}
