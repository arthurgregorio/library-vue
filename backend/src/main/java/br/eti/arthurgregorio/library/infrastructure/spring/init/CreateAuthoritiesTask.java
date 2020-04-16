package br.eti.arthurgregorio.library.infrastructure.spring.init;

import br.eti.arthurgregorio.library.domain.entities.administration.Authority;
import br.eti.arthurgregorio.library.domain.entities.administration.Authorities;
import br.eti.arthurgregorio.library.domain.repositories.administration.AuthorityRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 01/10/2019
 */
@Order(0)
@Component
public class CreateAuthoritiesTask implements InitialTask {

    private AuthorityRepository authorityRepository;

    /**
     *
     * @param roleRepository
     */
    public CreateAuthoritiesTask(AuthorityRepository roleRepository) {
        this.authorityRepository = roleRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void perform() {
        Authorities.ALL_AUTHORITIES.forEach(name ->
                this.authorityRepository.findByName(name)
                        .ifPresentOrElse(
                                authority -> { /* do nothing */ },
                                () -> this.authorityRepository.save(new Authority(name))));
    }
}
