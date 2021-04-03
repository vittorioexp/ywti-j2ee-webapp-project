package it.unipd.dei.yourwaytoitaly.utils;

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

public enum ErrorCode {

    WRONG_FORMAT(-100, HttpServletResponse.SC_BAD_REQUEST,"Wrong format."),
    AD_NOT_FOUND(-101, HttpServletResponse.SC_NOT_FOUND, "Advertisement not found."),
    EMPTY_INPUT_FIELDS(-102, HttpServletResponse.SC_BAD_REQUEST, "One or more input fields are empty."),
    EMAIL_MISSING(-103, HttpServletResponse.SC_BAD_REQUEST, "Email missing"),
    PASSWORD_MISSING(-104, HttpServletResponse.SC_BAD_REQUEST, "Password missing"),
    WRONG_CREDENTIALS(-105, HttpServletResponse.SC_BAD_REQUEST, "Submitted credentials are wrong"),
    DELETED_USER_NOT_PRESENT(-106, HttpServletResponse.SC_NOT_FOUND, "User to be deleted not found."),
    DIFFERENT_PASSWORDS(-107, HttpServletResponse.SC_CONFLICT, "different passwords"),
    MAIL_ALREADY_USED(-108, HttpServletResponse.SC_CONFLICT, "mail already used"),
    UNRECOGNIZED_ROLE(-109, HttpServletResponse.SC_BAD_REQUEST, "Unrecognized role"),
    ADVERTISEMENT_ALREADY_PRESENT(-110, HttpServletResponse.SC_CONFLICT, "Advertisement already inserted."),
    USER_ALREADY_PRESENT(-111, HttpServletResponse.SC_CONFLICT, "The user name has already been used."),
    MODEL_NOT_FOUND(-112, HttpServletResponse.SC_NOT_FOUND, "Model not found."),
    WRONG_REST_FORMAT(-113, HttpServletResponse.SC_BAD_REQUEST, "Wrong rest request format."),
    RIDE_ALREADY_PRESENT(-114, HttpServletResponse.SC_CONFLICT, "This ride id is already present"),
    RIDE_NOT_FOUND(-115, HttpServletResponse.SC_NOT_FOUND, "Ride not found."),
    ADVID_MISMATCH(-116, HttpServletResponse.SC_CONFLICT, "Advertisement requested and provided are different"),
    USER_NOT_FOUND(-117, HttpServletResponse.SC_NOT_FOUND, "User not found."),
    RIDEID_NOT_PROVIDED(-118, HttpServletResponse.SC_BAD_REQUEST, "Ad id missing."),
    DEVICE_NOT_FOUND(-119, HttpServletResponse.SC_NOT_FOUND, "Device not found"),
    BADLY_FORMATTED_JSON(-120,  HttpServletResponse.SC_BAD_REQUEST, "The input json is in the wrong format."),
    OPERATION_UNKNOWN(-200, HttpServletResponse.SC_BAD_REQUEST, "Operation unknown."),
    METHOD_NOT_ALLOWED(-500, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "The method is not allowed"),
    INTERNAL_ERROR(-999, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Error");


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


    public JSONObject toJSON() {
        JSONObject data = new JSONObject();
        data.put("code", errorCode);
        data.put("message", errorMessage);
        JSONObject info = new JSONObject();
        info.put("error", data);
        return info;
    }
}