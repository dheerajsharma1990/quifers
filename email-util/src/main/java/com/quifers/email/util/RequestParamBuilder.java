package com.quifers.email.util;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static java.net.URLEncoder.encode;

public class RequestParamBuilder {

    private final List<Pair<String, String>> paramList = new ArrayList<>();

    public RequestParamBuilder addParam(String paramName, String paramValue) throws UnsupportedEncodingException {
        paramList.add(new Pair(paramName, encode(paramValue, "UTF-8")));
        return this;
    }

    public String build() {
        Iterator<Pair<String, String>> entryIterator = paramList.iterator();
        StringBuilder builder = new StringBuilder();
        while (entryIterator.hasNext()) {
            Pair<String, String> pair = entryIterator.next();
            builder.append(pair.getK()).append("=").append(pair.getV());
            if (entryIterator.hasNext()) {
                builder.append("&");
            }
        }
        return builder.toString();
    }

}
