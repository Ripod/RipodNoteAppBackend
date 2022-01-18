package ru.ripod.utils.dbmodels;

import ru.ripod.utils.StorageUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.ripod.utils.StorageUtil.dateFormatter;

public class User {
    private int id;
    private String login;
    private String encodedPass;
    private String token;
    private String tokenValidUntil;

    public User(String login){
        this.login = login;
        this.id = login.hashCode();
    }
    public User(int id, String login, String encodedPass, String token, String tokenValidUntil) {
        this.id = id;
        this.login = login;
        this.encodedPass = encodedPass;
        this.token = token;
        this.tokenValidUntil = tokenValidUntil;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEncodedPass(String encodedPass) {
        this.encodedPass = encodedPass;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenValidUntil(String tokenValidUntil) {
        this.tokenValidUntil = tokenValidUntil;
    }
    public void setTokenValidUntil(Date timeUntil){
        this.tokenValidUntil = dateFormatter.format(timeUntil);
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEncodedPass() {
        return encodedPass;
    }

    public String getToken() {
        return token;
    }

    public String getTokenValidUntil() {
        return tokenValidUntil;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", encodedPass='" + encodedPass + '\'' +
                ", token='" + token + '\'' +
                ", tokenValidUntil='" + tokenValidUntil + '\'' +
                '}';
    }
}
