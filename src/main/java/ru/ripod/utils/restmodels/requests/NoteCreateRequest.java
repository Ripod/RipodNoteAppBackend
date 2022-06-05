package ru.ripod.utils.restmodels.requests;

public class NoteCreateRequest {
    private long userId;
    private String title;
    private boolean passwordProtected;
    private String password;
    private boolean shared;

    public NoteCreateRequest() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPasswordProtected() {
        return passwordProtected;
    }

    public void setPasswordProtected(boolean passwordProtected) {
        this.passwordProtected = passwordProtected;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }
}
