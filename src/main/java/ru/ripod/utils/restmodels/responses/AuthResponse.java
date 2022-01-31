package ru.ripod.utils.restmodels.responses;

public class AuthResponse extends BaseResponse{
    private long id;

    public AuthResponse(String message, long id){
        super(0, message);
        this.id = id;
    }
    public AuthResponse(int code, String message, long id) {
        super(code, message);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
