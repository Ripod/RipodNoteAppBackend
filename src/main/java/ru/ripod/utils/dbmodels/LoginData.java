package ru.ripod.utils.dbmodels;

import javax.persistence.*;
import java.util.Date;

import static ru.ripod.utils.StorageUtil.dateFormatter;

@Entity
@Table(name = "logindata")
public class LoginData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    private String login;
    private String pass;
    private String token;
    private String tokenExpire;

    public LoginData() {
    }

    public LoginData(int id, String login, String encodedPass, String token, String tokenValidUntil) {
        this.userId = id;
        this.login = login;
        this.pass = encodedPass;
        this.token = token;
        this.tokenExpire = tokenValidUntil;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenExpire(String tokenExpire) {
        this.tokenExpire = tokenExpire;
    }
    public void setTokenValidUntil(Date timeUntil){
        this.tokenExpire = dateFormatter.format(timeUntil);
    }

    public long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getToken() {
        return token;
    }

    public String getTokenExpire() {
        return tokenExpire;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", login='" + login + '\'' +
                ", encodedPass='" + pass + '\'' +
                ", token='" + token + '\'' +
                ", tokenValidUntil='" + tokenExpire + '\'' +
                '}';
    }
}
