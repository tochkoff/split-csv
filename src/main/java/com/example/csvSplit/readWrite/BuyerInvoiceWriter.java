package com.example.csvSplit.readWrite;

import com.example.csvSplit.data.BuyerInvoice;

import java.io.File;

public interface BuyerInvoiceWriter {

    void write(File file, String[] headers, BuyerInvoice buyerInvoice);
}
