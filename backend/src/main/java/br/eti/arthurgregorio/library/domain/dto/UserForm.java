package br.eti.arthurgregorio.library.domain.dto;

import br.eti.arthurgregorio.library.domain.dto.validation.Adding;
import br.eti.arthurgregorio.library.domain.dto.validation.Always;
import br.eti.arthurgregorio.library.domain.entities.administration.Authority;
import br.eti.arthurgregorio.library.domain.entities.administration.Grant;
import br.eti.arthurgregorio.library.domain.entities.administration.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/05/2020
 */
@ToString
public class UserForm extends BaseForm {

    @Getter
    @Setter
    private boolean active;
    @Getter
    @Setter
    @NotBlank(message = "user.empty-name", groups = Always.class)
    private String name;
    @Getter
    @Setter
    @NotBlank(message = "user.empty-username", groups = Always.class)
    private String username;
    @Getter
    @Setter
    @NotBlank(message = "user.empty-password", groups = Adding.class)
    private String password;
    @Getter
    @Setter
    @NotBlank(message = "user.empty-email", groups = Always.class)
    private String email;

    @Getter
    @Setter
    @NotEmpty(message = "user.empty-authorities", groups = Always.class)
    private List<Authority> authorities;

    /**
     *
     */
    public UserForm() {
        this.authorities = new ArrayList<>();
    }

    /**
     *
     * @param grants
     */
    public void setGrants(List<Grant> grants) {
        this.authorities = grants.stream().map(Grant::getAuthority).collect(Collectors.toList());
    }
}