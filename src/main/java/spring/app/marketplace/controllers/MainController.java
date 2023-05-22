package spring.app.marketplace.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.marketplace.exceptions.GoodNotFoundException;
import spring.app.marketplace.models.Good;
import spring.app.marketplace.services.GoodService;
import spring.app.marketplace.util.ErrorResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final GoodService goodService;

    @GetMapping("/")
    public List<Good> allGoods(@RequestParam(name = "priceOrder", required = false) Boolean priceOrder) {

        if (priceOrder == null) {
            // in order of addition
            return goodService.findAll();
        } else if (priceOrder) {
            // asc
            return goodService.findAll(true);
        }
        // desc
        return goodService.findAll(false);
    }

    @PostMapping("/search")
    public List<Good> searchPage(@RequestParam("text") String query) {
        // TODO
        return null;
    }

    @GetMapping("/good/{id}")
    public Good show(@PathVariable("id") int id) {
        Optional<Good> good = goodService.findById(id);

        if (good.isPresent()) {
            return good.get();
        }

        throw new GoodNotFoundException("Good wasn't found");
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(GoodNotFoundException e) {
        ErrorResponse goodErrorResponse = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(goodErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
