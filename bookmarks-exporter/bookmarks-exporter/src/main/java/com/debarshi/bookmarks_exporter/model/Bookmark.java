package com.debarshi.bookmarks_exporter.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Bookmark {

    private String id;

    private String title;

    private String url;

}
