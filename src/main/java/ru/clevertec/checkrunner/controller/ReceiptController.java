package ru.clevertec.checkrunner.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.checkrunner.config.PaginationProperties;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.dto.response.ApiResponse;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import ru.clevertec.checkrunner.service.ReceiptFileService;
import ru.clevertec.checkrunner.service.ReceiptService;

import java.util.Optional;

import static ru.clevertec.checkrunner.controller.ReceiptController.RECEIPT_API_PATH;
import static ru.clevertec.checkrunner.dto.response.ApiResponse.apiResponseEntity;

@RestController
@Validated
@RequestMapping(value = RECEIPT_API_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReceiptController {

    public static final String RECEIPT_API_PATH = "/api/v0/receipts";

    private final ReceiptService receiptService;
    private final ReceiptFileService receiptFileService;
    private final PaginationProperties paginationProperties;

    @PostMapping
    public ResponseEntity<ApiResponse<ReceiptDtoResponse>> createReceipt(@RequestBody @Valid ReceiptDtoRequest receiptDtoRequest) {
        ReceiptDtoResponse receipt = receiptService.createReceipt(receiptDtoRequest);

        return apiResponseEntity(
                "Receipt with ID " + receipt.getId() + " was created",
                RECEIPT_API_PATH,
                HttpStatus.CREATED,
                ApiResponse.Color.SUCCESS,
                receipt
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ReceiptDtoResponse>>> findAllReceipts(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        page = Optional.ofNullable(page).orElse(paginationProperties.getDefaultPageValue());
        pageSize = Optional.ofNullable(pageSize).orElse(paginationProperties.getDefaultPageSize());

        Page<ReceiptDtoResponse> receipts = receiptService.getAllReceipts(page, pageSize);

        return apiResponseEntity(
                "All Receipts",
                RECEIPT_API_PATH,
                HttpStatus.OK,
                ApiResponse.Color.SUCCESS,
                receipts
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReceiptDtoResponse>> findReceiptById(@PathVariable @Valid @NotNull Long id) {
        ReceiptDtoResponse receipt = receiptService.getReceiptById(id);

        return apiResponseEntity(
                "Receipt with ID " + receipt.getId() + " was found",
                RECEIPT_API_PATH + "/" + id,
                HttpStatus.OK,
                ApiResponse.Color.SUCCESS,
                receipt
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReceiptDtoResponse>> updateReceiptById(
            @PathVariable @Valid @NotNull Long id,
            @RequestBody @Valid ReceiptDtoRequest receiptDtoRequest
    ) {
        ReceiptDtoResponse receipt = receiptService.updateReceiptById(id, receiptDtoRequest);

        return apiResponseEntity(
                "Changes were applied to the Receipt with ID " + id,
                RECEIPT_API_PATH + "/" + id,
                HttpStatus.OK,
                ApiResponse.Color.SUCCESS,
                receipt
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ReceiptDtoResponse>> updateReceiptByIdPartially(
            @PathVariable @Valid @NotNull Long id,
            @RequestBody ReceiptDtoRequest receiptDtoRequest
    ) {
        ReceiptDtoResponse receipt = receiptService.updateReceiptByIdPartially(id, receiptDtoRequest);

        return apiResponseEntity(
                "Partial changes were applied to the Receipt with ID " + id,
                RECEIPT_API_PATH + "/" + id,
                HttpStatus.OK,
                ApiResponse.Color.SUCCESS,
                receipt
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReceiptById(@PathVariable @Valid @NotNull Long id) {
        receiptService.deleteReceiptById(id);

        return apiResponseEntity(
                "Receipt with ID " + id + " was deleted",
                RECEIPT_API_PATH + "/" + id,
                HttpStatus.NO_CONTENT,
                ApiResponse.Color.SUCCESS,
                null
        );
    }

    @GetMapping("/createFile/{id}")
    public ResponseEntity<ApiResponse<ReceiptDtoResponse>> createReceiptFile(@PathVariable Long id) {
        ReceiptDtoResponse receipt = receiptFileService.writeReceiptById(id);

        return apiResponseEntity(
                "Receipt with ID " + id + " was deleted",
                RECEIPT_API_PATH + "/" + id,
                HttpStatus.NO_CONTENT,
                ApiResponse.Color.SUCCESS,
                receipt
        );
    }
}
