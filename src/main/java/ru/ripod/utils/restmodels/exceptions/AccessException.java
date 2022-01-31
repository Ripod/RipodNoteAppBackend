package ru.ripod.utils.restmodels.exceptions;

public class AccessException extends CustomException{

    public AccessException(String message) {
        super(message);
        errorCode = 1;
    }
}
