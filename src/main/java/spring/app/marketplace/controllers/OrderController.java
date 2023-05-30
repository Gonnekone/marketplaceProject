package spring.app.marketplace.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.marketplace.dto.OrderDTO;
import spring.app.marketplace.security.UserPrincipal;
import spring.app.marketplace.services.OrderService;
import spring.app.marketplace.services.PersonService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final PersonService personService;
    private final ModelMapper modelMapper;
    private final OrderService orderService;

    @GetMapping
    public List<OrderDTO> showOrders(@AuthenticationPrincipal UserPrincipal principal) {
        return personService.findById(principal.getId()).get().getOrders().stream()
                .map(x -> modelMapper.map(x, OrderDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDTO showOrder(@AuthenticationPrincipal UserPrincipal principal,
                          @PathVariable int id) {
        return personService.findById(principal.getId()).get().getOrders().stream()
                .filter(x -> x.getId() == id).map(x -> modelMapper.map(x, OrderDTO.class))
                .findAny().orElse(null);
    }

    @PostMapping
    public void makeOrder(@AuthenticationPrincipal UserPrincipal principal) {
        orderService.makeOrder(personService.findById(principal.getId()).get());
    }
}
