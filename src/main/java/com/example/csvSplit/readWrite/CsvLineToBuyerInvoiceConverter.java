package com.example.csvSplit.readWrite;

import com.example.csvSplit.data.BuyerInvoice;

import java.util.Optional;

public interface CsvLineToBuyerInvoiceConverter {

    Optional<BuyerInvoice> convert(String line);
}
