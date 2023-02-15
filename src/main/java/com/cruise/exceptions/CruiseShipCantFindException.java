package com.cruise.exceptions;
import static com.cruise.exceptions.constants.ExceptionMessage.ERROR_CRUISE_SHIP_CANT_FIND;

public class CruiseShipCantFindException extends ServiceException{

    public CruiseShipCantFindException(){super(ERROR_CRUISE_SHIP_CANT_FIND);}
}
