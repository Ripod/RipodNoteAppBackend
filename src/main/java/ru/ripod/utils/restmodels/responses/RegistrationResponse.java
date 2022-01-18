package ru.ripod.utils.restmodels.responses;

public class RegistrationResponse extends BaseResponse{
    private int id;

    public RegistrationResponse(int code, String message, int id) {
        super(code, message);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
