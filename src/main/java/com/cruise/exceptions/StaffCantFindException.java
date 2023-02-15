package com.cruise.exceptions;
import static com.cruise.exceptions.constants.ExceptionMessage.ERROR_STAFF_CANT_FIND;

public class StaffCantFindException extends ServiceException{

    public StaffCantFindException(){super(ERROR_STAFF_CANT_FIND);}
}
