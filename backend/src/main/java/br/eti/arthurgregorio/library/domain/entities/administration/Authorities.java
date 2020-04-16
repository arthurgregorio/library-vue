package br.eti.arthurgregorio.library.domain.entities.administration;

import java.util.List;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/02/2020
 */
public interface Authorities {

    String LIBRARIAN = "LIBRARIAN";
    String ADMINISTRATOR = "ADMINISTRATOR";

    List<String> ALL_AUTHORITIES = List.of(ADMINISTRATOR, LIBRARIAN);
}
