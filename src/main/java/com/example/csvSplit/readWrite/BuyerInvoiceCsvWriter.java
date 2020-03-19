package com.example.csvSplit.readWrite;

import com.example.csvSplit.data.BuyerInvoice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Qualifier(value = "CSV")
public class BuyerInvoiceCsvWriter implements BuyerInvoiceWriter {

    private static final String CSV_SEPARATOR = ",";

    @Override
    public void write(File file, String[] headers, BuyerInvoice buyerInvoice) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            StringBuilder invoice = constructInvoice(headers, buyerInvoice);
            bw.write(invoice.toString());
            bw.newLine();

            bw.flush();
        } catch (IOException e) {
            System.out.println("Can not write file");
        }
    }

    private StringBuilder constructInvoice(String[] headers, BuyerInvoice buyerInvoice) {
        String csvHeader = String.join(",", headers);
        StringBuilder buyerInvoiceData = new StringBuilder(csvHeader);
        appendBuyerInvoiceData(buyerInvoice, buyerInvoiceData);
        return buyerInvoiceData;
    }

    private void appendBuyerInvoiceData(BuyerInvoice buyerInvoice, StringBuilder buyerInvoiceData) {
        buyerInvoiceData.append("\n");
        buyerInvoiceData.append(buyerInvoice.getBuyer() != null ? buyerInvoice.getBuyer() : "");
        buyerInvoiceData.append(CSV_SEPARATOR);
        buyerInvoiceData.append(buyerInvoice.getImageName() != null ? buyerInvoice.getImageName() : "");
        buyerInvoiceData.append(CSV_SEPARATOR);
        buyerInvoiceData.append(buyerInvoice.getImage() != null ? buyerInvoice.getImage() : "");
        buyerInvoiceData.append(CSV_SEPARATOR);
        buyerInvoiceData.append(buyerInvoice.getDueDate() != null ? buyerInvoice.getDueDate() : "");
        buyerInvoiceData.append(CSV_SEPARATOR);
        buyerInvoiceData.append(buyerInvoice.getNumber() != null ? buyerInvoice.getNumber() : "");
        buyerInvoiceData.append(CSV_SEPARATOR);
        buyerInvoiceData.append(buyerInvoice.getAmount() != null ? buyerInvoice.getAmount() : "");
        buyerInvoiceData.append(CSV_SEPARATOR);
        buyerInvoiceData.append(buyerInvoice.getCurrency() != null ? buyerInvoice.getCurrency() : "");
        buyerInvoiceData.append(CSV_SEPARATOR);
        buyerInvoiceData.append(buyerInvoice.getStatus() != null ? buyerInvoice.getStatus() : "");
        buyerInvoiceData.append(CSV_SEPARATOR);
        buyerInvoiceData.append(buyerInvoice.getSupplier() != null ? buyerInvoice.getSupplier() : "");
    }
}
