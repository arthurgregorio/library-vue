package br.eti.arthurgregorio.library.application.controllers.administration;

import br.eti.arthurgregorio.library.domain.entities.administration.Authority;
import br.eti.arthurgregorio.library.domain.repositories.administration.AuthorityRepository;
import br.eti.arthurgregorio.library.infrastructure.misc.RestPreconditions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 08/05/2020
 */
@RestController
@RequestMapping("/api/authorities")
public class AuthorityController {

    private AuthorityRepository authorityRepository;

    /**
     *
     * @param authorityRepository
     */
    public AuthorityController(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Authority>> get() {
        return RestPreconditions.checkFound(this.authorityRepository.findAll());
    }
}