package ru.clevertec.checkrunner.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.checkrunner.config.PaginationProperties;
import ru.clevertec.checkrunner.dto.DiscountCardDto;
import ru.clevertec.checkrunner.dto.response.ApiResponse;
import ru.clevertec.checkrunner.service.DiscountCardService;

import java.util.Optional;

import static ru.clevertec.checkrunner.controller.DiscountCardController.DISCOUNT_CARD_API_PATH;
import static ru.clevertec.checkrunner.dto.response.ApiResponse.apiResponseEntity;

@RestController
@Validated
@RequestMapping(value = DISCOUNT_CARD_API_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DiscountCardController {

    public static final String DISCOUNT_CARD_API_PATH = "/api/v0/discountCards";

    private final DiscountCardService discountCardService;
    private final PaginationProperties paginationProperties;

    @PostMapping
    public ResponseEntity<ApiResponse<DiscountCardDto>> createDiscountCard(
            @RequestBody @Valid DiscountCardDto discountCardDto
    ) {
        DiscountCardDto discountCard = discountCardService.createDiscountCard(discountCardDto);

        return apiResponseEntity(
                "Discount Card with ID " + discountCard.getId() + " was created",
                DISCOUNT_CARD_API_PATH,
                HttpStatus.CREATED,
                ApiResponse.Color.SUCCESS,
                discountCard
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DiscountCardDto>>> findAllDiscountCards(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        page = Optional.ofNullable(page).orElse(paginationProperties.getDefaultPageValue());
        pageSize = Optional.ofNullable(pageSize).orElse(paginationProperties.getDefaultPageSize());

        Page<DiscountCardDto> discountCards = discountCardService.getAllDiscountCards(page, pageSize);

        return apiResponseEntity(
                "All Discount Cards",
                DISCOUNT_CARD_API_PATH,
                HttpStatus.OK,
                ApiResponse.Color.SUCCESS,
                discountCards
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DiscountCardDto>> findDiscountCardById(@PathVariable @Valid @NotNull Long id) {
        DiscountCardDto discountCard = discountCardService.getDiscountCardById(id);

        return apiResponseEntity(
                "Discount Card with ID " + discountCard.getId() + " was found",
                DISCOUNT_CARD_API_PATH + "/" + id,
                HttpStatus.OK,
                ApiResponse.Color.SUCCESS,
                discountCard
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DiscountCardDto>> updateDiscountCardById(
            @PathVariable @Valid @NotNull Long id,
            @RequestBody @Valid DiscountCardDto discountCardDto
    ) {
        DiscountCardDto discountCard = discountCardService.updateDiscountCardById(id, discountCardDto);

        return apiResponseEntity(
                "Changes were applied to the Discount Card with ID " + id,
                DISCOUNT_CARD_API_PATH + "/" + id,
                HttpStatus.OK,
                ApiResponse.Color.SUCCESS,
                discountCard
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<DiscountCardDto>> updateDiscountCardByIdPartially(
            @PathVariable @Valid @NotNull Long id,
            @RequestBody DiscountCardDto discountCardDto
    ) {
        DiscountCardDto discountCard = discountCardService.updateDiscountCardByIdPartially(id, discountCardDto);

        return apiResponseEntity(
                "Partial changes were applied to the Discount Card with ID " + id,
                DISCOUNT_CARD_API_PATH + "/" + id,
                HttpStatus.OK,
                ApiResponse.Color.SUCCESS,
                discountCard
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDiscountCardById(@PathVariable @Valid @NotNull Long id) {
        discountCardService.deleteDiscountCardById(id);

        return apiResponseEntity(
                "Discount Card with ID " + id + " was deleted",
                DISCOUNT_CARD_API_PATH + "/" + id,
                HttpStatus.NO_CONTENT,
                ApiResponse.Color.SUCCESS,
                null
        );
    }
}
