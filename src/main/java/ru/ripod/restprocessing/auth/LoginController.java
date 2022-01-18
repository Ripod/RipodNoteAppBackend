package ru.ripod.restprocessing.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ripod.utils.dbmodels.User;
import ru.ripod.utils.restmodels.exceptions.RegistrationException;
import ru.ripod.utils.restmodels.responses.BaseResponse;
import ru.ripod.utils.restmodels.responses.RegistrationResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static ru.ripod.actionprocessing.Authorization.register;

@RestController
public class LoginController {
    @GetMapping("register")
    public BaseResponse registration(
            @RequestParam("login") String login,
            @RequestParam("pass") String password,
            HttpServletResponse response){
        try{
            User curUser = register(login, password);
            System.out.println("Added user: " + curUser);
            Cookie cookie = new Cookie("SessionID", curUser.getToken());
            cookie.setMaxAge(1800);
            response.addCookie(cookie);
            return new RegistrationResponse(0, "Регистрация прошла успешно", curUser.getId());
        }catch (RegistrationException exc){
            return new BaseResponse(1, exc.getMessage());
        }
    }
}
