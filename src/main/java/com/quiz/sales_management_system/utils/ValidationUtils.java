package com.quiz.sales_management_system.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils{

    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidUserName(String name) {
        // Regular expression to match valid names (only alphabets, space, hyphen, and apostrophe allowed)
        String regex = "^[a-zA-Z]+(?:[\\s-'][a-zA-Z]+)*$";
        return Pattern.matches(regex, name);
    }

    public static boolean isValidAddress(String address) {
        // Regular expression to match valid addresses (allowing alphanumeric characters, spaces, commas, and periods)
        String regex = "^[a-zA-Z0-9\\s,\\.]+$";
        return Pattern.matches(regex, address);
    }
}
