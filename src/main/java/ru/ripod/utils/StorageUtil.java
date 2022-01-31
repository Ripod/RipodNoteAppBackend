package ru.ripod.utils;

import ru.ripod.utils.dbmodels.LoginData;

import java.text.SimpleDateFormat;
import java.util.*;

public class StorageUtil {
    public static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
}
