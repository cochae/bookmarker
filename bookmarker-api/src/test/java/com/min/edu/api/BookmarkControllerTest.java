package com.min.edu.api;

import com.min.edu.domain.Bookmark;
import com.min.edu.repository.BookmarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.hamcrest.CoreMatchers;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        //"spring.datasource.url=jdbc:tc:postgresql:14-alpine;///demo"
        "spring.datasource.url=jdbc:tc:postgresql:14-alpine:///demo"
})
public class BookmarkControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @BeforeEach
    void setUp() {
        bookmarkRepository.deleteAllInBatch();

        List<Bookmark> bookmarks = List.of(
                new Bookmark(null, "OpenAI", "https://openai.com", Instant.now()),
                new Bookmark(null, "Notion", "https://www.notion.so", Instant.now()),
                new Bookmark(null, "Figma", "https://www.figma.com", Instant.now()),
                new Bookmark(null, "Slack", "https://slack.com", Instant.now()),
                new Bookmark(null, "Discord", "https://discord.com", Instant.now()),
                new Bookmark(null, "Spotify", "https://www.spotify.com", Instant.now()),
                new Bookmark(null, "Netflix", "https://www.netflix.com", Instant.now()),
                new Bookmark(null, "Airbnb", "https://www.airbnb.com", Instant.now()),
                new Bookmark(null, "LinkedIn", "https://www.linkedin.com", Instant.now()),
                new Bookmark(null, "Kakao", "https://www.kakao.com", Instant.now()),
                new Bookmark(null, "Coupang", "https://www.coupang.com", Instant.now()),
                new Bookmark(null, "Toss", "https://toss.im", Instant.now()),
                new Bookmark(null, "Naver", "https://www.naver.com", Instant.now()),
                new Bookmark(null, "Baemin", "https://www.baemin.com", Instant.now()),
                new Bookmark(null, "Samsung", "https://www.samsung.com", Instant.now())
        );

        bookmarkRepository.saveAll(bookmarks);
    }

    @Test
    void shouldBookmarks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookmarks"))
                .andExpect(status().isOk())
                //  .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(0)));
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(15)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(2)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(true)))
                .andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(false)))
                .andExpect(jsonPath("$.hasNext", CoreMatchers.equalTo(true)))
                .andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(false)));
    }

    @ParameterizedTest
    @CsvSource({"1, 15, 2, 1, true, false, true, false", "2, 15, 2, 2, false, true, false, true"})
    void shouldBookmarksPage(
            int pageNo, int totalElements, int totalPages, int currentPage, boolean isFirst, boolean isLast, boolean hasNext, boolean hasPrevious
    ) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookmarks?page="+pageNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(totalElements)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(totalPages)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(currentPage)))
                .andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(isFirst)))
                .andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(isLast)))
                .andExpect(jsonPath("$.hasNext", CoreMatchers.equalTo(hasNext)))
                .andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(hasPrevious)));
    }
    @Test
    public void shouldCreateBookmark() throws Exception{
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "title":"Link Lion Study"
                                }
                                """)
        )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.field", is("url")))
                .andExpect(jsonPath("$.message", is("URL은 필수입니다.")))
                .andReturn();
        
        String contentType = result.getResponse().getContentType();
        System.out.println("contentType = " + contentType);
        
        String responseBody = result.getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
    }
}
