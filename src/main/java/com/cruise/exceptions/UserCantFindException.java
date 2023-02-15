package com.cruise.exceptions;
import static com.cruise.exceptions.constants.ExceptionMessage.ERROR_USER_CANT_FIND;

public class UserCantFindException extends ServiceException{

    public UserCantFindException(){
        super(ERROR_USER_CANT_FIND);
    }
}
