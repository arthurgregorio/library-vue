package br.eti.arthurgregorio.library.domain.repositories.administration;

import br.eti.arthurgregorio.library.domain.entities.administration.Authority;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/02/2020
 */
@Repository
public interface AuthorityRepository extends DefaultRepository<Authority> {

    /**
     *
     * @param name
     * @return
     */
    Optional<Authority> findByName(String name);
}
