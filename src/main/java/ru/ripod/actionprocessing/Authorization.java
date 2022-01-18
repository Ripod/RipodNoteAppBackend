package ru.ripod.actionprocessing;

import org.springframework.util.DigestUtils;
import ru.ripod.utils.dbmodels.User;
import ru.ripod.utils.restmodels.exceptions.RegistrationException;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.TimeZone;

import static ru.ripod.utils.StorageUtil.*;

public class Authorization {
    public static User register(String login, String pass) throws RegistrationException {
        if(USER_STORAGE.containsKey(login)){
            throw new RegistrationException("Данный логин уже используется");
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

    private static void validatePasswordSecurity(String password)throws RegistrationException{
        if(password.length() < 6) throw new RegistrationException("Пароль слишком короткий");
        if(!password.matches("^[A-Za-z0-9]+$")) throw new RegistrationException("Пароль содержит недопустимые символы");
        if(!password.matches("^.+[A-Z]+.+$") ||
                !password.matches("^.+[a-z]+.+$") ||
                !password.matches("^.+[0-9]+.+$") ) throw new RegistrationException("Пароль не содержит строчной буквы, заглавной буквы или цифры");
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
}
