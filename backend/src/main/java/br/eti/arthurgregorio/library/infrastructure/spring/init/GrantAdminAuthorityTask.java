package br.eti.arthurgregorio.library.infrastructure.spring.init;

import br.eti.arthurgregorio.library.domain.entities.administration.Authorities;
import br.eti.arthurgregorio.library.domain.entities.administration.Grant;
import br.eti.arthurgregorio.library.domain.repositories.administration.AuthorityRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.GrantRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 01/10/2019
 */
@Order(1)
@Component
public class GrantAdminAuthorityTask implements InitialTask {

    private UserRepository userRepository;
    private GrantRepository grantRepository;
    private AuthorityRepository authorityRepository;

    /**
     *
     * @param userRepository
     * @param grantRepository
     * @param authorityRepository
     */
    public GrantAdminAuthorityTask(UserRepository userRepository, GrantRepository grantRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.grantRepository = grantRepository;
        this.authorityRepository = authorityRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void perform() {

        final var admin = this.userRepository.findByUsername("admin")
                .orElseThrow(() -> new IllegalStateException("Admin user not found, create it and try again"));

        final var adminAuthority = this.authorityRepository.findByName(Authorities.ADMINISTRATOR)
                .orElseThrow(() -> new IllegalStateException("Administrator authority not found, create it and try again"));

        this.grantRepository.findByUserAndAuthority(admin, adminAuthority)
                .ifPresentOrElse(
                        grant -> {/* do nothing */},
                        () -> this.grantRepository.save(new Grant(admin, adminAuthority)));
    }
}
