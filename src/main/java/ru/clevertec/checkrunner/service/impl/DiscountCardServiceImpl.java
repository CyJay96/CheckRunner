package ru.clevertec.checkrunner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.dto.DiscountCardDto;
import ru.clevertec.checkrunner.exception.DiscountCardNotFoundException;
import ru.clevertec.checkrunner.mapper.DiscountCardMapper;
import ru.clevertec.checkrunner.mapper.list.DiscountCardListMapper;
import ru.clevertec.checkrunner.repository.DiscountCardRepository;
import ru.clevertec.checkrunner.service.DiscountCardService;

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
                .orElseThrow(() -> new DiscountCardNotFoundException(id));
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
                                new DiscountCardNotFoundException(id))
        );
    }

    @Override
    public void deleteDiscountCardById(Long id) {
        try {
            discountCardRepository.deleteById(id);
        } catch (Exception e) {
            throw new DiscountCardNotFoundException(id);
        }
    }
}
