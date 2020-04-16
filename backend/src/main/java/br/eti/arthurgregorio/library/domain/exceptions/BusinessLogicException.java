package br.eti.arthurgregorio.library.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 07/01/2020
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessLogicException extends ApplicationRuntimeException {

    /**
     *
     */
    public BusinessLogicException() {
        this(null, new Object[]{});
    }

    /**
     * Constructor...
     *
     * @param message the message to describe the error
     */
    public BusinessLogicException(String message) {
        super(message);
    }

    /**
     * Constructor...
     *
     * @param message    the message to describe the error
     * @param parameters the parameters to fill in the message
     */
    public BusinessLogicException(String message, Object... parameters) {
        super(message);
        this.parameters = parameters;
    }

    /**
     * Constructor...
     *
     * @param message    the message to describe the error
     * @param throwable  the instance of the exception to compose the stack
     * @param parameters the parameters to fill in the message
     */
    public BusinessLogicException(String message, Throwable throwable, Object... parameters) {
        super(message, throwable);
        this.parameters = parameters;
    }
}

