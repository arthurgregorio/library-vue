package br.eti.arthurgregorio.library.domain.logics.user;

import br.eti.arthurgregorio.library.domain.entities.administration.User;
import br.eti.arthurgregorio.library.domain.exceptions.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserRepository;
import org.springframework.stereotype.Component;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 11/02/2020
 */
@Component
public class DuplicatedUserNameValidator implements UserSavingLogic {

    private UserRepository userRepository;

    /**
     *
     * @param userRepository
     */
    public DuplicatedUserNameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param value
     */
    @Override
    public void run(User value) {
        this.userRepository.findByUsername(value.getUsername())
                .ifPresent(user -> {
                    throw new BusinessLogicException("error.user.duplicated-username", user.getName());
                });
    }
}
