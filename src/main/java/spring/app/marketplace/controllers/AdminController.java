package spring.app.marketplace.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.marketplace.models.Order;
import spring.app.marketplace.models.Person;
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
}
