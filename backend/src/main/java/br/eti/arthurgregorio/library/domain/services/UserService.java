package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.domain.entities.administration.Authority;
import br.eti.arthurgregorio.library.domain.entities.administration.Grant;
import br.eti.arthurgregorio.library.domain.entities.administration.User;
import br.eti.arthurgregorio.library.domain.logics.user.UserSavingLogic;
import br.eti.arthurgregorio.library.domain.logics.user.UserUpdatingLogic;
import br.eti.arthurgregorio.library.domain.repositories.administration.GrantRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserActivationRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

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
    private UserActivationRepository userActivationRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private List<UserSavingLogic> userSavingLogics;
    @Autowired
    private List<UserUpdatingLogic> userUpdatingLogics;

    /**
     *
     * @param user
     * @param authorities
     */
    @Transactional
    public void save(User user, List<Authority> authorities) {
        this.userSavingLogics.forEach(logic -> logic.run(user));
        user.setPassword(this.encoder.encode(user.getPassword()));
        this.userRepository.save(user);
        authorities.forEach(authority -> this.grantRepository.save(new Grant(user, authority)));
    }

    /**
     *
     * @param user
     * @param authorities
     * @return
     */
    @Transactional
    public User update(User user, List<Authority> authorities) {
        this.userUpdatingLogics.forEach(logic -> logic.run(user));

        // if password is blank, keep the actual password, if not, encode and update a new one base on password field
        if (isBlank(user.getPassword())) {
            this.userRepository.findById(user.getId()).ifPresent(found -> user.setPassword(found.getPassword()));
        } else {
            final var encoded = this.encoder.encode(user.getPassword());
            user.setPassword(encoded);
        }

        this.userRepository.save(user);

        if (authorities != null && !authorities.isEmpty()) {
            this.grantRepository.deleteByUser_id(user.getId());
            authorities.forEach(authority -> this.grantRepository.save(new Grant(user, authority)));
        }

        final var grants = this.grantRepository.findByUser(user);
        user.setGrants(grants);

        return user;
    }

    /**
     *
     * @param userId
     */
    @Transactional
    public void delete(long userId) {
        this.userRepository.findById(userId)
                .ifPresent(user -> {
                    this.grantRepository.deleteByUser_id(userId);
                    this.userActivationRepository.deleteByUser_id(userId);
                    this.userRepository.delete(user);
                });
    }
}