package com.cruise.utils;

import com.cruise.exceptions.InvalidFormatException;

import java.sql.Date;
import java.time.LocalDate;

public final class ValidationUtil {


    public static void validateField(String field, String regex, String errorMessage) throws InvalidFormatException {
        if (field == null || !field.matches(regex))
            throw new InvalidFormatException(errorMessage);
    }

    public static void validateStartDateCruise(Date startDate) throws InvalidFormatException {
        if(!startDate.after(Date.valueOf(LocalDate.now()))){
            throw new InvalidFormatException("error.cruise.date");
        }
    }
    public static void validateEndDateCruise(Date endDate, Date startDate) throws InvalidFormatException {
        if(!endDate.after(startDate)){
            throw new InvalidFormatException("error.cruise.endDate");
        }
    }
    public static void validateAllDigitCruiseFields(int number) throws InvalidFormatException {
        if (number < 1){
            throw new InvalidFormatException("error.cruise.digit.lessThenZero");
        }
    }
    public static void validateCruiseFreeSpaces(int capacity, int freeSpaces) throws InvalidFormatException {
        if (freeSpaces < 1){
            throw new InvalidFormatException("error.cruise.freeSpaces");
        }
        if (freeSpaces > capacity){
            throw new InvalidFormatException("error.cruise.freeSpaces.more");
        }
    }
    public static void validateCruisePrice(double price) throws InvalidFormatException {
        if (price < 1){
            throw new InvalidFormatException("error.cruise.price.lessThenZero");
        }
    }
}
