package com.example.csvSplit.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

public interface SplitCsv {

    @PutMapping(value = "/csv/{file_name}", produces = "text/plain")
    String splitToCsv(String fileName);

    @PutMapping(value = "/xml/{file_name}", produces = "text/plain")
    String splitToXml(String fileName);
}
