package com.quifers.validations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAttributeValidator implements AttributeValidator<Date> {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator;
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public DateAttributeValidator(EmptyStringAttributeValidator emptyStringAttributeValidator) {
        this.emptyStringAttributeValidator = emptyStringAttributeValidator;
    }

    @Override
    public Date validate(String date) throws InvalidRequestException {
        date = emptyStringAttributeValidator.validate(date);
        date = date.trim();
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new InvalidRequestException("Date [" + date + "] has invalid format.Correct format is [" + DATE_FORMAT + "].");
        }
    }
}
