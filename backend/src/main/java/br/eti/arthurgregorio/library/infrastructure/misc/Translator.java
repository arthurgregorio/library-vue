package br.eti.arthurgregorio.library.infrastructure.misc;

import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 25/03/2020
 */
@Component
public class Translator {

    @Setter
    private static MessageSource messageSource;

    /**
     *
     * @param messageCode
     * @param parameters
     * @return
     */
    public static String translate(String messageCode, Object... parameters) {
        return messageSource.getMessage(messageCode, parameters, LocaleContextHolder.getLocale());
    }
}