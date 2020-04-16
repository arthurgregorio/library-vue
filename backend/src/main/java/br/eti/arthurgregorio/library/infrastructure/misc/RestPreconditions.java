package br.eti.arthurgregorio.library.infrastructure.misc;

import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 02/03/2020
 */
public final class RestPreconditions {

    /**
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> checkFound(final T object) {
        if (Objects.isNull(object)) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(object);
    }

    /**
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T extends Collection<T>> ResponseEntity<T> checkFound(final T object) {
        if (!Objects.isNull(object)) {
            if (!object.isEmpty()) {
                return ResponseEntity.ok(object);
            }
        }
        return ResponseEntity
                .noContent()
                .build();
    }
}