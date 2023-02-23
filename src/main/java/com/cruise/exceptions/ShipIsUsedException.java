package com.cruise.exceptions;
import static com.cruise.exceptions.constants.ExceptionMessage.ERROR_SHIP_IS_USED;

public class ShipIsUsedException extends ServiceException{

    public ShipIsUsedException(){super(ERROR_SHIP_IS_USED);}
}
