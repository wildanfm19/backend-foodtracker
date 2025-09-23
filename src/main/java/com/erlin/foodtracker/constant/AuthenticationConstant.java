package com.erlin.foodtracker.constant;

public record AuthenticationConstant() {
    // HTTP Status Codes
    public static final String HTTP_OK_200 = "200";
    public static final String HTTP_CREATED_201 = "201";
    public static final String HTTP_UNAUTHORIZED_401 = "401";
    public static final String HTTP_FORBIDDEN_403 = "403";
    public static final String HTTP_BAD_REQUEST_400 = "400";

    // Messages
    public static final String USER_REGISTERED_SUCCESS = "User registered successfully";
    public static final String USER_LOGGED_IN_SUCCESS = "User logged in successfully";
    public static final String USER_LOGGED_OUT_SUCCESS = "User logged out successfully";

    public static final String INVALID_CREDENTIALS = "Invalid username or password";
    public static final String TOKEN_EXPIRED = "JWT token has expired";
    public static final String TOKEN_INVALID = "Invalid JWT token";
    public static final String AUTHORIZATION_HEADER_MISSING = "Authorization header is missing";

    // Validation
    public static final String USERNAME_REQUIRED = "Username is required";
    public static final String PASSWORD_REQUIRED = "Password is required";
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String PASSWORD_TOO_SHORT = "Password must be at least 8 characters long";
}
