import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Bookmark } from '../common/Bookmark';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookmarkService {

  private baseUrl = 'http://localhost:9000/api/bookmarks';

  constructor(private httpClient: HttpClient) { }

  exportBookmarksToCSV(bookmarks: Bookmark[]): Observable<Blob> {

    return this.httpClient.post(`${this.baseUrl}/export/csv`, bookmarks, { responseType: 'blob' });

  }

  exportBookmarksToPDF(bookmarks: Bookmark[]): Observable<Blob> {

    return this.httpClient.post(`${this.baseUrl}/export/pdf`, bookmarks, { responseType: 'blob' });

  }

  exportBookmarksToDOCX(bookmarks: Bookmark[]): Observable<Blob> {

    return this.httpClient.post(`${this.baseUrl}/export/docx`, bookmarks, { responseType: 'blob' });

  }
}
