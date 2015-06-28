package com.quifers.servlet.validations;

import com.quifers.domain.Day;

import java.text.ParseException;

import static com.quifers.domain.Day.DATE_FORMAT;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class DayAttributeValidator implements AttributeValidator<Day> {

    @Override
    public Day validate(String day) throws InvalidRequestException {
        if (isEmpty(day)) {
            throw new InvalidRequestException("Day is empty.");
        }
        day = day.trim();
        try {
            return new Day(day);
        } catch (ParseException e) {
            throw new InvalidRequestException("Invalid day [" + day + "].Correct format is [" + DATE_FORMAT + "].");
        }
    }
}
