package com.quifers.validations;

import com.quifers.domain.Day;

import java.text.ParseException;

import static com.quifers.domain.Day.DATE_FORMAT;

public class DayAttributeValidator implements AttributeValidator<Day> {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator;

    public DayAttributeValidator(EmptyStringAttributeValidator emptyStringAttributeValidator) {
        this.emptyStringAttributeValidator = emptyStringAttributeValidator;
    }

    @Override
    public Day validate(String day) throws InvalidRequestException {
        day = emptyStringAttributeValidator.validate(day);
        day = day.trim();
        try {
            return new Day(day);
        } catch (ParseException e) {
            throw new InvalidRequestException("Invalid day [" + day + "].Correct format is [" + DATE_FORMAT + "].");
        }
    }
}
