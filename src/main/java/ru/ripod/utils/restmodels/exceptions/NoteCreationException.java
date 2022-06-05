package ru.ripod.utils.restmodels.exceptions;

public class NoteCreationException extends CustomException{

    public NoteCreationException(String message) {
        super(message);
        errorCode = 5;
    }
}
