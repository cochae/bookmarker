package com.min.edu.service;

import com.min.edu.domain.Bookmark;
import com.min.edu.dto.BookmarkDto;
import com.min.edu.dto.BookmarksDto;
import com.min.edu.dto.CreateBookmarkRequest;
import com.min.edu.mapper.BookmarkMapper;
import com.min.edu.repository.BookmarkRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.print.Book;
import java.time.Instant;


@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository repository;
    private final BookmarkMapper mapper;
    @Transactional(readOnly = true)
    public BookmarksDto getBookmark(Integer page) {
        int pageNo = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");

//        return new BookmarksDto(repository.findAll(pageable));

//        Page<BookmarkDto> bookmarkDtoPage =
//                repository.findAll(pageable).map(mapper::toDto);
//        return new BookmarksDto(bookmarkDtoPage);

        Page<BookmarkDto> bookmarkPage = repository.findByBookmarks(pageable);
        return new BookmarksDto(bookmarkPage);
    }

    @Transactional(readOnly = true)
    public BookmarksDto searchBookmarks(String query, Integer page){
        int pageNo = page < 1 ? 0: page - 1;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
        Page<BookmarkDto> bookmarkPage = repository.searchByBookmarks(query, pageable);
        return new BookmarksDto(bookmarkPage);
    }
    public BookmarkDto createBookmark(CreateBookmarkRequest request){
        Bookmark bookmark = new Bookmark(null, request.getTitle(), request.getUrl(), Instant.now());
        Bookmark savedBookmark = repository.save(bookmark);
        return mapper.toDto(savedBookmark);
    }
}
