package com.quifers.servlet;

import com.quifers.db.DatabaseHelper;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.quifers.listener.StartupContextListener.DATABASE_HELPER;

public class FieldManagerRegisterServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldManagerRegisterServlet.class);

    private DatabaseHelper databaseHelper;

    @Override
    public void init() throws ServletException {
        databaseHelper = (DatabaseHelper) getServletContext().getAttribute(DATABASE_HELPER);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userId = request.getParameter("user_id");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            long mobileNumber = Long.valueOf(request.getParameter("mobile_number"));
            FieldExecutiveAccount account = new FieldExecutiveAccount(userId, password);
            FieldExecutive fieldExecutive = new FieldExecutive(userId, name, mobileNumber);
            databaseHelper.save(account);
            databaseHelper.save(fieldExecutive);
            List<FieldExecutiveAccount> accounts = databaseHelper.getObjects(FieldExecutiveAccount.class, "userId", userId);
            List<FieldExecutive> fieldExecutives = databaseHelper.getObjects(FieldExecutive.class, "userId", userId);
            LOGGER.info("FieldManagerAccount: {}", accounts.iterator().next());
            LOGGER.info("FieldManagerDetails: {}", fieldExecutives.iterator().next());
        } catch (Throwable e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal server error has occurred.");
            LOGGER.error("An error occurred.", e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
