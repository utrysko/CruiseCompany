package com.cruise.exceptions;

import static com.cruise.exceptions.constants.ExceptionMessage.PASSWORD_DONT_MATCH;

public class PasswordDontMatchException extends ServiceException{
    public PasswordDontMatchException(){super(PASSWORD_DONT_MATCH);
    }
}
