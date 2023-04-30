package ru.clevertec.checkrunner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import ru.clevertec.checkrunner.service.ReceiptFileService;
import ru.clevertec.checkrunner.service.ReceiptService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import static ru.clevertec.checkrunner.util.Constants.DECIMAL_FORMAT;
import static ru.clevertec.checkrunner.util.Constants.RECEIPTS_FOLDER_NAME;

@Service
@RequiredArgsConstructor
public class ReceiptFileServiceImpl implements ReceiptFileService {

    private final ReceiptService receiptService;

    @Override
    public ReceiptDtoResponse writeReceiptById(Long receiptId) {
        ReceiptDtoResponse receipt = receiptService.getReceiptById(receiptId);

        File receiptFolder = new File(RECEIPTS_FOLDER_NAME);
        if (!receiptFolder.exists()) {
            receiptFolder.mkdir();
        }

        File receiptFile = new File(receiptFolder.getName() + File.separator + "receipt_" + receipt.getId() + ".txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(receiptFile, false))) {
            writer.write(receipt.getTitle() + "\n");
            writer.write(receipt.getShopTitle() + "\n");
            writer.write(receipt.getShopAddress() + "\n");
            writer.write("Tel: " + receipt.getPhoneNumber() + "\n");
            writer.write("CASHIER: #" + receipt.getCashierNumber() + "\n");

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            writer.write("DATE: " + dateFormatter.format(receipt.getCreationDate()) + "\n");
            writer.write("TIME: " + timeFormatter.format(receipt.getCreationDate()) + "\n\n");

            writer.write("QTY DESCRIPTION PRICE TOTAL" + "\n");
            receipt.getReceiptProducts().forEach(receiptProductDto -> {
                try {
                    writer.write(receiptProductDto.getQuantity() + " " +
                            "\"" + receiptProductDto.getProduct().getDescription() + "\" " +
                            receiptProductDto.getProduct().getPrice() + " " +
                            receiptProductDto.getTotal() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.write("\n");

            writer.write("IS DISCOUNT CARD PRESENTED: " + receipt.isDiscountCardPresented() + "\n");
            writer.write("DISCOUNT BY CARD: " + new DecimalFormat(DECIMAL_FORMAT)
                    .format(receipt.getDiscountCardPrice()) + "\n");
            writer.write("PERCENT DISCOUNT ON PROMOTIONAL PRODUCTS: " +
                    receipt.getPromotionalPercent() + "\n");
            writer.write("DISCOUNT ON PROMOTIONAL PRODUCTS: " + new DecimalFormat(DECIMAL_FORMAT)
                    .format( receipt.getPromotionalPrice()) + "\n");
            writer.write("TOTAL PRICE: " + new DecimalFormat(DECIMAL_FORMAT)
                    .format(receipt.getTotal()) + "\n");

            writer.flush();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }

        return receipt;
    }
}
