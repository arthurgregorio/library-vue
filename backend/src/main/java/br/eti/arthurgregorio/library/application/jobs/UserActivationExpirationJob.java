package br.eti.arthurgregorio.library.application.jobs;

import br.eti.arthurgregorio.library.domain.entities.administration.ActivationStatus;
import br.eti.arthurgregorio.library.domain.entities.administration.UserActivation;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserActivationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/05/2020
 */
@Component
public class UserActivationExpirationJob {

    private UserActivationRepository userActivationRepository;

    /**
     *
     * @param userActivationRepository
     */
    public UserActivationExpirationJob(UserActivationRepository userActivationRepository) {
        this.userActivationRepository = userActivationRepository;
    }

    /**
     *
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateExpiredActivations() {

        final var activations = this.userActivationRepository.findByActivationStatus(ActivationStatus.WAITING);

        activations.stream()
                .filter(UserActivation::isExpired)
                .forEach(activation -> this.userActivationRepository.save(activation.expire()));
    }
}