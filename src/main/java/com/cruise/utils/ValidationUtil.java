package com.cruise.utils;

import com.cruise.exceptions.InvalidFormatException;
import com.cruise.exceptions.ServiceException;
import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.CruiseShip;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Validation util class for validate input fields
 *
 * @author Vasyl Utrysko
 * @version 1.0
 */
public class ValidationUtil {

    private ValidationUtil(){}

    /**
     * Validate string input field
     *
     * @param field - value of field
     * @param regex - regex for field
     * @param errorMessage - error message
     */
    public static void validateStringField(String field, String regex, String errorMessage) throws InvalidFormatException {
        if (field == null || !field.matches(regex))
            throw new InvalidFormatException(errorMessage);
    }

    /** Validate start date of cruise
     *
     * @param startDate - start date of Cruise
     */
    public static void validateStartDateCruise(Date startDate) throws InvalidFormatException {
        if(!startDate.after(Date.valueOf(LocalDate.now()))){
            throw new InvalidFormatException("error.cruise.date");
        }
    }

    /** Validate end date of cruise
     *
     * @param startDate - start date of Cruise
     * @param endDate - end date of Cruise
     */
    public static void validateEndDateCruise(Date endDate, Date startDate) throws InvalidFormatException {
        if(!endDate.after(startDate)){
            throw new InvalidFormatException("error.cruise.endDate");
        }
    }

    /**
     * Validate any digit field if this field is less the zero.
     *
     * @param number - value of any digit input field
     */
    public static void validateDigitField(int number) throws InvalidFormatException {
        if (number < 1){
            throw new InvalidFormatException("error.cruise.digit.lessThenZero");
        }
    }

    /**
     * Validate free spaces of cruise
     *
     * @param capacity - capacity of CruiseShip
     * @param freeSpaces - free spaces of Cruise
     */
    public static void validateCruiseFreeSpaces(int capacity, int freeSpaces) throws InvalidFormatException {
        if (freeSpaces < 1){
            throw new InvalidFormatException("error.cruise.freeSpaces");
        }
        if (freeSpaces > capacity){
            throw new InvalidFormatException("error.cruise.freeSpaces.more");
        }
    }

    /**
     * Validate cruise price
     *
     * @param price - price of Cruise
     */
    public static void validateCruisePrice(double price) throws InvalidFormatException {
        if (price < 1){
            throw new InvalidFormatException("error.cruise.price.lessThenZero");
        }
    }

    /**
     * Validate ship adding to cruise
     *
     * @param cruise - cruise instance
     * @param cruiseShip - cruiseShip instance
     */
    public static void validateAddingShip(Cruise cruise, CruiseShip cruiseShip) throws ServiceException {
        if (cruise.getFreeSpaces() > cruiseShip.getCapacity())
            throw new ServiceException("error.addShip.capacity");
    }

    /**
     * Validate if cruise don't started
     *
     * @param cruiseStatus - status of cruise
     */
    public static void validateIfStarted(String cruiseStatus) throws ServiceException {
        if (cruiseStatus.equals("Started"))
            throw new ServiceException("error.cruiseStatus.started");
    }

    /**
     * Validate if cruise don't ended
     *
     * @param cruiseStatus - status of cruise
     */
    public static void validateIfEnded(String cruiseStatus) throws ServiceException {
        if (cruiseStatus.equals("Ended"))
            throw new ServiceException("error.cruiseStatus.ended");
    }
}
