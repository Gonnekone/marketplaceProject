package spring.app.marketplace.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.marketplace.models.Person;
import spring.app.marketplace.repositories.PersonRepository;
import spring.app.marketplace.util.Role;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(Role.USER);
        personRepository.save(person);
    }
}
