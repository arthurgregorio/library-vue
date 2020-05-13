package br.eti.arthurgregorio.library.application.controllers.administration;

import br.eti.arthurgregorio.library.domain.dto.UserForm;
import br.eti.arthurgregorio.library.domain.dto.validation.Adding;
import br.eti.arthurgregorio.library.domain.dto.validation.Editing;
import br.eti.arthurgregorio.library.domain.entities.administration.Authority;
import br.eti.arthurgregorio.library.domain.entities.administration.Grant;
import br.eti.arthurgregorio.library.domain.entities.administration.User;
import br.eti.arthurgregorio.library.domain.repositories.administration.UserRepository;
import br.eti.arthurgregorio.library.domain.services.UserService;
import br.eti.arthurgregorio.library.infrastructure.misc.RestPreconditions;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.remove;

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

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     *
     */
    @PostConstruct
    protected void configureMapper() {
        this.modelMapper.createTypeMap(User.class, UserForm.class)
                .addMapping(source -> "", UserForm::setPassword);
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
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserForm> getById(@PathVariable Long userId) {
        return this.userRepository.findById(userId)
                .map(found -> ResponseEntity.ok(this.modelMapper.map(found, UserForm.class)))
                .orElse(ResponseEntity.noContent().build());
    }

    /**
     *
     * @param model
     * @return
     */
    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Validated(Adding.class) UserForm model) {
        this.userService.save(this.modelMapper.map(model, User.class), model.getAuthorities());
        return ResponseEntity.ok().build();
    }

    /**
     *
     * @param userId
     * @param model
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserForm> update(@PathVariable long userId,
                                           @RequestBody @Validated(Editing.class) UserForm model) {
        return this.userRepository.findById(userId)
                .map(found -> {
                    final var user = this.modelMapper.map(model, User.class);
                    final var saved = this.userService.update(user, model.getAuthorities());
                    return ResponseEntity.ok(this.modelMapper.map(saved, UserForm.class));
                })
                .orElse(ResponseEntity.badRequest().build());
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
