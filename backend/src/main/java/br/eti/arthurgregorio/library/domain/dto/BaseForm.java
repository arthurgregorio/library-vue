package br.eti.arthurgregorio.library.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 11/05/2020
 */
public class BaseForm {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private LocalDateTime createdOn;
    @Getter
    @Setter
    private LocalDateTime updatedOn;
}
