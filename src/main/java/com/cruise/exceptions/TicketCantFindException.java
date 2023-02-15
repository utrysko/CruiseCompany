package com.cruise.exceptions;
import static com.cruise.exceptions.constants.ExceptionMessage.ERROR_TICKET_CANT_FIND;

public class TicketCantFindException extends ServiceException{
    public TicketCantFindException(){super(ERROR_TICKET_CANT_FIND);}
}
