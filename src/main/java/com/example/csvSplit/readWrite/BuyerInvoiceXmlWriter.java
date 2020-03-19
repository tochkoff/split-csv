package com.example.csvSplit.readWrite;

import com.example.csvSplit.data.BuyerInvoice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@Service
@Qualifier(value = "XML")
public class BuyerInvoiceXmlWriter implements BuyerInvoiceWriter {

    @Override
    public void write(File file, String[] headers, BuyerInvoice buyerInvoice) {
        try {
            writeXml(file, headers, buyerInvoice);
            writeImage(buyerInvoice);
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            throw new IllegalArgumentException("Can not write file");
        }
    }

    private void writeXml(File file, String[] headers, BuyerInvoice buyerInvoice) throws ParserConfigurationException, IOException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc;
        Element rootElement;

        try {
            doc = dBuilder.parse(file);
            rootElement = doc.getDocumentElement();
        } catch (FileNotFoundException e) {
            doc = dBuilder.newDocument();
            rootElement = doc.createElementNS("https://www.taulia.com/invoice", "Invoices");
            doc.appendChild(rootElement);
        } catch (SAXException e) {
            throw new IllegalArgumentException("Can not read file");
        }

        rootElement.appendChild(populateInvoiceData(headers, doc, buyerInvoice));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(file);

        transformer.transform(source, streamResult);
    }

    private void writeImage(BuyerInvoice buyerInvoice) {
        String image = buyerInvoice.getImage();
        if (image != null && image.length() != 0) {
            extractImage(buyerInvoice);
        }
    }

    private void extractImage(BuyerInvoice buyerInvoice) {
        String fileToWrite = "invoiceImages\\" + buyerInvoice.getImageName();
        byte[] decoded = Base64.getDecoder().decode(buyerInvoice.getImage());
        try (ByteArrayInputStream bis = new ByteArrayInputStream(decoded)) {
            BufferedImage image = ImageIO.read(bis);
            if (image != null) {
                ImageIO.write(image, "png", new File(fileToWrite));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not write image.");
        }
    }

    private Node populateInvoiceData(String[] headers, Document doc, BuyerInvoice buyerInvoice) {
        Element invoice = doc.createElement("Invoice");

        if (buyerInvoice.getBuyer() != null) {
            invoice.appendChild(createNode(doc, headers[0], buyerInvoice.getBuyer()));
        }
        if (buyerInvoice.getImageName() != null) {
            invoice.appendChild(createNode(doc, headers[1], buyerInvoice.getImageName()));
        }
        if (buyerInvoice.getDueDate() != null) {
            invoice.appendChild(createNode(doc, headers[3], buyerInvoice.getBuyer()));
        }
        if (buyerInvoice.getNumber() != null) {
            invoice.appendChild(createNode(doc, headers[4], buyerInvoice.getNumber()));
        }
        if (buyerInvoice.getAmount() != null) {
            invoice.appendChild(createNode(doc, headers[5], buyerInvoice.getAmount().toString()));
        }
        if (buyerInvoice.getCurrency() != null) {
            invoice.appendChild(createNode(doc, headers[6], buyerInvoice.getCurrency()));
        }
        if (buyerInvoice.getStatus() != null) {
            invoice.appendChild(createNode(doc, headers[7], buyerInvoice.getStatus()));
        }
        if (buyerInvoice.getSupplier() != null) {
            invoice.appendChild(createNode(doc, headers[8], buyerInvoice.getSupplier()));
        }

        return invoice;
    }

    private Node createNode(Document doc, String header, String value) {
        Element node = doc.createElement(header);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
