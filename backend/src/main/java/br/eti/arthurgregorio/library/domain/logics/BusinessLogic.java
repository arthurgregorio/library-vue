package br.eti.arthurgregorio.library.domain.logics;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 07/01/2020
 */
public interface BusinessLogic<T extends PersistentEntity> {

    /**
     *
     * @param value
     */
    void run(T value);
}
