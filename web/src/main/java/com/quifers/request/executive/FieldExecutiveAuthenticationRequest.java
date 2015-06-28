package com.quifers.request.executive;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.ApiRequest;

public class FieldExecutiveAuthenticationRequest implements ApiRequest {

    private final FieldExecutiveId fieldExecutiveId;
    private final String accessToken;

    public FieldExecutiveAuthenticationRequest(FieldExecutiveId fieldExecutiveId, String accessToken) {
        this.fieldExecutiveId = fieldExecutiveId;
        this.accessToken = accessToken;
    }

    public FieldExecutiveId getFieldExecutiveId() {
        return fieldExecutiveId;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
