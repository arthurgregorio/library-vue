package br.eti.arthurgregorio.library.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/05/2020
 */
@ToString
@NoArgsConstructor
public class RegistrationForm {

    @Getter
    @Setter
    @NotBlank(message = "registration.empty-name")
    private String name;
    @Getter
    @Setter
    @NotBlank(message = "registration.empty-password")
    private String password;
    @Getter
    @Setter
    @NotBlank(message = "registration.empty-email")
    private String email;
}