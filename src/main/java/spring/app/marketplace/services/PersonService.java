package spring.app.marketplace.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.marketplace.models.Person;
import spring.app.marketplace.repositories.PersonRepository;
import spring.app.marketplace.util.Role;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Optional<Person> findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public Optional<Person> findById(int userId) {
        return personRepository.findById(userId);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional
    public void changeRole(int id) {
        findById(id).get().setRole(Role.ADMIN);
    }
}
