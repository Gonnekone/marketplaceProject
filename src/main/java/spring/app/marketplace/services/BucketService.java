package spring.app.marketplace.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.marketplace.models.Bucket;
import spring.app.marketplace.models.Good;
import spring.app.marketplace.models.Person;
import spring.app.marketplace.repositories.BucketRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BucketService {
    @PersistenceContext
    private EntityManager entityManager;

    private final BucketRepository bucketRepository;

    @Transactional
    public void save(Bucket bucket) {
        bucketRepository.save(bucket);
    }

    @Transactional
    public void addGoodToBucket(Person person, Good good, Integer amount) {
        if (person.getBucket() == null) {

            Bucket bucket = Bucket.builder()
                    .owner(person)
                    .build();

            save(bucket);

            bucket.setOwner(person);
            person.setBucket(bucket);
        }

        entityManager.createNativeQuery("insert into good_bucket_amount values (?, ?, ?)")
                .setParameter(1, good.getId())
                .setParameter(2, person.getBucket().getId())
                .setParameter(3, amount == null ? 1 : amount)
                .executeUpdate();
    }
}
