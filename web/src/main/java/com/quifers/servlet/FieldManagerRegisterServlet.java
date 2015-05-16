package com.quifers.servlet;

import com.quifers.db.DatabaseHelper;
import com.quifers.domain.FieldManager;
import com.quifers.domain.FieldManagerAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
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
            FieldManagerAccount account = new FieldManagerAccount(userId, password);
            FieldManager fieldManager = new FieldManager(userId, name, mobileNumber);
            databaseHelper.save(account);
            databaseHelper.save(fieldManager);
            List<FieldManagerAccount> accounts = databaseHelper.getObjects(FieldManagerAccount.class, "userId", userId);
            List<FieldManager> fieldManagers = databaseHelper.getObjects(FieldManager.class, "userId", userId);
            LOGGER.info("FieldManagerAccount: {}", accounts.iterator().next());
            LOGGER.info("FieldManagerDetails: {}", fieldManagers.iterator().next());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
