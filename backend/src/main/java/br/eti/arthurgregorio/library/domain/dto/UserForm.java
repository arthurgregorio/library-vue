package br.eti.arthurgregorio.library.domain.dto;

import br.eti.arthurgregorio.library.domain.entities.administration.Authority;
import br.eti.arthurgregorio.library.domain.entities.administration.Grant;
import br.eti.arthurgregorio.library.domain.entities.administration.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class UserForm {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    @NotBlank(message = "user-form.name")
    private String name;
    @Getter
    @Setter
    @NotBlank(message = "user-form.username")
    private String username;
    @Getter
    @Setter
    @NotBlank(message = "user.empty-password")
    private String password;
    @Getter
    @Setter
    @NotBlank(message = "user.empty-email")
    private String email;

    @Setter
    private List<Authority> authorities;

    /**
     *
     * @return
     */
    public List<Authority> getAuthorities() {
        return Collections.unmodifiableList(this.authorities);
    }

    /**
     *
     * @param user
     * @return
     */
    public static UserForm of(User user) {

        final UserForm userForm = new UserForm();

        userForm.setId(user.getId());
        userForm.setName(user.getName());
        userForm.setEmail(user.getEmail());
        userForm.setUsername(user.getUsername());

        final var authorities = user.getGrants().stream()
                .map(Grant::getAuthority)
                .collect(Collectors.toList());

        userForm.setAuthorities(authorities);

        return userForm;
    }
}