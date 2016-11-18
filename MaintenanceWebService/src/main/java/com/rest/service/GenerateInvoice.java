//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;




public class GenerateInvoice extends BaseServiceImpl {

    private BaseFont bfBold;

    private BaseFont bf;

    private int pageNumber = 0;

    public static void main(String[] args) {

        String pdfFilename = "D:/invoice.pdf";
        GenerateInvoice generateInvoice = new GenerateInvoice();
        generateInvoice.createPDF(pdfFilename);

    }

    private void createPDF(String pdfFilename) {

        Document doc = new Document();
        PdfWriter docWriter = null;
        initializeFonts();

        try {
            String path = pdfFilename;
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.addAuthor("test");
            doc.addCreationDate();
            doc.addCreator("Upnal");
            doc.addTitle("Invoice");
            doc.setPageSize(PageSize.A4);

            doc.open();
            PdfContentByte cb = docWriter.getDirectContent();


            generateLayout(doc, cb);
            generateHeader(doc, cb);
            Map<String, Map<String, String>> map = new LinkedHashMap<String, Map<String, String>>();
            Map<String, String> map1 = new LinkedHashMap<String, String>();
            map1.put("Service Charges", "123.00");
            map1.put("After office Hour Charges", "103.00");
            map1.put("Holiday Charges", "00");
            map1.put("To and Fro Charges", "103.00");

            map1.put("Total Charge", "103.00");
            map1.put("Tax Type", "Service ax");
            map1.put("Tax %", "10.2%");
            map1.put("Tax Amount", "103.00");

            Map<String, String> map2 = new LinkedHashMap<String, String>();
            map2.put("Spare Amount", "123.00");
            map2.put("Spare Tax Type", "VAT");
            map2.put("Spare Tax %", "15%");
            map2.put("Spare Tax Amount", "103.00");




            map.put("Service", map1);
            map.put("Spare charges", map2);




            generateDetail(doc, cb, 30, decrement(635), map,1000.00);

            printPageNumber(cb);

        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
            }
            if (docWriter != null) {
                docWriter.close();
            }
        }
    }

    private void generateLayout(Document doc, PdfContentByte cb) {

        try {

            cb.setLineWidth(1f);

            // Invoice Header box layout
            cb.rectangle(20, 650, 550, 150);
            // cb.moveTo(320, 720);
            // cb.lineTo(380, 720);
            cb.moveTo(297, 800);
            cb.lineTo(297, 650);
            // cb.moveTo(380, 700);
            // cb.lineTo(380, 760);
            cb.stroke();
            float x = 322, y = 765;
            // Invoice Header box Text Headings
            createHeadings(cb, x + 50, y + 15, "Invoice Details.", 12, true);
            createHeadings(cb, x, y, "Invoice No.");
            createHeadings(cb, x, y - 15, "Date");
            createHeadings(cb, x, y - 30, "Report No.");
            createHeadings(cb, x, y - 45, "Report Date.");
            createHeadings(cb, x, y - 60, "Asset No.");
            createHeadings(cb, x, y - 75, "Asset Name.");


            // Invoice Detail box layout
            cb.rectangle(20, 50, 550, 600);
            cb.moveTo(20, 630);
            cb.lineTo(570, 630);
            cb.moveTo(50, 50);
            cb.lineTo(50, 650);
            cb.moveTo(200, 50);
            cb.lineTo(200, 630);
            cb.moveTo(500, 50);
            cb.lineTo(500, 650);
            cb.stroke();

            // Invoice Detail box Text Headings
            createHeadings(cb, 22, 633, "Sl.No");
            createHeadings(cb, 252, 633, "Perticular");
            createHeadings(cb, 502, 633, "Amount");

            // add the images
            // Image companyLogo = Image.getInstance("images/olympics_logo.gif");
            // companyLogo.setAbsolutePosition(25, 700);
            // companyLogo.scalePercent(25);
            // doc.add(companyLogo);

        }

        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void generateHeader(Document doc, PdfContentByte cb) {

        try {
            float x = 50, y = 780;
            createHeadings(cb, x + 50, y, "Company Details", 12, true);
            y = decrement(y);
            createHeadings(cb, x, y, "Company Name");
            y = decrement(y);
            createHeadings(cb, x, y, "Address Line 1");
            y = decrement(y);
            createHeadings(cb, x, y, "Address Line 2");
            y = decrement(y);
            createHeadings(cb, x, y, "City, State - ZipCode");
            y = decrement(y);
            createHeadings(cb, x, y, "Country");
            y = decrement(y);

            x = 400;
            y = 765;
            // Invoice Header box Text Headings
            createHeadings(cb, x, y, "435754");
            y = decrement(y);
            createHeadings(cb, x, y, "12/12/2016");
            y = decrement(y);
            createHeadings(cb, x, y, "Report No.-123");
            y = decrement(y);
            createHeadings(cb, x, y, "12/12/2016");
            y = decrement(y);
            createHeadings(cb, x, y, "Asset No-234");
            y = decrement(y);
            createHeadings(cb, x, y, "Welding machine");

        }

        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private float decrement(float v) {
        return v - 15;
    }

    private void generateDetail(Document doc, PdfContentByte cb, float x, float y) {
        DecimalFormat df = new DecimalFormat("0.00");
        int index = 1;
        try {

            createContent(cb, x, y, String.valueOf(index++), PdfContentByte.ALIGN_CENTER);
            createContent(cb, x + 80, y, "Service", PdfContentByte.ALIGN_LEFT);
            createContent(cb, x + 200, y, "Service Charges", PdfContentByte.ALIGN_LEFT);
            double price = Double.valueOf(df.format(Math.random() * 10));
            double extPrice = price * (index);
            createContent(cb, 568, y, df.format(extPrice), PdfContentByte.ALIGN_RIGHT);

        }

        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void generateDetail(Document doc, PdfContentByte cb, float x, float y,
            Map<String, Map<String, String>> listParticluarsMap, Double grandTotal) {
        DecimalFormat df = new DecimalFormat("0.00");
        int index = 1;
        for (String mapVal : listParticluarsMap.keySet()) {
            Map<String, String> map = listParticluarsMap.get(mapVal);            
            createContent(cb, x + 80, y, mapVal, PdfContentByte.ALIGN_LEFT);
            for (String key : map.keySet()) {
                try {
                    createContent(cb, x, y, String.valueOf(index++), PdfContentByte.ALIGN_CENTER);
                    createContent(cb, x + 200, y, key, PdfContentByte.ALIGN_LEFT);
                    try {
                        String price = df.format(Double.valueOf(map.get(key)));
                        createContent(cb, 568, y, price, PdfContentByte.ALIGN_RIGHT);
                    } catch (Exception ex) {
                        createContent(cb, 568, y, map.get(key), PdfContentByte.ALIGN_RIGHT);
                    }
                    y = decrement(y);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            cb.moveTo(20, y);
            cb.lineTo(570, y);
            cb.stroke();
            y = decrement(y);
          
        }
        createContent(cb, x + 200, y, "Grand Total", PdfContentByte.ALIGN_LEFT);
        String price = df.format(grandTotal);
        createContent(cb, 568, y, price, PdfContentByte.ALIGN_RIGHT);

    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setColorFill(BaseColor.BLACK);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text, int size,
            boolean isHeading) {
        cb.beginText();
        cb.setFontAndSize(bfBold, size);
        if (isHeading) {
            BaseColor color = new BaseColor(0, 64, 128);
            cb.setColorFill(color);
        } else {
            cb.setColorFill(BaseColor.BLACK);
        }
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    private void printPageNumber(PdfContentByte cb) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Page No. " + (pageNumber + 1), 570, 25, 0);
        cb.endText();
        pageNumber++;

    }

    private void createContent(PdfContentByte cb, float x, float y, String text, int align) {
        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.showTextAligned(align, text.trim(), x, y, 0);
        cb.endText();

    }

    private void initializeFonts() {
        try {
            bfBold =
                BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}