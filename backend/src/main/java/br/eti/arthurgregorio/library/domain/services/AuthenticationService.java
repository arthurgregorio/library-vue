package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.domain.repositories.administration.UserRepository;
import br.eti.arthurgregorio.library.application.components.CurrentUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/02/2020
 */
@Service
@Transactional(readOnly = true)
public class AuthenticationService implements UserDetailsService {

    private UserRepository userRepository;

    /**
     *
     * @param userRepository
     */
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final var user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + "can't be found!"));

        return CurrentUser.build(user);
    }
}
