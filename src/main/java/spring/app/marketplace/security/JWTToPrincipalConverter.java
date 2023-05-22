package spring.app.marketplace.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import spring.app.marketplace.models.Person;

@Component
public class JWTToPrincipalConverter {

    public UserPrincipal convert(DecodedJWT decodedJWT) {
        Person person = new Person();

        person.setEmail(decodedJWT.getClaim("email").asString());

        return UserPrincipal.builder()
                .person(person)
                .build();
    }
}
