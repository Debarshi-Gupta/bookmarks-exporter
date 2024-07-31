package com.debarshi.bookmarks_exporter.service;

import com.debarshi.bookmarks_exporter.model.Bookmark;

import java.util.List;

public interface BookmarksExporterService {

    byte[] generateCSV(List<Bookmark> bookmarkList);

    byte[] generatePDF(List<Bookmark> bookmarkList);

    byte[] generateDOCX(List<Bookmark> bookmarkList);

}
