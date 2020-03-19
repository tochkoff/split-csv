package com.example.csvSplit.rest;

import com.example.csvSplit.readWrite.BuyerInvoiceWriter;
import com.example.csvSplit.readWrite.CsvLineToBuyerInvoiceConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class SplitCsvImplTest {

    private SplitCsvImpl sut;

    @Mock
    private CsvLineToBuyerInvoiceConverter csvLineToBuyerInvoiceConverter;

    @Mock
    BuyerInvoiceWriter csvWriter;

    @Mock
    BuyerInvoiceWriter xmlWriter;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        sut = new SplitCsvImpl(csvLineToBuyerInvoiceConverter, csvWriter, xmlWriter);
    }

    @Test
    public void shouldReturnMessageAboutDirectoryWhenCsvSplitIsInvoked() {
        String result = sut.splitToCsv("invoices.csv");
        assertEquals("Created files are in directory csvFiles.", result);
    }

    @Test
    public void shouldThrowErrorIfFileIsNotFound() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sut.splitToCsv("cs.txt");
        });
    }

}