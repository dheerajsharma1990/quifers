package com.quifers.response;

import com.quifers.domain.FieldExecutive;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FieldExecutiveResponse {

    public static String getSuccessResponse() {
        JSONObject object = new JSONObject();
        object.put("success", "true");
        return object.toString();
    }

    public String getAllFieldExecutivesResponse(List<FieldExecutive> fieldExecutives) {
        List<FieldExecutiveListAll> listAlls = new ArrayList<>();
        for (FieldExecutive fieldExecutive : fieldExecutives) {
            listAlls.add(new FieldExecutiveListAll(fieldExecutive.getAccount().getUserId(),fieldExecutive.getName(),fieldExecutive.getMobileNumber()));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field_executives", listAlls);
        return jsonObject.toString();
    }


}
