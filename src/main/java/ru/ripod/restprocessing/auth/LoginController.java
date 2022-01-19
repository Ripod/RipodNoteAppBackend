package ru.ripod.restprocessing.auth;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ripod.utils.dbmodels.User;
import ru.ripod.utils.restmodels.exceptions.CustomException;
import ru.ripod.utils.restmodels.responses.BaseResponse;
import ru.ripod.utils.restmodels.responses.AuthResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static ru.ripod.actionprocessing.Authorization.*;

@RestController
public class LoginController {

    public static final String SESSION_ID = "SessionID";
    private static void setSessionCookie(HttpServletResponse response, User curUser){
        Cookie cookie = new Cookie(SESSION_ID, curUser.getToken());
        cookie.setMaxAge(1800);
        response.addCookie(cookie);
    }

    @GetMapping("register")
    public BaseResponse registration(
            @RequestParam("login") String login,
            @RequestParam("pass") String password,
            HttpServletResponse response
    ) {
        try {
            User curUser = register(login, password);
            setSessionCookie(response, curUser);
            return new AuthResponse(0, "Registration successful", curUser.getId());
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
            User curUser = loginProcess(login, password);
            setSessionCookie(response, curUser);
            return new AuthResponse("Login successful", curUser.getId());
        } catch (CustomException exc) {
            return new BaseResponse(exc.getErrorCode(), exc.getMessage());
        }
    }

    @GetMapping("logout")
    public BaseResponse logout(
            @RequestParam("login") String login,
            @CookieValue(name = SESSION_ID, defaultValue = "0") String token,
            HttpServletResponse response
    ){
        try {
            logoutProcess(login, token);
            Cookie cookie = new Cookie(SESSION_ID, "0");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return new BaseResponse(0, "Logout successful");
        } catch (CustomException exc) {
            return new BaseResponse(exc.getErrorCode(), exc.getMessage());
        }
    }



}
