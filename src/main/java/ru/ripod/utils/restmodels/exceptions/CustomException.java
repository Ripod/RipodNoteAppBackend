package ru.ripod.utils.restmodels.exceptions;

public abstract class CustomException extends Exception{
    protected int errorCode;

    public CustomException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
