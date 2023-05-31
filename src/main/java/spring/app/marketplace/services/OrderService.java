package spring.app.marketplace.services;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.marketplace.models.Good;
import spring.app.marketplace.models.Order;
import spring.app.marketplace.models.Person;
import spring.app.marketplace.repositories.OrderRepository;
import spring.app.marketplace.repositories.PersonRepository;
import spring.app.marketplace.util.Status;
import spring.app.marketplace.util.UniqueStringGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final EntityManager entityManager;
    private final PersonRepository personRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional
    public void makeOrder(Person person) {
        Order order = Order.builder()
                .owner(person)
                .status(Status.NEW)
                .created_at(LocalDateTime.now())
                .code(UniqueStringGenerator.generateUniqueString(person.getBucket().getGoods(),
                        person, LocalDateTime.now(), person.getId()))
                .build();

        orderRepository.save(order);
        person.addOrder(order);

        for (Good good : person.getBucket().getGoods()) {
            int amount = entityManager.createNativeQuery("select amount from good_bucket_amount where good_id = ? and bucket_id = ?")
                            .setParameter(1, good.getId())
                            .setParameter(2, person.getBucket().getId())
                                    .getFirstResult();

            entityManager.createNativeQuery("insert into order_good_amount values (?, ?, ?)")
                    .setParameter(1, good.getId())
                    .setParameter(2, order.getId())
                    .setParameter(3, amount)
                    .executeUpdate();
        }

        person.clearBucket();
    }

    public List<Order> findOrderByCodeEndingWith(String query) {
        return orderRepository.findOrderByCodeEndingWith(query);
    }
}
