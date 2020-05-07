package br.eti.arthurgregorio.library.application.components;

import br.eti.arthurgregorio.library.domain.entities.administration.User;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 03/04/2020
 */
@Component
public class AuthenticatedUser {

    private UserRepository userRepository;

    /**
     *
     * @param userRepository
     */
    public AuthenticatedUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * @return
     */
    public Optional<User> get() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return this.userRepository.findByUsername(authentication.getName());
    }
}