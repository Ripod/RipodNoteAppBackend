package ru.ripod.utils.restmodels.exceptions;

public class RegistrationException extends CustomException{
    public RegistrationException(String message){
        super(message);
        errorCode = 2;
    }
}
