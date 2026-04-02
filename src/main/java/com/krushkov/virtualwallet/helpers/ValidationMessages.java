package com.krushkov.virtualwallet.helpers;

public final class ValidationMessages {

    private ValidationMessages() {}

    // Auth request errors
    public final static String IDENTIFIER_PASSWORD_MISSING_ERROR = "Username/Email and password are required.";
    public final static String IDENTIFIER_PASSWORD_WRONG_ERROR = "Username/email or password are wrong.";

    // User request errors
    public final static String USERNAME_LENGTH_ERROR = "Username must be between {min} and {max} symbols.";
    public final static String PASSWORD_LENGTH_ERROR = "Password must be between {min} and {max} symbols.";
    public final static String FIRST_NAME_LENGTH_ERROR = "First name must be between {min} and {max} symbols.";
    public final static String LAST_NAME_LENGTH_ERROR = "Last name must be between {min} and {max} symbols.";
    public final static String EMAIL_LENGTH_ERROR = "Email must be between {min} and {max} symbols.";
    public final static String PHONE_NUMBER_LENGTH_ERROR = "Phone number must be {max} symbols.";
    public final static String PHOTO_URL_LENGTH_ERROR = "Photo URL must be between {min} and {max} symbols.";
    public final static String USER_CREATE_RANGE_ERROR = "Created from must be before or equal to created to.";

    public final static String USERNAME_NOT_NULL_ERROR = "Username is required.";
    public final static String FIRST_NAME_NOT_NULL_ERROR = "First name is required.";
    public final static String LAST_NAME_NOT_NULL_ERROR = "Last name is required.";
    public final static String EMAIL_NOT_NULL_ERROR = "Email is required.";
    public final static String PASSWORD_NOT_NULL_ERROR = "Password is required.";

    public final static String EMAIL_INVALID_ERROR = "Email is invalid.";

    // Card request errors
    public final static String CARD_HOLDER_LENGTH_ERROR = "Card holder must be between {min} and {max} characters.";
    public final static String CARD_NUMBER_LENGTH_ERROR = "Card number must be 16 digits.";

    public final static String CARD_HOLDER_NOT_NULL_ERROR = "Card holder is required.";
    public final static String CARD_NUMBER_NOT_NULL_ERROR = "Card number is required.";
    public final static String CARD_EXPIRATION_MONTH_NOT_NULL_ERROR = "Expiration month is required.";
    public final static String CARD_EXPIRATION_YEAR_NOT_NULL_ERROR = "Expiration year is required.";

    public final static String CARD_EXPIRED_ERROR = "Card is expired.";

    // Currency request errors
    public final static String CURRENCY_CODE_NOT_NULL_ERROR = "Currency code is required.";
    public final static String CURRENCY_NAME_NOT_NULL_ERROR = "Currency name is required.";
    public final static String CURRENCY_SYMBOL_NOT_NULL_ERROR = "Currency symbol is required.";
    public final static String CURRENCY_DECIMALS_NOT_NULL_ERROR = "Currency decimals is required.";

    public final static String CURRENCY_CODE_LENGTH_ERROR = "Currency code must be {max} symbols (ISO 4217).";
    public final static String CURRENCY_NAME_LENGTH_ERROR = "Currency name cannot be greater that {max} symbols.";
    public final static String CURRENCY_SYMBOL_LENGTH_ERROR =
            "Currency symbol cannot be greater than {max} characters.";
    public final static String CURRENCY_MIN_DECIMALS_LENGTH_ERROR = "Currency decimals must be positive.";
    public final static String CURRENCY_MAX_DECIMALS_LENGTH_ERROR =
            "Currency decimals cannot be grater than 10 digits.";

    // Transaction request errors
    public final static String TRANSACTION_CREATE_RANGE_ERROR = "Created from must be before or equal to created to.";
    public final static String TRANSACTION_AMOUNT_RANGE_ERROR = "Min amount must be less than or equal to max amount.";

    // Wallet request errors
    public final static String WALLET_NAME_NOT_NULL_ERROR = "Password is required.";
    public final static String WALLET_NAME_LENGTH_ERROR = "Wallet name must be between {min} and {max} symbols.";
    public final static String WALLET_USER_ID_LENGTH_ERROR = "User Id must be positive.";
    public final static String WALLET_MIN_BALANCE_LENGTH_ERROR = "Minimal balance must be positive or zero.";
    public final static String WALLET_MAX_BALANCE_LENGTH_ERROR = "Maximal balance must be positive or zero.";
    public final static String WALLET_BALANCE_RANGE_ERROR = "Min balance must be less than or equal to max balance.";

    // Payment/Top-Up/Transfer request errors
    public final static String AMOUNT_LENGTH_ERROR = "Amount must be greater that zero.";
    public final static String AMOUNT_NOT_NULL_ERROR = "Amount must be not null.";
    public final static String MERCHANT_NOT_NULL_ERROR = "Merchant reference is required.";
    public final static String CARD_ID_LENGTH_ERROR = "Card ID must be positive.";
    public final static String CARD_ID_NOT_NULL_ERROR = "Card ID is required.";
    public final static String RECIPIENT_ID_NOT_NULL_ERROR = "Recipient ID must be not null.";

    // User validation errors
    public static final String ADMIN_ONLY_ERROR = "Only admins are allowed to perform this action.";
    public static final String USER_ONLY_ERROR = "Only users are allowed to perform this action.";
    public static final String USER_BLOCKED_ERROR = "Blocked users cannot perform this action.";
    public static final String RECIPIENT_BLOCKED_ERROR = "User is blocked, you cannot perform this action.";
    public static final String RECIPIENT_NOT_ADMIN_ERROR = "You cannot perform this action.";

    // Transaction validation errors
    public static final String WRONG_CURRENCY_ERROR = "Currency mismatch.";
    public static final String POSITIVE_AMOUNT_ERROR = "Amount must be positive.";
    public static final String INSUFFICIENT_FUNDS_ERROR = "Insufficient funds.";

    // Card validation errors
    public static final String CARD_ACTIVATE_ERROR = "Card is not active.";
    public static final String CARD_ALREADY_ACTIVE_ERROR = "Card is already active.";
    public static final String CARD_ALREADY_DEACTIVATE_ERROR = "Card is already deactivated.";
    public static final String CARD_DEACTIVATED_BY_ADMIN_ERROR = "Card was already deactivated by administrator.";
    public static final String CARD_ALREADY_EXISTS_ERROR = "User already have this card.";

    // Wallet validation errors
    public static final String WALLET_NAME_ALREADY_EXISTS_ERROR = "User already have wallet with the same name.";
    public static final String WALLET_ALREADY_DEFAULT_ERROR = "This wallet is already default.";

    // Api access errors
    public static final String API_ACCESS_ERROR = "You are not allowed to access this resource.";
    public static final String AUTHENTICATION_MISSING_ERROR = "Authentication required.";

    // JWT errors
    public static final String INVALID_TOKEN_ERROR = "Invalid JWT token: ";

    // User service errors
    public static final String USER_ALREADY_BLOCKED = "User %s is already blocked.";
    public static final String USER_NOT_BLOCKED = "User %s is not blocked.";

}
