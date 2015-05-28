package com.quifers;

import com.quifers.properties.Environment;
import com.quifers.properties.PropertiesLoader;
import com.quifers.properties.QuifersProperties;
import com.quifers.utils.ParametersBuilder;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.quifers.runners.DatabaseRunner.runDatabaseServer;
import static com.quifers.runners.DatabaseRunner.stopDatabaseServer;
import static com.quifers.runners.JettyRunner.runJettyServer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EndToEndWebTest {

    private static final String BASE_URL = "http://localhost:9111";

    @Test
    public void shouldRegisterFieldExecutive() throws Exception {
        //given
        HttpURLConnection connection = getConnection(BASE_URL + "/admin/registerFieldExecutive");
        String request = buildFieldExecutiveAccount();

        //when
        int responseCode = sendRequest(connection, request);

        assertThat(responseCode, is(200));
    }

    @Test(dependsOnMethods = "shouldRegisterFieldExecutive")
    public void shouldSaveOrder() throws Exception {
        //given
        HttpURLConnection connection = getConnection(BASE_URL + "/api/v0/guest/order/book");
        String request = buildRequest();

        //when
        int responseCode = sendRequest(connection, request);

        //then
        assertThat(responseCode, is(200));
    }

    @Test(dependsOnMethods = "shouldSaveOrder")
    public void shouldAssignOrderToFieldManager() throws Exception {
        //given
        HttpURLConnection connection = getConnection(BASE_URL + "/assignFieldManager");
        String request = buildAssignFieldManagerRequest();

        //when
        int responseCode = sendRequest(connection, request);

        //then
        assertThat(responseCode, is(200));
    }

    @Test
    public void shouldRegisterNewAdmin() throws Exception {
        //given
        HttpURLConnection connection = getConnection(BASE_URL + "/api/v0/guest/admin/register");
        String request = buildNewAdminRegisterRequest();

        //when
        int responseCode = sendRequest(connection, request);

        //then
        assertThat(responseCode, is(200));
    }

    @Test(dependsOnMethods = "shouldRegisterNewAdmin")
    public void shouldSendInvalidAuthenticationOnAdminLogin() throws Exception {
        //given
        HttpURLConnection connection = getConnection(BASE_URL + "/api/v0/guest/admin/login");
        String request = buildInvalidAdminLoginRequest();

        //when
        int responseCode = sendRequest(connection, request);

        //then
        assertThat(responseCode, is(401));
    }

    @Test(dependsOnMethods = "shouldRegisterNewAdmin")
    public void shouldValidateAuthenticationOnAdminLogin() throws Exception {
        //given
        HttpURLConnection connection = getConnection(BASE_URL + "/api/v0/guest/admin/login");
        String request = buildValidAdminLoginRequest();

        //when
        int responseCode = sendRequest(connection, request);

        //then
        assertThat(responseCode, is(200));
        String response = IOUtils.toString(connection.getInputStream());
        assertThat(response, is("{\"access_token\":\"297f7024a516256a526bd6b9f2d3f15c\"}"));
    }

    @Test(dependsOnMethods = "shouldValidateAuthenticationOnAdminLogin")
    public void shouldGetAllFieldExecutives() throws Exception {
        //given
        HttpURLConnection connection = getConnection(BASE_URL + "/api/v0/admin/executives/listAll");
        String request = buildValidFieldExecutivesGetAllRequest();

        //when
        int responseCode = sendRequest(connection, request);

        //then
        assertThat(responseCode, is(200));
        String response = IOUtils.toString(connection.getInputStream());
        JSONTokener jsonTokener = new JSONTokener(response);
        JSONObject object = new JSONObject(jsonTokener);
        JSONArray executives = object.getJSONArray("field_executives");
        JSONObject executive = executives.getJSONObject(0);
        assertThat(executive.get("name").toString(), is("Dheeraj Sharma"));

    }

    private String buildFieldExecutiveAccount() throws UnsupportedEncodingException {
        return new ParametersBuilder().add("userId", "dheerajsharma1990")
                .add("password", "mypassword")
                .add("name", "Dheeraj Sharma")
                .add("mobileNumber", "9999770595")
                .build();
    }

    private String buildRequest() throws UnsupportedEncodingException {
        return new ParametersBuilder().add("client_name", "Dheeraj Sharma")
                .add("mobile_number", "9999770595")
                .add("email", "dheerajsharma1990@gmail.com")
                .add("from_address", "House Number 1234, Faridabad")
                .add("to_address", "House Number 4567, Gurgaon")
                .add("booking_date", "22/09/1990 10:20:30").build();
    }

    private String buildAssignFieldManagerRequest() throws UnsupportedEncodingException {
        return new ParametersBuilder().add("order_id", "1")
                .add("field_executive_id", "dheerajsharma1990").build();
    }

    private String buildNewAdminRegisterRequest() throws UnsupportedEncodingException {
        return new ParametersBuilder().add("user_id", "dheerajsharma1990")
                .add("password", "mypassword")
                .add("name", "Dheeraj Sharma")
                .add("mobile_number", "9999770595").build();
    }

    private String buildInvalidAdminLoginRequest() throws UnsupportedEncodingException {
        return new ParametersBuilder().add("user_id", "dheerajsharma1990")
                .add("password", "mywrongpassword").build();
    }

    private String buildValidAdminLoginRequest() throws UnsupportedEncodingException {
        return new ParametersBuilder().add("user_id", "dheerajsharma1990")
                .add("password", "mypassword").build();
    }

    private String buildValidFieldExecutivesGetAllRequest() throws UnsupportedEncodingException {
        return new ParametersBuilder().add("user_id", "dheerajsharma1990")
                .add("accessToken", "297f7024a516256a526bd6b9f2d3f15c").build();
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
        QuifersProperties quifersProperties = PropertiesLoader.loadProperties(Environment.LOCAL);
        runDatabaseServer(quifersProperties);
        runJettyServer(9111);

    }

    @AfterClass
    public void shutDownDatabase() {
        stopDatabaseServer();
    }
}
