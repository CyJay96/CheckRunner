package com.clevertec.checkrunner.controller;

import com.clevertec.checkrunner.domain.Product;
import com.clevertec.checkrunner.domain.Receipt;
import com.clevertec.checkrunner.domain.ReceiptProduct;
import com.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import com.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import com.clevertec.checkrunner.repository.ReceiptRepository;
import com.clevertec.checkrunner.service.mapper.ReceiptMapper;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.clevertec.checkrunner.controller.ReceiptController.RECEIPT_API_PATH;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private ReceiptRepository receiptRepository;

    @MockBean
    private ReceiptController receiptController;

    private Receipt receipt;
    private ReceiptDtoRequest mockReceiptDtoRequest;
    private ReceiptDtoResponse mockReceiptDtoResponse;

    @BeforeEach
    void init() {
        ReceiptProduct mockReceiptProduct = ReceiptProduct.builder()
                .id(1L)
                .quantity(1L)
                .product(Product.builder()
                        .id(1L)
                        .description("test product")
                        .price(BigDecimal.ONE)
                        .isPromotional(true)
                        .build())
                .total(BigDecimal.ONE)
                .build();
        Receipt mockReceipt = Receipt.builder()
                .id(1L)
                .title("test receipt")
                .shopTitle("test shop title")
                .shopAddress("test shop address")
                .phoneNumber("test phone number")
                .cashierNumber(1234L)
                .creationDate(new Date())
                .receiptProducts(List.of(mockReceiptProduct))
                .discountCardPrice(BigDecimal.ZERO)
                .promotionalPercent(BigDecimal.ZERO)
                .promotionalPrice(BigDecimal.ZERO)
                .total(BigDecimal.ONE)
                .build();
        mockReceiptProduct.setReceipt(mockReceipt);

        mockReceiptDtoRequest = ReceiptDtoRequest.builder()
                .title("test title request")
                .shopTitle("test shop title request")
                .shopAddress("test shop address")
                .phoneNumber("test phone number request")
                .cashierNumber(1234L)
                .products(new HashMap<>())
                .promotionalPercent(BigDecimal.ONE)
                .build();
        mockReceiptDtoRequest.getProducts().put(1L, 1L);

        receipt = receiptRepository.save(mockReceipt);
        mockReceiptDtoResponse = receiptMapper.domainToDtoResponse(receipt);
    }

    // CREATE Receipt
    @Test
    void createReceipt() throws Exception {
        Mockito.when(receiptController.createReceipt(Mockito.any()))
                .thenReturn(new ResponseEntity<>(mockReceiptDtoResponse, HttpStatus.CREATED));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(RECEIPT_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockReceiptDtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("test receipt")));
    }

    // GET Receipts
    @Test
    void findAllReceipts() throws Exception {
        List<ReceiptDtoResponse> receipts = new ArrayList<>(List.of(mockReceiptDtoResponse));

        Mockito.when(receiptController.findAllReceipts())
                .thenReturn(new ResponseEntity<>(receipts, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(RECEIPT_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // GET Receipt success
    @Test
    void findReceiptById_receiptExists() throws Exception {
        Long existedReceiptId = receipt.getId();

        Mockito.when(receiptController.findReceiptById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(mockReceiptDtoResponse, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(RECEIPT_API_PATH + "/" + existedReceiptId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("test receipt")));
    }

    // GET Receipt failure
    @Test
    void findReceiptById_receiptNotExists() throws Exception {
        long notExistedReceiptId = 5426;

        Mockito.when(receiptController.findReceiptById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(RECEIPT_API_PATH + "/" + notExistedReceiptId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // PUT Receipt success
    @Test
    void putReceiptById_receiptExists() throws Exception {
        Long existedReceiptId = receipt.getId();

        Mockito.when(receiptController.putReceiptById(Mockito.anyLong(), Mockito.any()))
                .thenReturn(new ResponseEntity<>(mockReceiptDtoResponse, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(RECEIPT_API_PATH + "/" + existedReceiptId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockReceiptDtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("test receipt")));
    }

    // PUT Receipt failure
    @Test
    void putReceiptById_receiptNotExists() throws Exception {
        long notExistedReceiptId = 5426;

        Mockito.when(receiptController.putReceiptById(Mockito.anyLong(), Mockito.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(RECEIPT_API_PATH + "/" + notExistedReceiptId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockReceiptDtoRequest)))
                .andExpect(status().isNotFound());
    }

    // DELETE Receipt success
    @Test
    void deleteReceiptById_receiptExists() throws Exception {
        Long existedReceiptId = receipt.getId();

        Mockito.when(receiptController.deleteReceiptById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(RECEIPT_API_PATH + "/" + existedReceiptId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // DELETE Receipt failure
    @Test
    void deleteReceiptById_receiptNotExists() throws Exception {
        long notExistedReceiptId = 5426;

        Mockito.when(receiptController.deleteReceiptById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(RECEIPT_API_PATH + "/" + notExistedReceiptId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
