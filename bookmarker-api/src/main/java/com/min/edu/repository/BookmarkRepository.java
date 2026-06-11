package com.min.edu.repository;

import com.min.edu.domain.Bookmark;
import com.min.edu.dto.BookmarkDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Query("""
            select new com.min.edu.dto.BookmarkDto(b.id, b.title, b.url, b.createdAt) from Bookmark b
            """)
    Page<BookmarkDto> findByBookmarks(Pageable pageable);

    @Query("""
                select new com.min.edu.dto.BookmarkDto(
                    b.id,
                    b.title,
                    b.url,
                    b.createdAt
                )
                from Bookmark b
                where b.title like concat('%', :query, '%')
            """)
    Page<BookmarkDto> searchByBookmarks(String query, Pageable pageable);
}
