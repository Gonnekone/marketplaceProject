package spring.app.marketplace.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.app.marketplace.models.Person;
import spring.app.marketplace.services.PersonService;

@Component
@RequiredArgsConstructor
public class JWTToPrincipalConverter {

    private final PersonService personService;

    public UserPrincipal convert(DecodedJWT decodedJWT) {
        Person person = personService.findById(decodedJWT.getClaim("user_id").asInt()).get();

        return UserPrincipal.builder()
                .person(person)
                .build();
    }
}
