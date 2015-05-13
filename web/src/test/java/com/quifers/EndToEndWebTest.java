package com.quifers;

import org.h2.tools.Server;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.quifers.runners.DatabaseRunner.runDatabaseServer;
import static com.quifers.runners.JettyRunner.runJettyServer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EndToEndWebTest {
    private Server server;

    @Test
    public void shouldSaveAndGetClient() throws Exception {
        HttpURLConnection connection = getConnection("http://localhost:9111/bookOrder");

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        String content = "client_name=Dheeraj&mobile_number=9999770595&email=dheerajsharma1990@gmail.com&from_address=" + URLEncoder.encode("123 HBC,Faridabad", "UTF-8")
                + "&to_address=" + URLEncoder.encode("456 HBC@Delhi", "UTF-8") + "&booking_date=" + URLEncoder.encode("22/09/1990 10:20:30", "UTF-8");
        outputStream.writeBytes(content);
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        DataInputStream inputStream = new DataInputStream(connection.getInputStream());
        assertThat(responseCode, is(200));
        System.out.println(inputStream.readLine());
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
