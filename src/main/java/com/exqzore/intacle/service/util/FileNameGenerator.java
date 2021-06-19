package com.exqzore.intacle.service.util;

import java.util.Calendar;

public class FileNameGenerator {
    private final static String EXTENSION_DELIMITER = ".";

    public static synchronized String generate(String postfix) {
        return Calendar.getInstance().getTimeInMillis() + postfix;
    }

    public static String getExtension(String name) {
        return name.substring(name.lastIndexOf(EXTENSION_DELIMITER));
    }
}
