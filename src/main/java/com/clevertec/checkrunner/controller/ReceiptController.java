package com.clevertec.checkrunner.controller;

import com.clevertec.checkrunner.dto.ReceiptDto;
import com.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import com.clevertec.checkrunner.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v0/receipt")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<ReceiptDto> createReceipt(@RequestBody ReceiptDtoRequest receiptDtoRequest) {
        ReceiptDto check = receiptService.createReceipt(receiptDtoRequest);
        return new ResponseEntity<>(check, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReceiptDto>> findAllReceipts() {
        List<ReceiptDto> receipts = receiptService.getAllReceipts();
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptDto> findReceiptById(@PathVariable Long id) {
        ReceiptDto receipt = receiptService.getReceiptById(id);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceiptDto> putReceiptById(
            @PathVariable Long id,
            @RequestBody ReceiptDtoRequest receiptDtoRequest
    ) {
        ReceiptDto receipt = receiptService.updateReceiptById(id, receiptDtoRequest);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceiptById(@PathVariable Long id) {
        receiptService.deleteReceiptById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
