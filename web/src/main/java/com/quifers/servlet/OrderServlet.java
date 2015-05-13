package com.quifers.servlet;

import com.quifers.db.DatabaseHelper;
import com.quifers.domain.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.quifers.listener.StartupContextListener.DATABASE_HELPER;

public class OrderServlet extends HttpServlet {

    private DatabaseHelper databaseHelper;

    @Override
    public void init() throws ServletException {
        databaseHelper = (DatabaseHelper) getServletContext().getAttribute(DATABASE_HELPER);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String clientName = request.getParameter("client_name");
            long mobileNumber = Long.valueOf(request.getParameter("mobile_number"));
            String email = request.getParameter("email");
            Client client = new Client(clientName, mobileNumber, email);
            databaseHelper.saveClient(client);
            Client savedClient = databaseHelper.getClientByMobile(client.getMobileNumber());
            response.getWriter().write(String.format("ClientId:[%d]%nName:[%s]%nMobile:[%d]%nEmail:[%s]",savedClient.getClientId(),
                    savedClient.getName(),savedClient.getMobileNumber(),savedClient.getEmail()));
        } catch (SQLException e) {
            response.getWriter().write("Unable to save client.");
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
