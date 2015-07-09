package com.quifers.validations;

import com.quifers.domain.id.OrderId;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderIdAttributeValidatorTest {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator = mock(EmptyStringAttributeValidator.class);

    private final OrderIdAttributeValidator validator = new OrderIdAttributeValidator(emptyStringAttributeValidator);

    @Test
    public void shouldThrowExceptionOnInvalidOrderIdPrefix() {
        try {
            //given
            String invalidPrefix = "QUIF1234567899";
            when(emptyStringAttributeValidator.validate(invalidPrefix)).thenReturn(invalidPrefix);

            //when
            validator.validate(invalidPrefix);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Order Id [QUIF1234567899] must start with [QUIFID]."));
        }
    }

    @Test
    public void shouldThrowExceptionOnInvalidOrderIdLength() {
        try {
            //given
            String invalidLength = "QUIFID123456";
            when(emptyStringAttributeValidator.validate(invalidLength)).thenReturn(invalidLength);

            //when

            validator.validate(invalidLength);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Order Id [QUIFID123456] does not have desired length of [14]."));
        }
    }

    @Test
    public void shouldPassAllValidationAndConvertToUpperCase() throws InvalidRequestException {
        //given
        String validOrderId = "quiFiD12345678";
        when(emptyStringAttributeValidator.validate(validOrderId)).thenReturn(validOrderId);

        //when
        OrderId orderId = validator.validate(validOrderId);

        //then
        assertThat(orderId, is(new OrderId("QUIFID12345678")));
    }

}
