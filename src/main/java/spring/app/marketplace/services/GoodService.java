package spring.app.marketplace.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.marketplace.dto.CategoryDTO;
import spring.app.marketplace.dto.GoodDTO;
import spring.app.marketplace.models.Category;
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
    private final CategoryService categoryService;

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

    @Transactional
    public void save(Good good) {
        goodRepository.save(good);
    }

    public Optional<Good> findByName(String name) {
        return goodRepository.findByName(name);
    }

    @Transactional
    public void addGood(GoodDTO goodDTO) {

        Good good = Good.builder()
                .name(goodDTO.getName())
                .price(goodDTO.getPrice())
                .description(goodDTO.getDescription())
                .build();

        goodRepository.save(good);

        for (CategoryDTO i : goodDTO.getCategories()) {

            if (!categoryService.findByName(i.getName()).isPresent()) {
                categoryService.save(Category.builder()
                        .name(i.getName())
                        .build());
            }

            categoryService.findByName(i.getName()).get().addGood(good);
            good.addCategory(categoryService.findByName(i.getName()).get());
        }

        save(good);
    }

    @Transactional
    public void deleteById(int id) {
        goodRepository.deleteById(id);
    }
}
