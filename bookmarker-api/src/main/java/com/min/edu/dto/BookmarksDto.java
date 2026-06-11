package com.min.edu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@ToString
public class BookmarksDto {
    private List<BookmarkDto> data;
    private long totalElements; //  전체 데이터 수
    private int totalPages; 	// 전체 페이지 수
    private int currentPage; 	// 현재 페이지

    // JSON의 결과의 값이 다름 isFirst=>first
    @JsonProperty(value = "isFirst")
    private boolean isFirst;
    @JsonProperty(value = "isLast")
    private boolean isLast;

    private boolean hasNext;
    private boolean hasPrevious;

    public BookmarksDto(Page<BookmarkDto> bookmarkPage){
        this.setData(bookmarkPage.getContent());
        this.setTotalElements(bookmarkPage.getTotalElements());
        this.setTotalPages(bookmarkPage.getTotalPages());
        this.setCurrentPage(bookmarkPage.getNumber()+1);
        this.setFirst(bookmarkPage.isFirst());
        this.setLast(bookmarkPage.isLast());
        this.setHasNext(bookmarkPage.hasNext());
        this.setHasPrevious(bookmarkPage.hasPrevious());
    }

}
