package br.eti.arthurgregorio.library.application.components.advice;

import br.eti.arthurgregorio.library.domain.exceptions.BusinessLogicException;
import br.eti.arthurgregorio.library.infrastructure.misc.Translator;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 11/02/2020
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     *
     * @param response
     * @param exception
     * @throws IOException
     */
    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public void handleIllegalArgumentException(HttpServletResponse response, RuntimeException exception) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), this.translate(exception.getMessage()));
    }

    /**
     *
     * @param response
     * @param exception
     * @throws IOException
     */
    @ExceptionHandler(BusinessLogicException.class)
    public void handleIllegalArgumentException(HttpServletResponse response, BusinessLogicException exception) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), this.translate(exception.getMessage(), exception.getParameters()));
    }

    /**
     *
     * @param response
     * @param exception
     * @throws IOException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void handleIllegalAccessDenied(HttpServletResponse response, AccessDeniedException exception) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), this.translate(exception.getMessage()));
    }

    /**
     *
     * @param messageCode
     * @param parameters
     * @return
     */
    private String translate(String messageCode, Object... parameters) {
        return Translator.translate(messageCode, parameters);
    }
}
