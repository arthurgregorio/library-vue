package br.eti.arthurgregorio.library.domain.dto.validation;

import javax.validation.GroupSequence;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 10/03/2020
 */
@GroupSequence({Adding.class, Editing.class})
public interface Always { }