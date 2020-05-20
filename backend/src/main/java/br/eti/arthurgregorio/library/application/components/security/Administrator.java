package br.eti.arthurgregorio.library.application.components.security;

import br.eti.arthurgregorio.library.domain.entities.administration.Authorities;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * Authority annotation for admin authorization
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 19/05/2020
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@PreAuthorize("hasAuthority('" + Authorities.ADMINISTRATOR + "')")
public @interface Administrator { }
