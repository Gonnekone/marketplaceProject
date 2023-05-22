package spring.app.marketplace.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import spring.app.marketplace.models.Person;
import spring.app.marketplace.repositories.PersonRepository;
import spring.app.marketplace.security.UserPrincipal;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByEmail(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new UserPrincipal(person.get());
    }
}
