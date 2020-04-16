package br.eti.arthurgregorio.library.domain.repositories;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 01/01/2020
 */
public interface DefaultRepository<T extends PersistentEntity> extends JpaRepository<T, Long> { }