package com.example.csvSplit.readWrite;

import com.example.csvSplit.data.BuyerInvoice;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CsvLineToBuyerInvoiceConverterImpl implements CsvLineToBuyerInvoiceConverter {

    @Override
    public Optional<BuyerInvoice> convert(String line) {
        BuyerInvoice buyerInvoice = null;
        if (line != null && line.length() != 0) {
            String[] fields = line.split(",");
            ensureLineHasAllFields(fields);
            buyerInvoice = new BuyerInvoice();
            buyerInvoice.setBuyer(fields[0]);
            buyerInvoice.setImageName(fields[1]);
            buyerInvoice.setImage(fields[2]);
            buyerInvoice.setDueDate(LocalDate.parse(fields[3]));
            buyerInvoice.setNumber(fields[4]);
            buyerInvoice.setAmount(new BigDecimal(fields[5]));
            buyerInvoice.setCurrency(fields[6]);
            buyerInvoice.setStatus(fields[7]);
            buyerInvoice.setSupplier(fields[8]);
        }
        return Optional.ofNullable(buyerInvoice);
    }

    private void ensureLineHasAllFields(String[] fields) {
        if (fields.length != 9) {
            throw new IllegalArgumentException("Field count is incorrect");
        }
    }

}
