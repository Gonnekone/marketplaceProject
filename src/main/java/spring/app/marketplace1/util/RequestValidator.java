package spring.app.marketplace1.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.app.marketplace1.models.LoginRequest;
import spring.app.marketplace1.repositories.PersonRepository;

@RequiredArgsConstructor
@Component
public class RequestValidator implements Validator {

    private final PersonRepository personRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginRequest request = (LoginRequest) target;

        if (personRepository.findByEmail(request.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is already in use");
        }
    }
}
