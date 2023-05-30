package spring.app.marketplace.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.app.marketplace.dto.GoodDTO;
import spring.app.marketplace.models.Order;
import spring.app.marketplace.models.Person;
import spring.app.marketplace.services.CategoryService;
import spring.app.marketplace.services.GoodService;
import spring.app.marketplace.services.OrderService;
import spring.app.marketplace.services.PersonService;
import spring.app.marketplace.util.Response;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final OrderService orderService;
    private final PersonService personService;
    private final ModelMapper modelMapper;
    private final GoodService goodService;
    private final CategoryService categoryService;

    @GetMapping("/orders")
    public List<Order> orders() {
        return orderService.findAll();
    }

    @GetMapping("/users")
    public List<Person> users() {
        return personService.findAll();
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Response> changeRole(@PathVariable int id) {
        personService.changeRole(id);
        return new ResponseEntity<>(new Response("The role of user with id "
                + id + " has changed"), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public Person showPerson(@PathVariable int id) {
        return personService.findById(id).get();
    }

    @PostMapping("/goods")
    public void addGood(@RequestBody @Valid GoodDTO goodDTO,
                        BindingResult bindingResult) {

        goodService.addGood(goodDTO);
    }
}
