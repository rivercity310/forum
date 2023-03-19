package com.example.practice.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional

@DisplayName("Data Rest - API Test")
@Transactional
@WithMockUser
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class DataRestTest @Autowired constructor(private val mvc : MockMvc) {
    @DisplayName("게시글 리스트 조회")
    @Test
    internal fun givenNothing_whenRequestingArticles_thenReturnsArticlesJSONResponse() {
        mvc.perform(get("/api/articles"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
            .andDo(print())
    }

    @DisplayName("게시글 단건 조회")
    @Test
    internal fun givenNothing_whenRequestingArticles_thenReturnsArticlesOnlyOne() {
        mvc.perform(get("/api/articles/1"))
            .andExpect(status().is4xxClientError)
            //.andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
    }

    @DisplayName("게시글 -> 댓글 리스트 조회")
    @Test
    internal fun givenNothing_whenRequestingArticleCommentsFromArticle_thenReturnsArticleCommentsJsonResponse() {
        mvc.perform(get("/api/articles/3/articleComments"))
            .andExpect(status().is4xxClientError)
    }

    @DisplayName("댓글 리스트 조회")
    @Test
    internal fun givenNothing_whenRequestingArticleComments_thenReturnsArticleCommentsJsonResonse() {
        mvc.perform(get("/api/articleComments"))
            .andExpect(status().is4xxClientError)
    }

    @DisplayName("댓글 단건 조회")
    @Test
    internal fun givenNothing_whenRequestingArticleComment_thenReturnsArticleCommentJsonResponse() {
        mvc.perform(get("/api/articleComments/1"))
            .andExpect(status().is4xxClientError)
    }
}