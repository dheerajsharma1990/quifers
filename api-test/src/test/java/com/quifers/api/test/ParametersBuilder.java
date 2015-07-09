package com.quifers.api.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

}
