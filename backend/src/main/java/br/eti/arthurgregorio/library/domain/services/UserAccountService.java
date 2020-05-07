package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.application.components.MailMessage;
import br.eti.arthurgregorio.library.domain.dto.RegistrationForm;
import br.eti.arthurgregorio.library.domain.entities.administration.ActivationStatus;
import br.eti.arthurgregorio.library.domain.entities.administration.User;
import br.eti.arthurgregorio.library.domain.entities.administration.UserActivation;
import br.eti.arthurgregorio.library.domain.exceptions.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.administration.AuthorityRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.GrantRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserActivationRepository;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserRepository;
import br.eti.arthurgregorio.library.infrastructure.misc.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.eti.arthurgregorio.library.infrastructure.misc.Translator.translate;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/05/2020
 */
@Service
@Transactional(readOnly = true)
public class UserAccountService {

    @Value("${library.application-url}")
    private String applicationUrl;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GrantRepository grantRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserActivationRepository userActivationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     *
     * @param registrationForm
     */
    @Transactional
    public void register(RegistrationForm registrationForm) {

        final User user = new User();

        this.requestActivation(user);
    }

    /**
     *
     * @param user
     */
    @Transactional
    public void requestActivation(User user) {

        final var activation = this.userActivationRepository.save(new UserActivation(user));

        final MailMessage mailMessage = new MailMessage();

        mailMessage.setTemplateName("account-activation");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(translate("account-activation.subject"));

        mailMessage.addProperty("name", user.getName());
        mailMessage.addProperty("applicationUrl", this.applicationUrl + "/");
        mailMessage.addProperty("imageLogo", this.applicationUrl + "/logo.png");

        final String activationLink = this.applicationUrl + "/account-activation/" + activation.getToken();
        mailMessage.addProperty("link", activationLink);

        this.eventPublisher.publishEvent(mailMessage);
    }

    /**
     *
     * @param token
     */
    @Transactional
    public void activate(String token) {
        this.userActivationRepository.findByTokenAndActivationStatus(token, ActivationStatus.WAITING)
                .ifPresentOrElse(activation -> {
                    this.userRepository.save(activation.getUser().enable());
                    this.userActivationRepository.save(activation.activate());
                }, () -> {
                    throw new BusinessLogicException("error.user-activation.token-not-found");
                });
    }

    /**
     *
     * @param email
     */
    @Transactional
    public void recoverPassword(String email) {
        this.userRepository.findByEmail(email)
                .ifPresent(user -> {
                    final String newPassword = PasswordGenerator.generate();
                    user.setPassword(this.passwordEncoder.encode(newPassword));

                    this.userRepository.save(user);

                    final MailMessage mailMessage = new MailMessage();

                    mailMessage.setTemplateName("password-recover");
                    mailMessage.setTo(user.getEmail());
                    mailMessage.setSubject(translate("password-recover.subject"));

                    mailMessage.addProperty("applicationUrl", this.applicationUrl + "/");
                    mailMessage.addProperty("imageLogo", this.applicationUrl + "/logo.png");

                    mailMessage.addProperty("name", user.getName());
                    mailMessage.addProperty("password", newPassword);

                    this.eventPublisher.publishEvent(mailMessage);
                });
    }
}