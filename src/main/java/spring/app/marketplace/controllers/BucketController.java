package spring.app.marketplace.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.marketplace.dto.BucketDTO;
import spring.app.marketplace.security.UserPrincipal;
import spring.app.marketplace.services.BucketService;
import spring.app.marketplace.services.GoodService;
import spring.app.marketplace.services.PersonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bucket")
public class BucketController {
    private final BucketService bucketService;
    private final PersonService personService;
    private final GoodService goodService;

    @GetMapping("/bucket")
    public BucketDTO bucket(@AuthenticationPrincipal UserPrincipal principal) {
        return bucketService.showBucket(personService.findById(principal.getId()).get());
    }

    @PatchMapping("/bucket")
    public void addGoodToBucket(@AuthenticationPrincipal UserPrincipal principal,
                                @RequestParam(name = "amount", required = false) Integer amount,
                                @RequestParam(name = "goodId") int goodId) {

        bucketService.addGoodToBucket(personService.findById(principal.getId()).get(),
                goodService.findById(goodId).get(), amount);
    }
}
