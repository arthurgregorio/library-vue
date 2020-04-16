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
public class DuplicatedEmailValidator implements UserSavingLogic, UserUpdatingLogic {

    private UserRepository userRepository;

    /**
     *
     * @param userRepository
     */
    public DuplicatedEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param value
     */
    @Override
    public void perform(User value) {
        if (value.isSaved()) {
            this.userRepository.findByEmailAndIdNot(value.getEmail(), value.getId())
                    .ifPresent(this::duplicatedException);
        } else {
            this.userRepository.findByEmail(value.getEmail())
                    .ifPresent(this::duplicatedException);
        }
    }

    /**
     *
     * @param user
     */
    private void duplicatedException(User user) {
        throw new BusinessLogicException("error.user.duplicated-email", user.getName());
    }
}
