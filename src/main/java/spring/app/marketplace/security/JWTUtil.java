package spring.app.marketplace.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    public DecodedJWT decode(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("Huyzon")
                .build();

        return verifier.verify(token);
    }

    public String issue(int id) {
        return JWT.create()
                .withSubject("User details")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("user_id", id)
                .withIssuer("Huyzon")
                .sign(Algorithm.HMAC256(secret));
    }
}
