package spring.app.marketplace.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.app.marketplace.dto.BucketDTO;
import spring.app.marketplace.dto.GoodDTO;
import spring.app.marketplace.dto.OrderDTO;
import spring.app.marketplace.dto.PersonDTO;
import spring.app.marketplace.exceptions.GoodNotFoundException;
import spring.app.marketplace.models.Order;
import spring.app.marketplace.models.Person;
import spring.app.marketplace.services.CategoryService;
import spring.app.marketplace.services.GoodService;
import spring.app.marketplace.services.OrderService;
import spring.app.marketplace.services.PersonService;
import spring.app.marketplace.util.Response;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<OrderDTO> orders() {
        return orderService.findAll().stream().map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/users")
    public List<PersonDTO> users() {
        return personService.findAll().stream().map(this::convertToPersonDTO)
                .collect(Collectors.toList());
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

    @DeleteMapping("/goods/{id}")
    public void deleteGood(@PathVariable int id) {
        goodService.deleteById(id);
    }

    @PatchMapping("/goods/{id}")
    public void changeGood(@PathVariable int id,
                           @RequestBody @Valid GoodDTO goodDTO,
                            BindingResult bindingResult) {

        if (!goodService.findById(id).isPresent()) {
            throw new GoodNotFoundException("Good wasn't found");
        }
        goodService.deleteById(id);
        goodService.addGood(goodDTO);
    }

    @PostMapping("/search")
    public List<OrderDTO> resultSearchPage(@RequestParam("query") String query) {
        return orderService.findOrderByCodeEndingWith(query).stream().map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setOwnerId(order.getOwner().getId());

        return orderDTO;
    }

    private PersonDTO convertToPersonDTO(Person person) {
        PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
        personDTO.setBucket(modelMapper.map(person.getBucket(), BucketDTO.class));
        personDTO.setOrders(person.getOrders().stream()
                .map(x -> modelMapper.map(x, OrderDTO.class)).collect(Collectors.toList()));

        return personDTO;
    }

    @ExceptionHandler
    private ResponseEntity<Response> handleException(GoodNotFoundException e) {
        Response goodResponse = new Response(e.getMessage());

        return new ResponseEntity<>(goodResponse, HttpStatus.BAD_REQUEST);
    }
}
