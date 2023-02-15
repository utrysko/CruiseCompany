package com.cruise.exceptions;
import static com.cruise.exceptions.constants.ExceptionMessage.ERROR_ROUTE_CANT_FIND;

public class RouteCantFindException extends ServiceException{

    public RouteCantFindException(){super(ERROR_ROUTE_CANT_FIND);}
}
