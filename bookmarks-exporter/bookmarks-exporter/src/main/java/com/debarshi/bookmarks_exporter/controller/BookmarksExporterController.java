package com.debarshi.bookmarks_exporter.controller;

import com.debarshi.bookmarks_exporter.model.Bookmark;
import com.debarshi.bookmarks_exporter.service.BookmarksExporterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@CrossOrigin
@Slf4j
public class BookmarksExporterController {

    @Autowired
    private BookmarksExporterService bookmarksExporterService;

    @PostMapping("/export/csv")
    public ResponseEntity<byte[]> exportToCSV(@RequestBody List<Bookmark> bookmarkList)
    {

        log.info("Received request to export {} bookmarkList to CSV", bookmarkList.size());

        byte[] csvData;
        try {
            csvData = bookmarksExporterService.generateCSV(bookmarkList);
            log.info("Successfully generated CSV data");
        } catch (Exception e) {
            log.error("Error generating CSV data", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bookmarkList.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");

        log.info("Returning CSV file with {} bytes", csvData.length);
        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }

    @PostMapping("/export/pdf")
    public ResponseEntity<byte[]> exportToPDF(@RequestBody List<Bookmark> bookmarkList)
    {
        log.info("Received request to export {} bookmarkList to PDF", bookmarkList.size());

        byte[] pdfData;
        try {
            pdfData = bookmarksExporterService.generatePDF(bookmarkList);
            log.info("Successfully generated PDF data");
        } catch (Exception e) {
            log.error("Error generating PDF data", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "bookmarks.pdf");

        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
    }

    @PostMapping("/export/docx")
    public ResponseEntity<byte[]> exportToDOCX(@RequestBody List<Bookmark> bookmarkList)
    {
        log.info("Received request to export {} bookmarkList to DOCX", bookmarkList.size());

        byte[] docxData;
        try {
            docxData = bookmarksExporterService.generateDOCX(bookmarkList);
            log.info("Successfully generated DOCX data");
        } catch (Exception e) {
            log.error("Error generating DOCX data", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=bookmarks.docx");

        return new ResponseEntity<>(docxData, headers, HttpStatus.OK);
    }

}
