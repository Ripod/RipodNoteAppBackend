package ru.ripod.actionprocessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import ru.ripod.db.repositories.UserRepository;
import ru.ripod.utils.dbmodels.LoginData;
import ru.ripod.utils.restmodels.exceptions.AccessException;
import ru.ripod.utils.restmodels.exceptions.LoginException;
import ru.ripod.utils.restmodels.exceptions.RegistrationException;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import static ru.ripod.utils.StorageUtil.*;

@Service
public class Authorization {
    @Autowired
    UserRepository userRepository;

    public LoginData registerProcess(String login, String pass) throws RegistrationException {
        if(USER_STORAGE.containsKey(login)){
            throw new RegistrationException("This login is already taken");
        }
        System.out.println("creating user");
        validatePasswordSecurity(pass);
        LoginData result = new LoginData();
        result.setLogin(login);
        result.setPass(encodePassword(pass));
        addTokenAndValidUntil(result);
        userRepository.save(result);
        USER_STORAGE.put(result.getLogin(), result);
        System.out.println("Registration " + result);
        return result;
    }

    public static LoginData loginProcess(String login, String pass) throws LoginException {
        LoginData curLoginData = USER_STORAGE.get(login);
        String encodedPass = encodePassword(pass);
        if(curLoginData == null || !curLoginData.getPass().equals(encodedPass))
            throw new LoginException("Wrong login or password");
        addTokenAndValidUntil(curLoginData);
        System.out.println("Login " + curLoginData);
        return curLoginData;
    }

    public static LoginData logoutProcess(String login, String token) throws AccessException {
        LoginData curLoginData = validateToken(login, token);
        Calendar curTime = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        curTime.add(Calendar.SECOND, -1);
        curLoginData.setTokenValidUntil(curTime.getTime());
        System.out.println("Logout " + curLoginData);
        return curLoginData;
    }

    public static LoginData renewSessionProcess(String login, String token) throws AccessException {
        LoginData curLoginData = validateToken(login, token);
        Calendar validationTime = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        validationTime.add(Calendar.MINUTE, 30);
        curLoginData.setTokenValidUntil(validationTime.getTime());
        System.out.println("Session renewal " + curLoginData);
        return curLoginData;
    }

    private static void validatePasswordSecurity(String password)throws RegistrationException{
        if(password.length() < 6) throw new RegistrationException("Password is too short");
        if(!password.matches("^[A-Za-z0-9]+$")) throw new RegistrationException("Password contains unacceptable symbols");
        if(!password.matches("^.+[A-Z]+.+$") ||
                !password.matches("^.+[a-z]+.+$") ||
                !password.matches("^.+[0-9]+.+$") )
            throw new RegistrationException("Password doesn't contain small letter, capital letter or number");
    }

    private static String encodePassword(String pass) {
        return DigestUtils.md5DigestAsHex(pass.getBytes(StandardCharsets.UTF_8));
    }

    private static void addTokenAndValidUntil(LoginData loginData){
        Calendar curTime = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        String curTimeString = dateFormatter.format(curTime.getTime());
        String tokenInitString = loginData.getUserId() + curTimeString;
        String token = DigestUtils.md5DigestAsHex(tokenInitString.getBytes(StandardCharsets.UTF_8));
        loginData.setToken(token);
        curTime.add(Calendar.MINUTE, 30);
        loginData.setTokenValidUntil(curTime.getTime());
    }

    private static LoginData validateToken(String login, String token) throws AccessException {
        LoginData curLoginData = USER_STORAGE.get(login);

        if (curLoginData == null) throw new AccessException("You must be logged in to do this");
        Calendar curTime = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        try {
            Calendar validationTime = Calendar.getInstance();
            validationTime.setTime(dateFormatter.parse(curLoginData.getTokenExpire()));
            if (!curLoginData.getToken().equals(token) || curTime.after(validationTime))
                throw new AccessException("You must be logged in to do this");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return curLoginData;

    }
}
