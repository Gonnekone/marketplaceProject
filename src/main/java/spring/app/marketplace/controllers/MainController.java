package spring.app.marketplace.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.marketplace.dto.BucketDTO;
import spring.app.marketplace.dto.GoodDTO;
import spring.app.marketplace.exceptions.GoodNotFoundException;
import spring.app.marketplace.models.Good;
import spring.app.marketplace.security.UserPrincipal;
import spring.app.marketplace.services.BucketService;
import spring.app.marketplace.services.GoodService;
import spring.app.marketplace.services.PersonService;
import spring.app.marketplace.util.Response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final GoodService goodService;
    private final PersonService personService;
    private final ModelMapper modelMapper;
    private final BucketService bucketService;

    @GetMapping("/")
    public List<GoodDTO> allGoods(@RequestParam(name = "priceOrder", required = false) Boolean priceOrder) {

        if (priceOrder == null) {
            // in order of addition
            return goodService.findAll().stream().map(x -> modelMapper.map(x, GoodDTO.class))
                    .collect(Collectors.toList());

        } else if (priceOrder) {
            // asc
            return goodService.findAll(true).stream()
                    .map(x -> modelMapper.map(x, GoodDTO.class)).collect(Collectors.toList());
        }
        // desc
        return goodService.findAll(false).stream()
                .map(x -> modelMapper.map(x, GoodDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("/search")
    public List<Good> searchPage(@RequestParam("text") String query) {
        // TODO
        return null;
    }

    @GetMapping("/goods/{id}")
    public Good show(@PathVariable("id") int id) {
        Optional<Good> good = goodService.findById(id);

        if (good.isPresent()) {
            return good.get();
        }

        throw new GoodNotFoundException("Good wasn't found");
    }

    @ExceptionHandler
    private ResponseEntity<Response> handleException(GoodNotFoundException e) {
        Response goodResponse = new Response(e.getMessage());

        return new ResponseEntity<>(goodResponse, HttpStatus.BAD_REQUEST);
    }
}
