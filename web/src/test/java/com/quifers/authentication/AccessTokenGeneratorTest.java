package com.quifers.authentication;

import com.quifers.domain.AdminAccount;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AccessTokenGeneratorTest {

    private final AccessTokenGenerator tokenGenerator = new AccessTokenGenerator();

    @Test
    public void shouldGenerateAdminAccessToken() throws Exception {
        //given
        AdminAccount account = new AdminAccount("dheerajsharma1990", "mypassword");

        //when
        String accessToken = tokenGenerator.generateAccessToken(account);

        //then
        assertThat(accessToken, is("297f7024a516256a526bd6b9f2d3f15c"));
    }
}
