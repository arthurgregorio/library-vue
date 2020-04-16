package br.eti.arthurgregorio.library.application.controllers.administration;

import br.eti.arthurgregorio.library.domain.dto.UserForm;
import br.eti.arthurgregorio.library.domain.entities.administration.User;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserRepository;
import br.eti.arthurgregorio.library.domain.services.UserService;
import br.eti.arthurgregorio.library.infrastructure.misc.RestPreconditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 02/03/2020
 */
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private UserService userService;

    private UserRepository userRepository;

    /**
     *
     * @param userService
     * @param userRepository
     */
    public UsersController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserForm> getById(@PathVariable Long id) {
        return this.userRepository.findById(id)
                .map(found -> ResponseEntity.ok(UserForm.of(found)))
                .orElse(ResponseEntity.noContent().build());
    }

    /**
     *
     * @param filter
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<User>> get(@RequestParam String filter, Pageable pageable) {
        return RestPreconditions.checkFound(this.userRepository.findByFilter(filter, pageable));
    }

    /**
     *
     * @param model
     * @return
     */
    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserForm model) {
        return ResponseEntity.ok(this.userService.save(model));
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return this.userRepository.findById(id).map(user -> {
            this.userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.badRequest().build());
    }
}
