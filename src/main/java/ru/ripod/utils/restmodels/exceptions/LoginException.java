package ru.ripod.utils.restmodels.exceptions;

public class LoginException extends CustomException{
    public LoginException(String message) {
        super(message);
        errorCode = 3;
    }
}
