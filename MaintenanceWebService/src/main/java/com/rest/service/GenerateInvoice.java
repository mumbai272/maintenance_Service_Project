//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.maintenance.common.util.DateUtil;
import com.rest.entity.Address;
import com.rest.entity.AssetReport;
import com.rest.entity.AssetReportCharges;




public class GenerateInvoice extends BaseServiceImpl {
    private static Logger logger = Logger.getLogger(GenerateInvoice.class);

    private static BaseFont bfBold;

    private static BaseFont bf;



    public static String createPDF(String author, String pdfFilename, AssetReportCharges charges,
            AssetReport report, String company, Address address) {

        Document doc = new Document();
        PdfWriter docWriter = null;
        initializeFonts();
        File file = new File(pdfFilename);
//        FileUtils.mkDir(file.getParentFile()
        if(!file.getParentFile().exists() || !file.getParentFile().isDirectory()){
            logger.info("createing folder");
           file.getParentFile().mkdirs();
        }
        if(!file.exists()){
        	logger.info("creting invoice file");
            file=new File(pdfFilename);           
        }else{
        	 logger.info("file not exist");
        }
        try {
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.addAuthor(author);
            doc.addCreationDate();
            doc.addCreator(author);
            doc.addTitle("Invoice");
            doc.setPageSize(PageSize.A4);

            doc.open();
            PdfContentByte cb = docWriter.getDirectContent();


            generateLayout(doc, cb);
            generateHeader(doc, cb, report, company, address, charges);
            Map<String, Map<String, String>> map = new LinkedHashMap<String, Map<String, String>>();
            Map<String, String> map1 = new LinkedHashMap<String, String>();
            map1.put("Service Charges", (charges.getServiceCharges()==null)?"00.00":charges.getServiceCharges().toString());
            map1.put("After office Hour Charges", (charges.getOffHourCharges()==null)?"00.00":charges.getOffHourCharges().toString());
            map1.put("Holiday Charges", (charges.getHoidayCharges()==null)?"00.00":charges.getHoidayCharges().toString());
            map1.put("To and Fro Charges", (charges.getToFroCharges()==null)?"00.00":charges.getToFroCharges().toString());

            map1.put("Total Charge", (charges.getTotalCharges()==null)?"00.00":charges.getTotalCharges().toString());
            map1.put("Tax Type", StringUtils.isBlank(charges.getTaxType())?"---":charges.getTaxType());
            map1.put("Tax %", charges.getTaxPercentage()==null?"00.00":charges.getTaxPercentage().toString().concat("%"));
            map1.put("Tax Amount", charges.getTaxAmount()==null?"00.00":charges.getTaxAmount().toString());

            Map<String, String> map2 = new LinkedHashMap<String, String>();
            map2.put("Spare Amount",charges.getSpareAmount()==null?"00.00":charges.getSpareAmount().toString());
            map2.put("Spare Tax Type", StringUtils.isBlank(charges.getSpareTaxType())?"---":charges.getSpareTaxType());
            map2.put("Spare Tax %", charges.getSpareTaxPercentage()==null?"00.00":charges.getSpareTaxPercentage().toString().concat("%"));
            map2.put("Spare Tax Amount", charges.getSpareTaxAmount()==null?"00.00":charges.getSpareTaxAmount().toString());
            map.put("Service", map1);
            map.put("Spare charges", map2);

            generateDetail(doc, cb, 30, decrement(635), map, charges.getGrandTotal());

            return pdfFilename;

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
        return null;
    }

    private static void generateLayout(Document doc, PdfContentByte cb) {

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
            // createHeadings(cb, x, y - 75, "Asset Name.");


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
        }

        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void generateHeader(Document doc, PdfContentByte cb, AssetReport report,
            String company, Address address, AssetReportCharges charges) {

        try {
            float x = 50, y = 780;
            createHeadings(cb, x + 50, y, "Company Details", 12, true);
            y = decrement(y);
            createHeadings(cb, x, y, company);
            y = decrement(y);
            createHeadings(cb, x, y, address.getStreet1());
            y = decrement(y);
            createHeadings(cb, x, y, address.getStreet2() + "," + address.getStreet3());
            y = decrement(y);
            createHeadings(cb, x, y, address.getCity() + "-" + address.getZipCode());
            y = decrement(y);
            createHeadings(cb, x, y, address.getCountry());
            y = decrement(y);

            x = 400;
            y = 765;
            // Invoice Header box Text Headings
            createHeadings(cb, x, y, charges.getInvoiceNo());
            y = decrement(y);
            createHeadings(cb, x, y, DateUtil.formate(charges.getInvoiceDate().getTime(), null));
            y = decrement(y);
            createHeadings(cb, x, y,
                StringUtils.isNotBlank(report.getReportNo()) ? report.getReportNo() : report
                        .getReportId().toString());
            y = decrement(y);
            createHeadings(cb, x, y, DateUtil.formate(report.getReportedDateTime().getTime(), null));
            y = decrement(y);
            createHeadings(cb, x, y, report.getAssetLog().getAssetId().toString());
            y = decrement(y);
            // createHeadings(cb, x, y, report.getAssetLog().get);

        }

        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static float decrement(float v) {
        return v - 15;
    }

    private static void generateDetail(Document doc, PdfContentByte cb, float x, float y,
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

    private static void createHeadings(PdfContentByte cb, float x, float y, String text) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setColorFill(BaseColor.BLACK);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    private static void createHeadings(PdfContentByte cb, float x, float y, String text, int size,
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

    private static void createContent(PdfContentByte cb, float x, float y, String text, int align) {
        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.showTextAligned(align, text.trim(), x, y, 0);
        cb.endText();

    }

    private static void initializeFonts() {
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