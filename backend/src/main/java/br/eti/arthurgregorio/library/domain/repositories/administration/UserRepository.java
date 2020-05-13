package br.eti.arthurgregorio.library.domain.repositories.administration;

import br.eti.arthurgregorio.library.domain.entities.administration.User;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/02/2020
 */
@Repository
public interface UserRepository extends DefaultRepository<User> {

    /**
     *
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);

    /**
     *
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);

    /**
     *
     * @param email
     * @param id
     * @return
     */
    Optional<User> findByEmailAndIdNot(String email, Long id);

    /**
     *
     * @param filter
     * @return
     */
    @Query("from User u where filter(:filter, u.name, u.email, u.username) = true")
    Page<User> findByFilter(String filter, Pageable pageable);
}
