package com.cruise.exceptions;
import static com.cruise.exceptions.constants.ExceptionMessage.ERROR_SHIP_NAME_IS_USED;

public class ShipNameIsUsedException extends ServiceException{

    public ShipNameIsUsedException(){super(ERROR_SHIP_NAME_IS_USED);}
}
