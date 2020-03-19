package com.example.csvSplit.readWrite;

import com.example.csvSplit.data.BuyerInvoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BuyerInvoiceXmlWriterTest {

    private BuyerInvoiceXmlWriter sut;

    @BeforeEach
    public void init() {
        sut = new BuyerInvoiceXmlWriter();
    }

    @Test
    void shouldCreateXmlFile() throws IOException {
        BuyerInvoice invoiceToBeWritten = mock(BuyerInvoice.class);
        when(invoiceToBeWritten.getBuyer()).thenReturn("buyer");
        when(invoiceToBeWritten.getNumber()).thenReturn("number");
        when(invoiceToBeWritten.getImage()).thenReturn(Base64.getEncoder().encodeToString("encode".getBytes()));
        when(invoiceToBeWritten.getImageName()).thenReturn("testImageFile.txt");

        File fileToWrite = new File("xmlFiles\\" + invoiceToBeWritten.getBuyer() + "_" + invoiceToBeWritten.getNumber() + ".xml");
        String[] headers = {"h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9"};

        sut.write(fileToWrite, headers, invoiceToBeWritten);
        assertTrue(fileToWrite.exists());

        fileToWrite.delete();
    }

    @Test
    void shouldThrowExceptionIfFileDoesNotExist() throws IOException {
        BuyerInvoice invoiceToBeWritten = mock(BuyerInvoice.class);
        when(invoiceToBeWritten.getBuyer()).thenReturn("buyer");
        when(invoiceToBeWritten.getNumber()).thenReturn("number");
        File fileToWrite = new File("test\\xmlFiles.xml");
        String[] headers = {"h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9"};

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sut.write(fileToWrite, headers, invoiceToBeWritten);
        });

    }
}
