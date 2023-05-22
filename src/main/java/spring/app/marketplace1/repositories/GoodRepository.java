package spring.app.marketplace1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.app.marketplace1.models.Good;

@Repository
public interface GoodRepository extends JpaRepository<Good, Integer> {
}
