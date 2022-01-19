package ru.ripod.actionprocessing;

import org.springframework.util.DigestUtils;
import ru.ripod.utils.dbmodels.User;
import ru.ripod.utils.restmodels.exceptions.AccessException;
import ru.ripod.utils.restmodels.exceptions.LoginException;
import ru.ripod.utils.restmodels.exceptions.RegistrationException;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import static ru.ripod.utils.StorageUtil.*;

public class Authorization {
    public static User register(String login, String pass) throws RegistrationException {
        if(USER_STORAGE.containsKey(login)){
            throw new RegistrationException("This login is already taken");
        }
        validatePasswordSecurity(pass);
        User result = new User(login);
        result.setEncodedPass(encodePassword(pass));
        int id = nextId();
        result.setId(id);
        addTokenAndValidUntil(result);
        //TODO Добавление в БД
        USER_STORAGE.put(result.getLogin(), result);
        return result;
    }

    public static User loginProcess(String login, String pass) throws LoginException {
        User curUser = USER_STORAGE.get(login);
        String encodedPass = encodePassword(pass);
        if(curUser == null || !curUser.getEncodedPass().equals(encodedPass))
            throw new LoginException("Wrong login or password");
        addTokenAndValidUntil(curUser);
        return curUser;
    }

    public static User logoutProcess(String login, String token) throws AccessException {
        User curUser = validateToken(login, token);
        Calendar curTime = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        curTime.add(Calendar.SECOND, -1);
        String curTimeString = dateFormatter.format(curTime.getTime());
        curUser.setTokenValidUntil(curTimeString);
        return curUser;
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

    private static void addTokenAndValidUntil(User user){
        Calendar curTime = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        String curTimeString = dateFormatter.format(curTime.getTime());
        String tokenInitString = user.getId() + curTimeString;
        String token = DigestUtils.md5DigestAsHex(tokenInitString.getBytes(StandardCharsets.UTF_8));
        user.setToken(token);
        curTime.add(Calendar.MINUTE, 30);
        user.setTokenValidUntil(curTime.getTime());
    }

    private static User validateToken(String login, String token) throws AccessException {
        User curUser = USER_STORAGE.get(login);

        if (curUser == null) throw new AccessException("You must be logged in to do this");
        Calendar curTime = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        try {
            Calendar validationTime = Calendar.getInstance();
            validationTime.setTime(dateFormatter.parse(curUser.getTokenValidUntil()));
            if (!curUser.getToken().equals(token) || curTime.after(validationTime))
                throw new AccessException("You must be logged in to do this");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return curUser;

    }
}
