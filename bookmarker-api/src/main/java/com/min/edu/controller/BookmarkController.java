package com.min.edu.controller;

import com.min.edu.dto.BookmarkDto;
import com.min.edu.dto.BookmarksDto;
import com.min.edu.dto.CreateBookmarkRequest;
import com.min.edu.service.BookmarkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService service;
    @GetMapping
    public BookmarksDto getBookmark(@RequestParam(name="page", defaultValue = "1") Integer page,
                                    @RequestParam(name="query", defaultValue = "") String query){
        if(query == null || query.trim().isEmpty()){
            return service.getBookmark(page);
        }
        return service.searchBookmarks(query, page);
    }
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookmarkDto createBookmark(@RequestBody @Valid CreateBookmarkRequest createBookmarkRequest){
        return service.createBookmark(createBookmarkRequest);
    }
}
