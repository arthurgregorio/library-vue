package br.eti.arthurgregorio.library.application.components.formatters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 23/04/2020
 */
@Component
public class LocalDateFormatter implements Formatter<LocalDate> {

    private static final String PATTERN = "yyyy-MM-dd";

    /**
     * {@inheritDoc}
     *
     * @param text
     * @param locale
     * @return
     * @throws ParseException
     */
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return StringUtils.isNotBlank(text) ? LocalDate.parse(text, DateTimeFormatter.ofPattern(PATTERN)) : null;
    }

    /**
     * {@inheritDoc}
     *
     * @param localDate
     * @param locale
     * @return
     */
    @Override
    public String print(LocalDate localDate, Locale locale) {
        return localDate.format(DateTimeFormatter.ofPattern(PATTERN));
    }
}