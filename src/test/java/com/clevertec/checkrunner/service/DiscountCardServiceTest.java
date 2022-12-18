package com.clevertec.checkrunner.service;

import com.clevertec.checkrunner.domain.DiscountCard;
import com.clevertec.checkrunner.dto.DiscountCardDto;
import com.clevertec.checkrunner.repository.DiscountCardRepository;
import com.clevertec.checkrunner.service.mapper.DiscountCardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
class DiscountCardServiceTest {

    @MockBean
    private DiscountCardService discountCardService;

    @Autowired
    private DiscountCardRepository discountCardRepository;

    @Autowired
    private DiscountCardMapper discountCardMapper;

    private DiscountCard discountCard;
    private DiscountCardDto mockDiscountCardDto;

    @BeforeEach
    void init() {
        DiscountCard mockDiscountCard = DiscountCard.builder()
                .id(1L)
                .number(1234L)
                .build();

        discountCard = discountCardRepository.save(mockDiscountCard);
        mockDiscountCardDto = discountCardMapper.domainToDto(discountCard);
    }

    // CREATE Discount Card
    @Test
    void createDiscountCard() {
        DiscountCardDto discountCard = discountCardService.createDiscountCard(mockDiscountCardDto);
        Mockito.verify(discountCardService, Mockito.atLeastOnce()).createDiscountCard(Mockito.any());
    }

    // GET Discount Cards
    @Test
    void getAllDiscountCards() {
        List<DiscountCardDto> discountCards = discountCardService.getAllDiscountCards();
        Mockito.verify(discountCardService, Mockito.atLeastOnce()).getAllDiscountCards();
    }

    // GET Discount Card
    @Test
    void getDiscountCardById() {
        Long discountCardExistedId = discountCard.getId();
        DiscountCardDto discountCard = discountCardService.getDiscountCardById(discountCardExistedId);
        Mockito.verify(discountCardService, Mockito.atLeastOnce()).getDiscountCardById(Mockito.anyLong());
    }

    // PUT Discount Card
    @Test
    void updateDiscountCardById() {
        Long discountCardExistedId = discountCard.getId();
        DiscountCardDto discountCardDto = discountCardService
                .updateDiscountCardById(discountCardExistedId, mockDiscountCardDto);
        Mockito.verify(discountCardService, Mockito.atLeastOnce())
                .updateDiscountCardById(Mockito.anyLong(), Mockito.any());
    }

    // DELETE Discount Card
    @Test
    void deleteDiscountCardById() {
        Long discountCardExistedId = discountCard.getId();
        discountCardService.deleteDiscountCardById(discountCardExistedId);
        Mockito.verify(discountCardService, Mockito.atLeastOnce()).deleteDiscountCardById(Mockito.anyLong());
    }

}
