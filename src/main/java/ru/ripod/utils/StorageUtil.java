package ru.ripod.utils;

import ru.ripod.utils.dbmodels.User;

import java.text.SimpleDateFormat;
import java.util.*;

public class StorageUtil {
    private static int lastId;
    static {
        lastId = 1;
    }
    public static int nextId(){
        return lastId++;
    }
    public static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
    public static final Map<String, User> USER_STORAGE = new HashMap<>();
}
