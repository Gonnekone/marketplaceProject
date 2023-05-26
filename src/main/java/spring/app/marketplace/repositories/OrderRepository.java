package spring.app.marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.app.marketplace.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
