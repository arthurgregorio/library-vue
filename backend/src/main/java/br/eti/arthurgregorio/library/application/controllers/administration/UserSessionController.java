package br.eti.arthurgregorio.library.application.controllers.administration;

import br.eti.arthurgregorio.library.application.components.AuthenticatedUser;
import br.eti.arthurgregorio.library.domain.dto.Principal;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 19/05/2020
 */
@RestController
@RequestMapping("/api/me")
public class UserSessionController {

    private ModelMapper modelMapper;
    private AuthenticatedUser authenticatedUser;

    /**
     *
     * @param modelMapper
     * @param authenticatedUser
     */
    public UserSessionController(ModelMapper modelMapper, AuthenticatedUser authenticatedUser) {
        this.modelMapper = modelMapper;
        this.authenticatedUser = authenticatedUser;
    }

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<Principal> me() {
        return this.authenticatedUser.get()
                .map(user -> ResponseEntity.ok(this.modelMapper.map(user, Principal.class)))
                .orElse(ResponseEntity.noContent().build());
    }
}
