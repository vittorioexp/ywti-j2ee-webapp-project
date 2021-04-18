package it.unipd.dei.yourwaytoitaly.utils;

//import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

public enum ErrorCode {

    WRONG_FORMAT(-1, HttpServletResponse.SC_BAD_REQUEST,"Wrong format."),
    AD_NOT_FOUND(-2, HttpServletResponse.SC_NOT_FOUND, "Advertisement not found."),
    EMPTY_INPUT_FIELDS(-3, HttpServletResponse.SC_BAD_REQUEST, "One or more input fields are empty."),
    EMAIL_MISSING(-4, HttpServletResponse.SC_BAD_REQUEST, "Email missing"),
    PASSWORD_MISSING(-5, HttpServletResponse.SC_BAD_REQUEST, "Password missing"),
    WRONG_CREDENTIALS(-6, HttpServletResponse.SC_BAD_REQUEST, "Submitted credentials are wrong"),
    DIFFERENT_PASSWORDS(-7, HttpServletResponse.SC_CONFLICT, "different passwords"),
    MAIL_ALREADY_USED(-8, HttpServletResponse.SC_CONFLICT, "email already used"),
    UNRECOGNIZED_USER(-9, HttpServletResponse.SC_BAD_REQUEST, "Unrecognized user"),
    WRONG_REST_FORMAT(-10, HttpServletResponse.SC_BAD_REQUEST, "Wrong rest request format."),
    EMPTY_LIST(-11, HttpServletResponse.SC_NOT_FOUND, "No results found"),
    USER_NOT_FOUND(-12, HttpServletResponse.SC_NOT_FOUND, "User not found."),
    BADLY_FORMATTED_JSON(-13,  HttpServletResponse.SC_BAD_REQUEST, "The input json is in the wrong format."),
    ADVERTISEMENT_NOT_FOUND(-14, HttpServletResponse.SC_NOT_FOUND, "Advertisement not found."),
    USER_NOT_ALLOWED(-15, HttpServletResponse.SC_BAD_REQUEST, "User not allowed."),
    OPERATION_UNKNOWN(-20, HttpServletResponse.SC_BAD_REQUEST, "Operation unknown."),
    METHOD_NOT_ALLOWED(-40, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "The method is not allowed"),
    INTERNAL_ERROR(-100, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Error");


    //TYPE_MISSING(-1, HttpServletResponse.SC_BAD_REQUEST, "User type not selected.");


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


//    public JSONObject toJSON() {
//        JSONObject data = new JSONObject();
//        data.put("code", errorCode);
//        data.put("message", errorMessage);
//        JSONObject info = new JSONObject();
//        info.put("error", data);
//        return info;
//    }
}