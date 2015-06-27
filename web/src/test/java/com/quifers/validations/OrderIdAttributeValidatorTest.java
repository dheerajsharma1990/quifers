package com.quifers.validations;

import com.quifers.domain.id.OrderId;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.validations.OrderIdAttributeValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OrderIdAttributeValidatorTest {

    private final OrderIdAttributeValidator validator = new OrderIdAttributeValidator();

    @Test
    public void shouldThrowExceptionOnEmptyOrderId() {
        try {
            validator.validate("");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Order Id is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionOnNullOrderId() {
        try {
            validator.validate(null);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Order Id is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionOnInvalidOrderIdPrefix() {
        try {
            validator.validate("QUIF1234567899");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Order Id [QUIF1234567899] must start with [QUIFID]."));
        }
    }

    @Test
    public void shouldThrowExceptionOnInvalidOrderIdLength() {
        try {
            validator.validate("QUIFID123456");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Order Id [QUIFID123456] does not have desired length of [14]."));
        }
    }

    @Test
    public void shouldPassAllValidationAndConvertToUpperCase() throws InvalidRequestException {
        //when
        OrderId orderId = validator.validate("quiFiD12345678");

        //then
        assertThat(orderId, is(new OrderId("QUIFID12345678")));
    }

}
