package com.debarshi.bookmarks_exporter.service;

import com.debarshi.bookmarks_exporter.model.Bookmark;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
public class BookmarksExporterServiceImpl implements BookmarksExporterService {

    public byte[] generateCSV(List<Bookmark> bookmarkList) {
        log.info("Starting CSV generation for {} bookmarks", bookmarkList.size());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ID", "Title", "URL"))) {

            for (Bookmark bookmark : bookmarkList) {

                log.debug("Writing bookmark to CSV: {}", bookmark);

                csvPrinter.printRecord(bookmark.getId(), bookmark.getTitle(), bookmark.getUrl());
            }

            log.info("CSV generation completed successfully");

        } catch (Exception e) {

            log.error("Error occurred while generating CSV", e);

        }
        return outputStream.toByteArray();
    }

    public byte[] generatePDF(List<Bookmark> bookmarkList) {

        log.info("Starting PDF generation for {} bookmarks", bookmarkList.size());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            log.debug("Created new PDF document and added a page");

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 725);

            log.debug("Initialized PDF content stream");

            for (Bookmark bookmark : bookmarkList) {
                if (bookmark.getTitle() != null) {
                    log.debug("Adding bookmark title: {}", bookmark.getTitle());
                    contentStream.showText(bookmark.getTitle());
                    contentStream.newLine();
                }
                if (bookmark.getUrl() != null) {
                    log.debug("Adding bookmark URL: {}", bookmark.getUrl());
                    contentStream.showText(bookmark.getUrl());
                    contentStream.newLine();
                }
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            document.save(byteArrayOutputStream);
            document.close();

            log.info("PDF generation completed successfully");

        } catch (IOException e) {
            log.error("Error occurred while generating PDF", e);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generateDOCX(List<Bookmark> bookmarkList)
    {
        log.info("Starting DOCX generation for {} bookmarks", bookmarkList.size());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("Bookmarks:");
            run.addCarriageReturn();

            for (Bookmark bookmark : bookmarkList) {
                run.addCarriageReturn();
                run.setText(bookmark.getTitle() + ": ");
                run.addCarriageReturn();
                run.setText(bookmark.getUrl());
                log.debug("Added bookmark - Title: {}, URL: {}", bookmark.getTitle(), bookmark.getUrl());
                run.addCarriageReturn();
            }

            document.write(byteArrayOutputStream);
            document.close();
            log.info("DOCX generation completed successfully");
        } catch (IOException e) {
            log.error("Error occurred while generating DOCX", e);
        }

        return byteArrayOutputStream.toByteArray();
    }
}
