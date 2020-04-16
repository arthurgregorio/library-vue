package br.eti.arthurgregorio.library.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 01/01/2020
 */
public interface IPersistentEntity<T extends Serializable> extends Serializable {

    /**
     *
     * @return
     */
    T getId();

    /**
     *
     * @return
     */
    @JsonIgnore
    boolean isSaved();

    /**
     *
     * @return
     */
    @JsonIgnore
    default boolean isNotSaved() {
        return !this.isSaved();
    }
}
