package br.eti.arthurgregorio.library.application.components;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 25/03/2020
 */
@ToString
@EqualsAndHashCode
public class MailMessage {

    @Getter
    @Setter
    private String templateName;

    @Getter
    @Setter
    private String to;
    @Getter
    @Setter
    private String from;
    @Getter
    @Setter
    private String subject;

    private Map<String, Object> properties;

    /**
     *
     */
    public MailMessage() {
        this.properties = new HashMap<>();
    }

    /**
     *
     * @param name
     * @param value
     */
    public void addProperty(String name, Object value) {
        this.properties.put(name, value);
    }

    /**
     *
     * @return
     */
    public Map<String, Object> getProperties() {
        return Collections.unmodifiableMap(this.properties);
    }
}