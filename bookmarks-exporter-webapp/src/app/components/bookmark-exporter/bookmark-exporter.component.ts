import { Component } from '@angular/core';
import { Bookmark } from 'src/app/common/Bookmark';
import { BookmarkService } from 'src/app/services/bookmark.service';

@Component({
  selector: 'app-bookmark-exporter',
  templateUrl: './bookmark-exporter.component.html',
  styleUrls: ['./bookmark-exporter.component.css']
})
export class BookmarkExporterComponent {

  bookmarks: Bookmark[] = [];

  constructor(private bookmarkService: BookmarkService) { }

  ngOnInit() {
    chrome.bookmarks.getTree((bookmarkTreeNodes: chrome.bookmarks.BookmarkTreeNode[]) => {
      this.bookmarks = this.flattenBookmarks(bookmarkTreeNodes);
    });
  }

  flattenBookmarks(bookmarkNodes: chrome.bookmarks.BookmarkTreeNode[]): Bookmark[] {
    let bookmarks: Bookmark[] = [];
    for (let node of bookmarkNodes) {
      if (node.url) {
        bookmarks.push({ id: node.id, title: node.title, url: node.url });
      }
      if (node.children) {
        bookmarks = bookmarks.concat(this.flattenBookmarks(node.children));
      }
    }
    return bookmarks;
  }

  exportToCSV() {
    this.bookmarkService.exportBookmarksToCSV(this.bookmarks).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'bookmarks.csv';
      a.click();
    });
    console.log(this.bookmarks);
  }

  exportToPDF() {
    this.bookmarkService.exportBookmarksToPDF(this.bookmarks).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'bookmarks.pdf';
      a.click();
    });
    console.log(this.bookmarks);
  }

  exportToDOCX() {
    this.bookmarkService.exportBookmarksToDOCX(this.bookmarks).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'bookmarks.docx';
      a.click();
    });
    console.log(this.bookmarks);
  }

}
