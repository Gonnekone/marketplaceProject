package spring.app.marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.app.marketplace.models.Good;

import java.util.Optional;

@Repository
public interface GoodRepository extends JpaRepository<Good, Integer> {
    Optional<Good> findByName(String name);
}
