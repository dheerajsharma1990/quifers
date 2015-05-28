package com.quifers.servlet.admin;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.domain.FieldExecutive;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.quifers.listener.StartupContextListener.FIELD_EXECUTIVE_DAO;

public class AdminServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServlet.class);
    private FieldExecutiveDao fieldExecutiveDao;

    @Override
    public void init() throws ServletException {
        fieldExecutiveDao = (FieldExecutiveDao) getServletContext().getAttribute(FIELD_EXECUTIVE_DAO);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<FieldExecutive> allFieldExecutives = fieldExecutiveDao.getAllFieldExecutives();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("field_executives", allFieldExecutives);
            response.setContentType("application/json");
            response.getWriter().write(jsonObject.toString());
        } catch (SQLException e) {
            LOGGER.error("Error occurred in field executives.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
