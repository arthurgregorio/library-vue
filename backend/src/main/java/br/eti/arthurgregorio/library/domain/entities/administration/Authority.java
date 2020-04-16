package br.eti.arthurgregorio.library.domain.entities.administration;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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
@Table(name = "authorities", schema = ADMINISTRATION)
public class Authority extends PersistentEntity {

    @Getter
    @Setter
    @NotBlank(message = "authority.empty-name")
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    /**
     *
     * @param name
     */
    public Authority(String name) {
        this.name = name;
    }
}
