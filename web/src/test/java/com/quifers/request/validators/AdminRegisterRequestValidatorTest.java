package com.quifers.request.validators;

import com.quifers.request.AdminRegisterRequest;
import org.testng.annotations.Test;

import static com.quifers.request.validators.admin.AdminRegisterRequestValidator.validateAdminRegisterRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

public class AdminRegisterRequestValidatorTest {


    @Test
    public void shouldThrowInvalidRequestExceptionOnEmptyClientName() {
        //given
        AdminRegisterRequest request = mock(AdminRegisterRequest.class);
        when(request.getUserId()).thenReturn("user");
        when(request.getPassword()).thenReturn("password");
        when(request.getName()).thenReturn("Some Name");
        when(request.getMobileNumber()).thenReturn(null);

        //when
        try {
            validateAdminRegisterRequest(request);
            fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Mobile number cannot be empty."));
        }
    }
}
