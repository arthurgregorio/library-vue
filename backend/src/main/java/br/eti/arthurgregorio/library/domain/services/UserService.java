package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.domain.dto.UserForm;
import br.eti.arthurgregorio.library.domain.entities.administration.User;
import br.eti.arthurgregorio.library.domain.logics.user.UserSavingLogic;
import br.eti.arthurgregorio.library.domain.logics.user.UserUpdatingLogic;
import br.eti.arthurgregorio.library.domain.repositories.administration.AuthorityRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.GrantRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserActivationRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private UserActivationRepository userActivationRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private List<UserSavingLogic> userSavingLogics;
    @Autowired
    private List<UserUpdatingLogic> userUpdatingLogics;

    /**
     *
     * @param userForm
     * @return
     */
    @Transactional
    public User save(UserForm userForm) {
        return null;
    }

    /**
     *
     * @param id
     * @param userForm
     * @return
     */
    @Transactional
    public User update(long id, UserForm userForm) {
        return null;
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