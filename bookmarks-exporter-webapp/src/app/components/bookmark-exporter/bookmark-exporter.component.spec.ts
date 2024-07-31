import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookmarkExporterComponent } from './bookmark-exporter.component';

describe('BookmarkExporterComponent', () => {
  let component: BookmarkExporterComponent;
  let fixture: ComponentFixture<BookmarkExporterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookmarkExporterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookmarkExporterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
