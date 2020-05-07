package br.eti.arthurgregorio.library.domain.repositories.administration;

import br.eti.arthurgregorio.library.domain.entities.administration.ActivationStatus;
import br.eti.arthurgregorio.library.domain.entities.administration.UserActivation;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/05/2020
 */
@Repository
public interface UserActivationRepository extends DefaultRepository<UserActivation> {

    /**
     *
     * @param email
     * @return
     */
    Optional<UserActivation> findByUser_email(String email);

    /**
     *
     * @param userId
     * @return
     */
    Optional<UserActivation> findByUser_id(Long userId);

    /**
     *
     * @param userId
     * @return
     */
    void deleteByUser_id(long userId);

    /**
     *
     * @param status
     * @return
     */
    List<UserActivation> findByActivationStatus(ActivationStatus status);

    /**
     *
     * @param token
     * @param status
     * @return
     */
    Optional<UserActivation> findByTokenAndActivationStatus(String token, ActivationStatus status);
}