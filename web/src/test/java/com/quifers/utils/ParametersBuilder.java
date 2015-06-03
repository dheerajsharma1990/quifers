package com.quifers.utils;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import com.quifers.hibernate.FieldExecutiveDaoImpl;
import com.quifers.hibernate.OrderDaoImpl;
import com.quifers.hibernate.SessionFactoryBuilder;
import com.quifers.properties.Environment;
import org.hibernate.SessionFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParametersBuilder {

    private final Map<String, String> parameters = new HashMap<>();

    public ParametersBuilder add(String paramName, String paramValue) {
        parameters.put(paramName, paramValue);
        return this;
    }

    public String build() throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, String>> entryIterator = parameters.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, String> entry = entryIterator.next();
            builder.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            if (entryIterator.hasNext()) {
                builder.append("&");
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryBuilder.getSessionFactory(Environment.LOCAL);
        OrderDao orderDao = new OrderDaoImpl(sessionFactory,new FieldExecutiveDaoImpl(sessionFactory));
        Order order = new Order();
        order.setOrderId(1l);
        OrderWorkflow workflow = new OrderWorkflow(1l, OrderState.TRIP_STARTED,new Date());
        order.addOrderWorkflow(workflow);
        orderDao.saveOrder(order);
        Order orderFromDb = orderDao.getOrder(1l);
        System.out.println();
    }

}
