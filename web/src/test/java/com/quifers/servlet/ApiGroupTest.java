package com.quifers.servlet;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ApiGroupTest {

    @Test
    public void shouldReturnGuestApiGroup() throws CommandNotFoundException {
        //when
        ApiGroup apiGroup = ApiGroup.getMatchingApiGroup("/api/v0/guest/my/api");

        //then
        assertThat(apiGroup, is(ApiGroup.GUEST));
    }

    @Test
    public void shouldReturnAdminApiGroup() throws CommandNotFoundException {
        //when
        ApiGroup apiGroup = ApiGroup.getMatchingApiGroup("/api/v0/admin/api");

        //then
        assertThat(apiGroup, is(ApiGroup.ADMIN));
    }

    @Test
    public void shouldReturnFieldExecutiveApiGroup() throws CommandNotFoundException {
        //when
        ApiGroup apiGroup = ApiGroup.getMatchingApiGroup("/api/v0/executive/group");

        //then
        assertThat(apiGroup, is(ApiGroup.FIELD_EXECUTIVE));
    }

    @Test
    public void shouldThrowNoCommandFoundForInvalidApi() {
        //when
        try {
            ApiGroup.getMatchingApiGroup("/api/v0/api/guest/*");
            Assert.fail();
        } catch (CommandNotFoundException e) {
        }
    }
}
