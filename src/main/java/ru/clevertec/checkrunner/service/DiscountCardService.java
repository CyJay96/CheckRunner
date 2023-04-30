package ru.clevertec.checkrunner.service;

import org.springframework.data.domain.Page;
import ru.clevertec.checkrunner.dto.DiscountCardDto;

public interface DiscountCardService {

    DiscountCardDto createDiscountCard(DiscountCardDto discountCardDto);

    Page<DiscountCardDto> getAllDiscountCards(Integer page, Integer pageSize);

    DiscountCardDto getDiscountCardById(Long id);

    DiscountCardDto updateDiscountCardById(Long id, DiscountCardDto discountCardDto);

    DiscountCardDto updateDiscountCardByIdPartially(Long id, DiscountCardDto discountCardDto);

    void deleteDiscountCardById(Long id);
}
