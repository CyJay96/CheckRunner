package com.clevertec.checkrunner.controller;

import com.clevertec.checkrunner.domain.DiscountCard;
import com.clevertec.checkrunner.dto.DiscountCardDto;
import com.clevertec.checkrunner.repository.DiscountCardRepository;
import com.clevertec.checkrunner.service.mapper.DiscountCardMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.clevertec.checkrunner.controller.DiscountCardController.DISCOUNT_CARD_API_PATH;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DiscountCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DiscountCardMapper discountCardMapper;

    @Autowired
    private DiscountCardRepository discountCardRepository;

    @MockBean
    private DiscountCardController discountCardController;

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

    @Test
    void createDiscountCard() throws Exception {
        Mockito.when(discountCardController.createDiscountCard(Mockito.any()))
                .thenReturn(new ResponseEntity<>(mockDiscountCardDto, HttpStatus.CREATED));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(DISCOUNT_CARD_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockDiscountCardDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.number", is(1234)));
    }

    @Test
    void findAllDiscountCards() throws Exception {
        List<DiscountCardDto> discountCards = new ArrayList<>(List.of(mockDiscountCardDto));

        Mockito.when(discountCardController.findAllDiscountCards())
                .thenReturn(new ResponseEntity<>(discountCards, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(DISCOUNT_CARD_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // GET Discount Card success
    @Test
    void findDiscountCardById_discountCardExists() throws Exception {
        Long existedDiscountCardId = discountCard.getId();

        Mockito.when(discountCardController.findDiscountCardById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(mockDiscountCardDto, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(DISCOUNT_CARD_API_PATH + "/" + existedDiscountCardId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.number", is(1234)));
    }

    // GET Discount Card failure
    @Test
    void findDiscountCardById_discountCardNotExists() throws Exception {
        long notExistedDiscountCardId = 5426;

        Mockito.when(discountCardController.findDiscountCardById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(DISCOUNT_CARD_API_PATH + "/" + notExistedDiscountCardId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // PUT Discount Card success
    @Test
    void putDiscountCardById_discountCardExists() throws Exception {
        Long existedDiscountCardId = discountCard.getId();

        Mockito.when(discountCardController.putDiscountCardById(Mockito.anyLong(), Mockito.any()))
                .thenReturn(new ResponseEntity<>(mockDiscountCardDto, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(DISCOUNT_CARD_API_PATH + "/" + existedDiscountCardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockDiscountCardDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.number", is(1234)));
    }

    // PUT Discount Card failure
    @Test
    void putDiscountCardById_discountCardNotExists() throws Exception {
        long notExistedDiscountCardId = 5426;

        Mockito.when(discountCardController.putDiscountCardById(Mockito.anyLong(), Mockito.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(DISCOUNT_CARD_API_PATH + "/" + notExistedDiscountCardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockDiscountCardDto)))
                .andExpect(status().isNotFound());
    }

    // DELETE Discount Card success
    @Test
    void deleteDiscountCardById_discountCardExists() throws Exception {
        Long existedDiscountCardId = discountCard.getId();

        Mockito.when(discountCardController.deleteDiscountCardById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(DISCOUNT_CARD_API_PATH + "/" + existedDiscountCardId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // DELETE Discount Card failure
    @Test
    void deleteDiscountCardById_discountCardNotExists() throws Exception {
        long notExistedDiscountCardId = 5426;

        Mockito.when(discountCardController.deleteDiscountCardById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(DISCOUNT_CARD_API_PATH + "/" + notExistedDiscountCardId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
