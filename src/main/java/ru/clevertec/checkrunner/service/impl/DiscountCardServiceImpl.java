package ru.clevertec.checkrunner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.dto.DiscountCardDto;
import ru.clevertec.checkrunner.exception.ConversionException;
import ru.clevertec.checkrunner.exception.DiscountCardNotFoundException;
import ru.clevertec.checkrunner.repository.DiscountCardRepository;
import ru.clevertec.checkrunner.service.DiscountCardService;

import java.util.Optional;

import static ru.clevertec.checkrunner.util.Constants.DomainClasses.DISCOUNT_CARD_DOMAIN;

@Service
@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepository discountCardRepository;
    private final ConversionService conversionService;

    @Override
    public DiscountCardDto createDiscountCard(DiscountCardDto discountCardDto) {
        DiscountCard discountCard = Optional.ofNullable(conversionService.convert(discountCardDto, DiscountCard.class))
                .orElseThrow(() -> new ConversionException(DISCOUNT_CARD_DOMAIN));
        DiscountCard savedDiscountCard = discountCardRepository.save(discountCard);
        return conversionService.convert(savedDiscountCard, DiscountCardDto.class);
    }

    @Override
    public Page<DiscountCardDto> getAllDiscountCards(Integer page, Integer pageSize) {
        Page<DiscountCard> discountCardPage = discountCardRepository.findAll(PageRequest.of(page, pageSize));
        return discountCardPage.map(discountCard -> conversionService.convert(discountCard, DiscountCardDto.class));
    }

    @Override
    public DiscountCardDto getDiscountCardById(Long id) {
        return discountCardRepository.findById(id)
                .map(discountCard -> conversionService.convert(discountCard, DiscountCardDto.class))
                .orElseThrow(() -> new DiscountCardNotFoundException(id));
    }

    @Override
    public DiscountCardDto updateDiscountCardById(Long id, DiscountCardDto discountCardDto) {
        DiscountCard discountCard = Optional.ofNullable(conversionService.convert(discountCardDto, DiscountCard.class))
                .orElseThrow(() -> new ConversionException(DISCOUNT_CARD_DOMAIN));
        discountCard.setId(id);

        DiscountCard savedDiscountCard = discountCardRepository.save(discountCard);
        return conversionService.convert(savedDiscountCard, DiscountCardDto.class);
    }

    @Override
    public DiscountCardDto updateDiscountCardByIdPartially(Long id, DiscountCardDto discountCardDto) {
        DiscountCard updatedDiscountCard = discountCardRepository.findById(id)
                .map(discountCard -> {
                    Optional.ofNullable(discountCardDto.getNumber()).ifPresent(discountCard::setNumber);
                    return discountCard;
                })
                .orElseThrow(() ->
                        new DiscountCardNotFoundException(id));

        DiscountCard savedDiscountCard = discountCardRepository.save(updatedDiscountCard);
        return conversionService.convert(savedDiscountCard, DiscountCardDto.class);
    }

    @Override
    public void deleteDiscountCardById(Long id) {
        try {
            discountCardRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DiscountCardNotFoundException(id);
        }
    }
}
