package com.example.csvSplit.rest;

import com.example.csvSplit.data.BuyerInvoice;
import com.example.csvSplit.readWrite.BuyerInvoiceWriter;
import com.example.csvSplit.readWrite.CsvLineToBuyerInvoiceConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.util.Optional;

@RestController
@RequestMapping(path = "/split")
public class SplitCsvImpl implements SplitCsv{

    private final CsvLineToBuyerInvoiceConverter csvLineToBuyerInvoiceConverter;
    private final BuyerInvoiceWriter csvWriter;
    private final BuyerInvoiceWriter xmlWriter;

    @Autowired
    public SplitCsvImpl(CsvLineToBuyerInvoiceConverter csvLineToBuyerInvoiceConverter,
                        @Qualifier(value = "CSV") BuyerInvoiceWriter csvWriter,
                        @Qualifier(value = "XML") BuyerInvoiceWriter xmlWriter) {
        this.csvLineToBuyerInvoiceConverter = csvLineToBuyerInvoiceConverter;
        this.csvWriter = csvWriter;
        this.xmlWriter = xmlWriter;
    }

    @Override
    public String splitToCsv(@PathVariable("file_name") String fileName) {
        File file = new File(fileName);
        try (InputStream fis = new FileInputStream(file);
             Reader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            String[] headers = null;

            if ((line = br.readLine()) != null) {
                headers = line.split(",");
            }
            while ((line = br.readLine()) != null) {
                Optional<BuyerInvoice> buyerInvoice = csvLineToBuyerInvoiceConverter.convert(line);
                if (buyerInvoice.isPresent()) {
                    BuyerInvoice invoiceToWrite = buyerInvoice.get();
                    File fileToWrite = new File("csvFiles\\" + invoiceToWrite.getBuyer() + ".csv");
                    csvWriter.write(fileToWrite, headers, buyerInvoice.get());
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not read file");
        }
        return "Created files are in directory csvFiles.";
    }

    @Override
    public String splitToXml(@PathVariable("file_name") String fileName) {
        File file = new File(fileName);
        try (InputStream fis = new FileInputStream(file);
             Reader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            String[] headers = null;

            if ((line = br.readLine()) != null) {
                headers = line.split(",");
            }
            while ((line = br.readLine()) != null) {
                Optional<BuyerInvoice> buyerInvoice = csvLineToBuyerInvoiceConverter.convert(line);
                if (buyerInvoice.isPresent()) {
                    BuyerInvoice invoiceToBeWritten = buyerInvoice.get();
                    File fileToWrite = new File("xmlFiles\\" + invoiceToBeWritten.getBuyer() + ".xml");
                    xmlWriter.write(fileToWrite, headers, invoiceToBeWritten);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not read file");
        }
        return "Created files are in directory xmlFiles.";
    }
}
