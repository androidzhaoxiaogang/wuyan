package net.wecash.common.exception;

/**
 * 
 * @author xkk
 *
 */
public enum ErrorCode {

    //System Error
    SYSTEM_ERROR(10001, "System error"),
    SERVICE_UNAVAILABLE(10002, "Service unavailable"),
    
    MISS_REQUIRED_PARAMETER(10103, "Miss requred parameter (%s),see doc for more info"),
    ILLEGAL_REQUEST(10104,"Illegal request"),
    PARAMETER_VALUE_INVALID(10105, "Parameter (%s)'s value invalid, see doc for more info"),
    PARAMETER_ERROR(10106, "Parameter error, see doc for more info"),
    HTTP_BODY_FORMAT_ERROR(10107, "HTTP body format error, see doc for more info"),
    HTTP_METHOD_SUPORT_ERROR(10108, "HTTP method is not suported for this request"),

    //Server Error
    UID_OR_ACCESS_TOKEN_ERROR(20001, "Uid or access_token error"),
    THIRD_TOKEN_UNAUTHORIZED(20002, "Third token unauthorized"),
    NONSUPPORT_SNS_AUTH_TYPE(20003, "Nonsupport sns authorization type"),
    CLIENT_TYPE_UNAUTHORIZED(20004, "Client type unauthorized"),
    USER_ALREADY_EXISTS(20004, "User already exists"),
    USERNAME_ALREADY_EXISTS(20007, "Username already exists"),
    USERNAME_DOES_NOT_EXISTS(20008, "Username does not exists"),
    PHONE_CAPTCHA_HAS_BEEN_SEND(20011, "Phone captcha has been send to user"),
    
    TAG_NUM_ALREADY_EXISTS(20100, "Tag already exists"),
    TAG_NUM_DOES_NOT_EXISTS(20101, "Tag does not exists"),
    IMAGE_DOSE_NOT_EXISTS(20102, "Image does not exists"),
    USER_IMAGE_COUNT_UPPER_LIMIT(20103, "User image upper limit"),
    USER_DOSE_NOT_EXISTS(20106, "User does not exists"),
    RESOURCE_DOES_NOT_EXISTS(20110, "Resource (%s) does not exists"),
    RESOURCE_ALREADY_EXISTS(20111, "Resource (%s) already exists"),
    
    
    TEXT_TOO_LONG(20200, "Text too long"),
    VARIFICATION_CODE_ERROR(20201, "Varification code error"),
    OPERATION_OUT_OF_LIMIT(20202, "Operation out of limit"),
    
    PERMISSION_DENIED(21101, "Permission denied"),
    ARREARAGE(21102, "Arrearage"),

    AUTH_FAILED(21301, "Auth failed"),
    USERNAME_OR_PASSWORD_ERROR(21302, "Username or password error"),
    USERNAME_ADD_PWD_AUTH_OUT_OF_RATE_LIMIT(21303, "Username and pwd auth out of rate limit"),

    USER_LOCKED(21304, "User locked, unlock left (%s)"),
    USER_LOGIN_REQUIRED(21305, "User login requried"),
    
    TAG_REFERENCE_ERROR(21501, "Unable to delete, tag reference"),
    REQUEST_BODY_IS_ILLEGAL(21502, "Request body is illegal"),

    //oAuth error
    REDIRECT_URI_MISMATCH(21322, "Redirect uri mismatch"),
    INVALID_REQUEST(21323, "Invalid request"),
    INVALID_CLIENT(21324, "Invalid client"),
    INVALID_GRANT(21325, "Invalid grant"),
    UNAUTHORIZED_CLIENT(21326, "Unauthorized client"),
    EXPIRED_TOKEN(21327, "Expired token"),
    UNSUPPORTED_GRANT_TYPE(21328, "Unsupported grant type"),
    UNSUPPORTED_RESPONSE_TYPE(21329, "Unsupported response type"),
    ACCESS_DENIED(21330, "Access denied"),
    TEMPORARILY_UNAVAILABLE(21331, "Temporarily unavailable"),
    REFRESH_TOKEN_ERROR(21332, "Refresh token (%s) error"),
    REFRESH_TOKEN_EXPIRED(21333, "Refresh token (%s) expired"),
    CODE_ERROR(21334, "Code (%s) error"),
    CODE_EXPIRED(21335, "Code (%s) expired"),
    TOKEN_ERROR(21336, "Token (%s) error"),
    SERVER_TOKEN_ERROR(21337, "Server token (%s) error"),
    SERVER_TOKEN_EXPIRED(21338, "Server token (%s) expired"),

    HTTP_CONNECTION_TO_REFUSED(21801, "Http connection to (%s) refused"),
    HTTP_RESPONSE_TIMEOUT(21802, "Http response from (%s) timeout"),
    HTTP_INVALID_RESPONSE(21803, "Http invalid response from (%s)"),
    HTTP_RESPONSE_BAD_STATUS_CODE(21804, "Http response bad status code (%s) from (%s)"),
    FILE_READ_ERROR(22001, "File read error (%s)"),
    FILE_OUTOF_MAX_SIZE_ERROR(22002, "File size is too large"),//MaxUploadSize
    FILE_FORMAT_ERROR(22003, "File format error"),
    FILE_ALREADY_EXISTS(22004,"File already exists"),
    FILE_DOES_NOT_EXISTS(22004,"File does not exists"),

    DATABASE_CONNECTION_ERROR(30002, "Database connection error"),
    DATABASE_OPERATION_ERROR(30003, "Database operation error");    
    
    
    
    private int errorCode;
    private String error;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private ErrorCode(int errorCode, String error) {
        this.errorCode = errorCode;
        this.error = error;
    }

}
