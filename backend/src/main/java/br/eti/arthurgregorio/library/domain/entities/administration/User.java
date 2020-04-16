package br.eti.arthurgregorio.library.domain.entities.administration;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.eti.arthurgregorio.library.infrastructure.jpa.DefaultSchemes.ADMINISTRATION;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/02/2020
 */
@Entity
@Audited
@ToString
@EqualsAndHashCode(callSuper = true)
@Table(name = "users", schema = ADMINISTRATION)
public class User extends PersistentEntity {

    @Getter
    @Setter
    @NotBlank(message = "user.empty-name")
    @Column(name = "name", nullable = false, length = 90)
    private String name;
    @Getter
    @Setter
    @NotBlank(message = "user.empty-email")
    @Column(name = "email", nullable = false, length = 90)
    private String email;
    @Getter
    @Setter
    @NotBlank(message = "user.empty-username")
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    @Getter
    @Setter
    @JsonIgnore
    @NotBlank(message = "user.empty-password")
    @Column(name = "password", nullable = false)
    private String password;
    @Getter
    @Setter
    @Column(name = "active", nullable = false)
    private boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = {REMOVE})
    private final List<Grant> grants;

    /**
     *
     */
    public User() {
        this.active = true;
        this.grants = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public List<Grant> getGrants() {
        return Collections.unmodifiableList(this.grants);
    }
}
