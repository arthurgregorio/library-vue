package br.eti.arthurgregorio.library.domain.entities.administration;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static br.eti.arthurgregorio.library.infrastructure.jpa.DefaultSchemes.ADMINISTRATION;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/02/2020
 */
@Entity
@Audited
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "grants", schema = ADMINISTRATION)
public class Grant extends PersistentEntity {

    @Getter
    @Setter
    @ManyToOne(optional = false)
    @NotNull(message = "grant.null-authority")
    @JoinColumn(name = "id_authority", nullable = false)
    private Authority authority;
    @Getter
    @Setter
    @ManyToOne(optional = false)
    @NotNull(message = "grant.null-user")
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    /**
     *
     * @param user
     * @param authority
     */
    public Grant(User user, Authority authority) {
        this.user = user;
        this.authority = authority;
    }
}
