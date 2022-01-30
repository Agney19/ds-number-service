package ru.dsnumberservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalNumException extends IllegalArgumentException {

    public static final String OUT_OF_BOUNDS_VALUE_ERROR_MSG = "The provided value must be between %d and %d";

    public static final String SAME_VALUE_ERROR_MSG_TEMPLATE = "Value of %d was processed earlier";
    public static final String VALUE_MINUS_ONE_ERROR_MSG_TEMPLATE = "Value of %d is one less than some value processed earlier";

    public IllegalNumException(String message) {
        super(message);
    }
}
