package com.example.practice.service

import com.example.practice.domain.type.SearchType
import com.example.practice.dto.ArticleDto
import com.example.practice.dto.ArticleWithCommentsDto
import com.example.practice.repository.ArticleRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ArticleService(private val articleRepository: ArticleRepository) {
    @Transactional(readOnly = true)
    internal fun searchArticles(type: SearchType, searchKeyword: String, pageable: Pageable): Page<ArticleDto> {
        return Page.empty()
    }

    @Transactional(readOnly = true)
    internal fun getArticle(articleId: Long): ArticleWithCommentsDto? {
        return null
    }
}