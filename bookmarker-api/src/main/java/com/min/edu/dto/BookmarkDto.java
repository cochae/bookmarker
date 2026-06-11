package com.min.edu.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {
    private Long id;
    private String title;
    private String url;
    private Instant createdAt;
}
