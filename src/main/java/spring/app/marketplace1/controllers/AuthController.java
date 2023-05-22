package spring.app.marketplace1.controllers;

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
import spring.app.marketplace1.exceptions.PersonNotCreatedException;
import spring.app.marketplace1.exceptions.PersonNotLoggedInException;
import spring.app.marketplace1.models.LoginRequest;
import spring.app.marketplace1.models.LoginResponse;
import spring.app.marketplace1.models.Person;
import spring.app.marketplace1.security.JWTIssuer;
import spring.app.marketplace1.services.RegisterService;
import spring.app.marketplace1.util.ErrorResponse;
import spring.app.marketplace1.util.RequestValidator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JWTIssuer jwtIssuer;

    private final AuthenticationManager authenticationManager;

    private final RegisterService registerService;

    private final ModelMapper modelMapper;

    private final RequestValidator requestValidator;

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

        String token = jwtIssuer.issue(request.getEmail());

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

        String token = jwtIssuer.issue(request.getEmail());

        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(PersonNotCreatedException e) {
        ErrorResponse sensorErrorResponse = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(PersonNotLoggedInException e) {
        ErrorResponse sensorErrorResponse = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
