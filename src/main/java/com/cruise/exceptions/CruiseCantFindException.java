package com.cruise.exceptions;
import static com.cruise.exceptions.constants.ExceptionMessage.ERROR_CRUISE_CANT_FIND;

public class CruiseCantFindException extends ServiceException{

    public CruiseCantFindException(){
        super(ERROR_CRUISE_CANT_FIND);
    }
}
