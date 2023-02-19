package ru.clevertec.checkrunner.service;

import ru.clevertec.checkrunner.dto.DiscountCardDto;

import java.util.List;

public interface DiscountCardService {

    DiscountCardDto createDiscountCard(DiscountCardDto discountCardDto);

    List<DiscountCardDto> getAllDiscountCards();

    DiscountCardDto getDiscountCardById(Long id);

    DiscountCardDto updateDiscountCardById(Long id, DiscountCardDto discountCardDto);

    void deleteDiscountCardById(Long id);
}
