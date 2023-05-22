package spring.app.marketplace1.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JWTIssuer {

    @Value("${jwt_secret}")
    private String secret;

    public String issue(String email) {
        return JWT.create()
                .withSubject("User details")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("email", email)
                .withIssuer("Huyzon")
                .sign(Algorithm.HMAC256(secret));
    }
}
