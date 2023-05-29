package spring.app.marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.app.marketplace.models.Bucket;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Integer> {
}
