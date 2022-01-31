package ru.ripod.restprocessing.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ripod.actionprocessing.Authorization;
import ru.ripod.utils.dbmodels.LoginData;
import ru.ripod.utils.restmodels.exceptions.CustomException;
import ru.ripod.utils.restmodels.responses.BaseResponse;
import ru.ripod.utils.restmodels.responses.AuthResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static ru.ripod.actionprocessing.Authorization.*;

@RestController
public class LoginController {

    public static final String SESSION_ID = "SessionID";

    @Autowired
    private Authorization authorization;

    private static void setSessionCookie(HttpServletResponse response, LoginData curLoginData) {
        Cookie cookie = new Cookie(SESSION_ID, curLoginData.getToken());
        cookie.setMaxAge(1800);
        response.addCookie(cookie);
    }

    @GetMapping("register")
    public BaseResponse registration(
            @RequestParam("login") String login,
            @RequestParam("pass") String password,
            @RequestParam("name") String name,
            HttpServletResponse response
    ) {
        try {
            LoginData curLoginData = authorization.registerProcess(login, password, name);
            setSessionCookie(response, curLoginData);
            return new AuthResponse(0, "Registration successful", curLoginData.getUserId());
        } catch (CustomException exc) {
            return new BaseResponse(exc.getErrorCode(), exc.getMessage());
        }
    }

    @GetMapping("login")
    public BaseResponse login(
            @RequestParam("login") String login,
            @RequestParam("pass") String password,
            HttpServletResponse response
    ) {
        try {
            LoginData curLoginData = authorization.loginProcess(login, password);
            setSessionCookie(response, curLoginData);
            return new AuthResponse("Login successful", curLoginData.getUserId());
        } catch (CustomException exc) {
            return new BaseResponse(exc.getErrorCode(), exc.getMessage());
        }
    }

    @GetMapping("logout")
    public BaseResponse logout(
            @RequestParam("userId") long userId,
            @CookieValue(name = SESSION_ID, defaultValue = "0") String token,
            HttpServletResponse response
    ) {
        try {
            authorization.logoutProcess(userId, token);
            Cookie cookie = new Cookie(SESSION_ID, "0");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return new BaseResponse(0, "Logout successful");
        } catch (CustomException exc) {
            return new BaseResponse(exc.getErrorCode(), exc.getMessage());
        }
    }

    @GetMapping("renew")
    public BaseResponse sessionRenew(
            @RequestParam("userId") long userId,
            @CookieValue(name = SESSION_ID, defaultValue = "0") String token,
            HttpServletResponse response
    ) {
        try {
            LoginData curLoginData = authorization.renewSessionProcess(userId, token);
            setSessionCookie(response, curLoginData);
            return new AuthResponse("Session renewal successful", curLoginData.getUserId());
        } catch (CustomException exc) {
            return new BaseResponse(exc.getErrorCode(), exc.getMessage());
        }
    }
}
