package com.WMS1.drawful.helpers;

import android.service.autofill.RegexValidator;

public class Validation {
    public static boolean validateMail(String mail) {
        return mail.matches("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)");
    }

    public static boolean validateUsername(String password) {
        return password.matches("(^[a-zA-Z0-9]{4,32}$)");
    }

    public static boolean validateJoinCode(String code) {
        return code.matches("(^[A-Z0-9]{4}$)");
    }
}
