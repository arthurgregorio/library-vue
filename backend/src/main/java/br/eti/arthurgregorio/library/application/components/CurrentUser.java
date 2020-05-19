package br.eti.arthurgregorio.library.application.components;

import br.eti.arthurgregorio.library.domain.entities.administration.Grant;
import br.eti.arthurgregorio.library.domain.entities.administration.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/02/2020
 */
@EqualsAndHashCode
public class CurrentUser implements UserDetails {

    @Getter
    private String name;
    @Getter
    private String email;

    private String username;

    @JsonIgnore
    private boolean active;
    @JsonIgnore
    private String password;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    /**
     *
     * @param email
     * @param password
     * @param authorities
     */
    public CurrentUser(String name, String username, String email, boolean active, String password, Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.email = email;
        this.active = active;
        this.password = password;
        this.username = username;
        this.authorities = authorities;
    }

    /**
     *
     * @param user
     * @return
     */
    public static CurrentUser build(User user) {

        final var grants = user.getGrants()
                .stream()
                .map(Grant::getAuthority)
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        return new CurrentUser(user.getName(), user.getUsername(), user.getEmail(), user.isActive(),
                user.getPassword(), grants);
    }

    /**
     *
     * @return
     */
    @JsonProperty("authorities")
    public List<String> getGrants() {
        return this.authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return this.active;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.active;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.active;
    }
}