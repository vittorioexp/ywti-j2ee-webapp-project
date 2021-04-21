package it.unipd.dei.yourwaytoitaly.utils;


import javax.servlet.http.HttpServletResponse;

/**
 * Enumerator to manage the error codes
 * @author Francesco Giurisato
 * @version 1.0
 * @since 1.0
 */
public enum ErrorCode {

    WRONG_FORMAT(-1, HttpServletResponse.SC_BAD_REQUEST,"Wrong format."),
    AD_NOT_FOUND(-2, HttpServletResponse.SC_NOT_FOUND, "Advertisement not found."),
    EMPTY_INPUT_FIELDS(-3, HttpServletResponse.SC_BAD_REQUEST, "One or more input fields are empty."),
    EMAIL_MISSING(-4, HttpServletResponse.SC_BAD_REQUEST, "Email missing."),
    PASSWORD_MISSING(-5, HttpServletResponse.SC_BAD_REQUEST, "Password missing."),
    DIFFERENT_PASSWORDS(-6, HttpServletResponse.SC_CONFLICT, "Different passwords."),
    MAIL_ALREADY_USED(-7, HttpServletResponse.SC_CONFLICT, "Email already used."),
    EMPTY_LIST(-8, HttpServletResponse.SC_NOT_FOUND, "No results found."),
    USER_NOT_FOUND(-9, HttpServletResponse.SC_NOT_FOUND, "User not found."),
    BADLY_FORMATTED_JSON(-10,  HttpServletResponse.SC_BAD_REQUEST, "The input json is in the wrong format."),
    USER_NOT_ALLOWED(-11, HttpServletResponse.SC_UNAUTHORIZED, "User not allowed."),
    BOOKING_ALREADY_DONE(-12, HttpServletResponse.SC_CONFLICT, "Booking already done."),
    FEEDBACK_ALREADY_DONE(-13, HttpServletResponse.SC_CONFLICT, "Feedback already done."),
    OPERATION_UNKNOWN(-20, HttpServletResponse.SC_BAD_REQUEST, "Operation unknown."),
    METHOD_NOT_ALLOWED(-40, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "The method is not allowed."),
    INTERNAL_ERROR(-100, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Error.");


    private final int errorCode;
    private final int httpCode;
    private final String errorMessage;

    ErrorCode(int errorCode, int httpCode, String errorMessage) {
        this.errorCode = errorCode;
        this.httpCode = httpCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getHTTPCode() {
        return httpCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}