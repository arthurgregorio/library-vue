package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.domain.dto.RegistrationForm;
import br.eti.arthurgregorio.library.domain.services.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/05/2020
 */
@RestController
@RequestMapping("/user-account")
public class UserAccountController {

    private UserAccountService userAccountService;

    /**
     *
     * @param userAccountService
     */
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    /**
     *
     * @param registrationForm
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated RegistrationForm registrationForm) {
        this.userAccountService.register(registrationForm);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * @param token
     * @return
     */
    @GetMapping("/activate/{token}")
    public ResponseEntity<?> activate(@PathVariable String token) {
        this.userAccountService.activate(token);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * @param email
     * @return
     */
    @GetMapping("/recover-password/{email}")
    public ResponseEntity<?> recoverPassword(@PathVariable String email) {
        this.userAccountService.recoverPassword(email);
        return ResponseEntity.ok().build();
    }
}