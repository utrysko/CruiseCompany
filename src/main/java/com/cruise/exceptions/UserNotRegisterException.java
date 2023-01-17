package com.cruise.exceptions;

import static com.cruise.exceptions.constants.ExceptionMessage.USER_NOT_REGISTER;

public class UserNotRegisterException extends ServiceException{
    public UserNotRegisterException(){
        super(USER_NOT_REGISTER);
    }
}
