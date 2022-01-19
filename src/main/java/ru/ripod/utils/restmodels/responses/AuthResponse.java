package ru.ripod.utils.restmodels.responses;

public class AuthResponse extends BaseResponse{
    private int id;

    public AuthResponse(String message, int id){
        super(0, message);
        this.id = id;
    }
    public AuthResponse(int code, String message, int id) {
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
