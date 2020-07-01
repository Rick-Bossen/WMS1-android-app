package com.WMS1.drawful.helpers;

/**
 * Static class containing all the validation functions.
 */
public class Validation {

    /**
     * Validates an email address using regex.
     *
     * @param mail the address to validate
     * @return true if the mail matches the regex
     */
    public static boolean validateMail(String mail) {
        return mail.matches("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)");
    }

    /**
     * Validates an username using regex.
     *
     * @param username the username to validate
     * @return true if the username matches the regex
     */
    public static boolean validateUsername(String username) {
        return username.matches("(^[a-zA-Z0-9]{4,32}$)");
    }

    /**
     * Validates a join code address using regex.
     *
     * @param code the code to validate
     * @return true if the code matches the regex
     */
    public static boolean validateJoinCode(String code) {
        return code.matches("(^[A-Z0-9]{4}$)");
    }
}
