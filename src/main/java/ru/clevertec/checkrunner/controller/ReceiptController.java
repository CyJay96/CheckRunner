package ru.clevertec.checkrunner.controller;

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
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import ru.clevertec.checkrunner.service.ReceiptFileService;
import ru.clevertec.checkrunner.service.ReceiptService;

import java.util.List;

import static ru.clevertec.checkrunner.controller.ReceiptController.RECEIPT_API_PATH;

@RestController
@Validated
@RequestMapping(value = RECEIPT_API_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReceiptController {

    public static final String RECEIPT_API_PATH = "/api/v0/receipt";

    private final ReceiptService receiptService;
    private final ReceiptFileService receiptFileService;

    @PostMapping
    public ResponseEntity<ReceiptDtoResponse> createReceipt(@RequestBody @Valid ReceiptDtoRequest receiptDtoRequest) {
        ReceiptDtoResponse check = receiptService.createReceipt(receiptDtoRequest);
        return new ResponseEntity<>(check, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReceiptDtoResponse>> findAllReceipts() {
        List<ReceiptDtoResponse> receipts = receiptService.getAllReceipts();
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptDtoResponse> findReceiptById(@PathVariable @Valid @NotNull Long id) {
        ReceiptDtoResponse receipt = receiptService.getReceiptById(id);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceiptDtoResponse> putReceiptById(
            @PathVariable @Valid @NotNull Long id,
            @RequestBody @Valid ReceiptDtoRequest receiptDtoRequest
    ) {
        ReceiptDtoResponse receipt = receiptService.updateReceiptById(id, receiptDtoRequest);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceiptById(@PathVariable @Valid @NotNull Long id) {
        receiptService.deleteReceiptById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/createfile/{id}")
    public ResponseEntity<ReceiptDtoResponse> createReceiptFile(@PathVariable Long id) {
        ReceiptDtoResponse receipt = receiptFileService.writeReceiptById(id);
        return new ResponseEntity<>(receipt, HttpStatus.NO_CONTENT);
    }
}
