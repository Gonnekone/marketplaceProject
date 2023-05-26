package spring.app.marketplace.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import spring.app.marketplace.exceptions.PersonNotCreatedException;
import spring.app.marketplace.exceptions.PersonNotLoggedInException;
import spring.app.marketplace.login.LoginRequest;
import spring.app.marketplace.login.LoginResponse;
import spring.app.marketplace.models.Person;
import spring.app.marketplace.security.JWTUtil;
import spring.app.marketplace.services.PersonService;
import spring.app.marketplace.services.RegisterService;
import spring.app.marketplace.util.Response;
import spring.app.marketplace.util.RequestValidator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RegisterService registerService;
    private final ModelMapper modelMapper;
    private final RequestValidator requestValidator;
    private final PersonService personService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessage.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new PersonNotLoggedInException(errorMessage.toString());
        }

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        Person person = personService.findByEmail(request.getEmail()).get();

        String token = jwtUtil.issue(person.getId());

        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }

    @PostMapping("/registration")
    public LoginResponse register(@RequestBody @Valid LoginRequest request,
                       BindingResult bindingResult) {

        requestValidator.validate(request, bindingResult);

        if (bindingResult.hasErrors()) {

            StringBuilder errorMessage = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessage.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new PersonNotCreatedException(errorMessage.toString());
        }

        registerService.register(modelMapper.map(request, Person.class));

        Person person = personService.findByEmail(request.getEmail()).get();

        String token = jwtUtil.issue(person.getId());

        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }

    @ExceptionHandler
    private ResponseEntity<Response> handleException(PersonNotCreatedException e) {
        Response personResponse = new Response(e.getMessage());

        return new ResponseEntity<>(personResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<Response> handleException(PersonNotLoggedInException e) {
        Response personResponse = new Response(e.getMessage());

        return new ResponseEntity<>(personResponse, HttpStatus.BAD_REQUEST);
    }
}
