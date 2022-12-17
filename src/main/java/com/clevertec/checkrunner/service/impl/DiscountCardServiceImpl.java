package com.clevertec.checkrunner.service.impl;

import com.clevertec.checkrunner.domain.DiscountCard;
import com.clevertec.checkrunner.dto.DiscountCardDto;
import com.clevertec.checkrunner.exception.DiscountCardNotFoundException;
import com.clevertec.checkrunner.repository.DiscountCardRepository;
import com.clevertec.checkrunner.service.DiscountCardService;
import com.clevertec.checkrunner.service.mapper.DiscountCardMapper;
import com.clevertec.checkrunner.service.mapper.list.DiscountCardListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepository discountCardRepository;
    private final DiscountCardMapper discountCardMapper;
    private final DiscountCardListMapper discountCardListMapper;

    @Override
    public DiscountCardDto createDiscountCard(DiscountCardDto discountCardDto) {
        DiscountCard discountCard = discountCardRepository.save(discountCardMapper.dtoToDomain(discountCardDto));
        return discountCardMapper.domainToDto(discountCard);
    }

    @Override
    public List<DiscountCardDto> getAllDiscountCards() {
        return discountCardListMapper.domainToDto(discountCardRepository.findAll());
    }

    @Override
    public DiscountCardDto getDiscountCardById(Long id) {
        return discountCardRepository.findById(id)
                .map(discountCardMapper::domainToDto)
                .orElseThrow(() -> new DiscountCardNotFoundException("Discount card with ID = " + id + " not found"));
    }

    @Override
    public DiscountCardDto updateDiscountCardById(Long id, DiscountCardDto discountCardDto) {
        return discountCardMapper.domainToDto(
                discountCardRepository.findById(id)
                        .map(discountCard -> {
                            discountCard.setNumber(discountCardDto.getNumber());
                            return discountCardRepository.save(discountCard);
                        })
                        .orElseThrow(() ->
                                new DiscountCardNotFoundException("Discount card with ID = " + id + " not found"))
        );
    }

    @Override
    public void deleteDiscountCardById(Long id) {
        try {
            discountCardRepository.deleteById(id);
        } catch (Exception e) {
            throw new DiscountCardNotFoundException("Discount card with ID = " + id + " not found");
        }
    }

}
