package com.clevertec.checkrunner.controller;

import com.clevertec.checkrunner.dto.DiscountCardDto;
import com.clevertec.checkrunner.service.DiscountCardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.clevertec.checkrunner.controller.DiscountCardController.DISCOUNT_CARD_API_PATH;

@RestController
@Validated
@RequestMapping(value = DISCOUNT_CARD_API_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DiscountCardController {

    public static final String DISCOUNT_CARD_API_PATH = "/api/v0/discountcard";

    private final DiscountCardService discountCardService;

    @PostMapping
    public ResponseEntity<DiscountCardDto> createDiscountCard(@RequestBody @Valid DiscountCardDto discountCardDto) {
        DiscountCardDto discountCard = discountCardService.createDiscountCard(discountCardDto);
        return new ResponseEntity<>(discountCard, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DiscountCardDto>> findAllDiscountCards() {
        List<DiscountCardDto> discountCards = discountCardService.getAllDiscountCards();
        return new ResponseEntity<>(discountCards, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountCardDto> findDiscountCardById(@PathVariable @Valid @NotNull Long id) {
        DiscountCardDto discountCard = discountCardService.getDiscountCardById(id);
        return new ResponseEntity<>(discountCard, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountCardDto> putDiscountCardById(
            @PathVariable @Valid @NotNull Long id,
            @RequestBody @Valid DiscountCardDto discountCardDto
    ) {
        DiscountCardDto discountCard = discountCardService.updateDiscountCardById(id, discountCardDto);
        return new ResponseEntity<>(discountCard, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscountCardById(@PathVariable @Valid @NotNull Long id) {
        discountCardService.deleteDiscountCardById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
