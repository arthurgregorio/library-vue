package br.eti.arthurgregorio.library.domain.entities.administration;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import br.eti.arthurgregorio.library.infrastructure.misc.RandomString;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;

import static br.eti.arthurgregorio.library.infrastructure.jpa.DefaultSchemes.ADMINISTRATION;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 26/03/2020
 */
@Entity
@Audited
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_activations", schema = ADMINISTRATION)
public class UserActivation extends PersistentEntity {

    @Getter
    @Setter
    @Column(name = "token", nullable = false, length = 20, unique = true)
    private String token;
    @Getter
    @Setter
    @Column(name = "expire_in", nullable = false)
    private LocalDateTime expireIn;
    @Getter
    @Setter
    @Column(name = "activated_on")
    private LocalDateTime activatedOn;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false, length = 20)
    private ActivationStatus activationStatus;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    /**
     *
     */
    public UserActivation(User user) {
        this.user = user;
        this.expireIn = LocalDateTime.now().plusDays(2);
        this.token = new RandomString(20).nextString();
        this.activationStatus = ActivationStatus.WAITING;
    }

    /**
     *
     */
    public UserActivation expire() {
        this.activationStatus = ActivationStatus.EXPIRED;
        return this;
    }

    /**
     *
     * @return
     */
    public UserActivation activate() {
        this.activationStatus = ActivationStatus.ACTIVATED;
        this.activatedOn = LocalDateTime.now();
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expireIn);
    }
}