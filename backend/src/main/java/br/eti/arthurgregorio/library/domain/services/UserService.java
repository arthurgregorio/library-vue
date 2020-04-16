package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.domain.dto.UserForm;
import br.eti.arthurgregorio.library.domain.entities.administration.Grant;
import br.eti.arthurgregorio.library.domain.entities.administration.User;
import br.eti.arthurgregorio.library.domain.repositories.administration.AuthorityRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.GrantRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 08/03/2020
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GrantRepository grantRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder encoder;

    /**
     *
     * @param userForm
     * @return
     */
    @Transactional
    public User save(UserForm userForm) {

        final User user = new User();

        user.setEmail(userForm.getEmail());
        user.setName(userForm.getName());
        user.setPassword(this.encoder.encode(userForm.getPassword()));

        this.userRepository.save(user);

        userForm.getAuthorities().forEach(authority -> {
            this.authorityRepository.findById(authority.getId())
                    .ifPresent(found -> this.grantRepository.save(new Grant(user, found)));
        });

        return user;
    }
}