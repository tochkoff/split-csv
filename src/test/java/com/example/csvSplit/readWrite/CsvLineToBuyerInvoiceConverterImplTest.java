package com.example.csvSplit.readWrite;

import com.example.csvSplit.data.BuyerInvoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvLineToBuyerInvoiceConverterImplTest {

    private CsvLineToBuyerInvoiceConverterImpl sut;

    @BeforeEach
    public void init() {
        sut = new CsvLineToBuyerInvoiceConverterImpl();
    }

    @Test
    public void shouldCreateBuyerInvoiceWhenStringLineHasAllParameters() {
        String input = "1,2,3,2020-07-01,5,6,7,8,9";
        Optional<BuyerInvoice> convert = sut.convert(input);
        assertTrue(convert.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalForEmptyInput() {
        String input = "";
        Optional<BuyerInvoice> convert = sut.convert(input);
        assertFalse(convert.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalForNullInput() {
        String input = null;
        Optional<BuyerInvoice> convert = sut.convert(input);
        assertFalse(convert.isPresent());
    }

    @Test
    public void shouldThrowExceptionIfInputFieldsIsNotCorrect() {
        String input = "1, 2";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sut.convert(input);
        });
    }
}