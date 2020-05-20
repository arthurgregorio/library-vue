package br.eti.arthurgregorio.library.domain.dto;

import br.eti.arthurgregorio.library.domain.entities.administration.Authority;
import br.eti.arthurgregorio.library.domain.entities.administration.Grant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 19/05/2020
 */
@ToString
@EqualsAndHashCode
public class Principal {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String username;

    @Getter
    private List<String> authorities;

    /**
     *
     * @param grants
     */
    public void setGrants(List<Grant> grants) {
        this.authorities = grants.stream()
                .map(Grant::getAuthority)
                .map(Authority::getName)
                .collect(Collectors.toList());
    }
}
