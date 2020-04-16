package br.eti.arthurgregorio.library.infrastructure.spring.init;

import br.eti.arthurgregorio.library.domain.entities.administration.User;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserRepository;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class CreateAdminTask implements InitialTask {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    /**
     *
     * @param userRepository
     * @param passwordEncoder
     */
    public CreateAdminTask(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void perform() {
        this.userRepository.findByUsername("admin")
                .ifPresentOrElse(saved -> {/* empty */}, () -> {

                    final User user = new User();

                    user.setName("Administrador");
                    user.setUsername("admin");
                    user.setEmail("admin@admin.eti.br");
                    user.setPassword(this.passwordEncoder.encode("admin"));

                    this.userRepository.save(user);
                });
    }
}
