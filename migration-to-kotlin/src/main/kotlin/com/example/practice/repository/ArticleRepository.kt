package com.example.practice.repository

import com.example.practice.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
}