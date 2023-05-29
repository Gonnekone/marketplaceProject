package spring.app.marketplace.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.marketplace.models.Order;
import spring.app.marketplace.security.UserPrincipal;
import spring.app.marketplace.services.PersonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final PersonService personService;

    @GetMapping
    public List<Order> orders(@AuthenticationPrincipal UserPrincipal principal) {
        return personService.findById(principal.getId()).get().getOrders();
    }
}
