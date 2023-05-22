package spring.app.marketplace.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.marketplace.models.Good;
import spring.app.marketplace.repositories.GoodRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoodService {

    private final GoodRepository goodRepository;

    public List<Good> findAll() {
        return goodRepository.findAll();
    }

    public List<Good> findAll(Boolean priceOrder) {
        List<Good> res = goodRepository.findAll(Sort.by("price"));
        if (priceOrder) {
            return res;
        }

        Collections.reverse(res);
        return res;
    }

    public Optional<Good> findById(int id) {
        return goodRepository.findById(id);
    }
}
