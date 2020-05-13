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
@ToString(exclude = "grants")
@EqualsAndHashCode(callSuper = true, exclude = "grants")
@Table(name = "users", schema = ADMINISTRATION)
public class User extends PersistentEntity {

    @Getter
    @Setter
    @Column(name = "name", nullable = false, length = 90)
    private String name;
    @Getter
    @Setter
    @Column(name = "email", nullable = false, length = 90)
    private String email;
    @Getter
    @Setter
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    @Getter
    @Setter
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
    @Getter
    @Setter
    @Column(name = "active", nullable = false)
    private boolean active;

    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = {REMOVE})
    private List<Grant> grants;

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
    public User enable() {
        this.active = true;
        return this;
    }

    /**
     *
     * @return
     */
    public List<Grant> getGrants() {
        return Collections.unmodifiableList(this.grants);
    }
}
