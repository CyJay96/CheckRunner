package com.clevertec.checkrunner.service.impl;

import com.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import com.clevertec.checkrunner.service.ReceiptFileService;
import com.clevertec.checkrunner.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class ReceiptFileServiceImpl implements ReceiptFileService {

    private final ReceiptService receiptService;

    private static final String DECIMAL_FORMAT = "#0.00";
    private static final String FOLDER_NAME = "receipts";

    @Override
    public ReceiptDtoResponse writeReceiptById(Long receiptId) {
        ReceiptDtoResponse receipt = receiptService.getReceiptById(receiptId);

        File receiptFolder = new File(FOLDER_NAME);
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

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String formattedDate = dateFormat.format(receipt.getCreationDate());
            String formattedTime = timeFormat.format(receipt.getCreationDate());
            writer.write("DATE: " + formattedDate + "\n");
            writer.write("TIME: " + formattedTime + "\n\n");

            writer.write("QTY DESCRIPTION PRICE TOTAL" + "\n");
            receipt.getReceiptProductDtos().forEach(receiptProductDto -> {
                try {
                    writer.write(receiptProductDto.getQuantity() + " " +
                            "\"" + receiptProductDto.getProductDto().getDescription() + "\" " +
                            receiptProductDto.getProductDto().getPrice() + " " +
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
