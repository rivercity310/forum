package com.example.practice.controller

import com.example.practice.dto.ArticleDto
import com.example.practice.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/articles")
@RestController
class ArticleController(private val articleService: ArticleService) {
    @GetMapping
    internal fun articles() : ArticleDto? {
        return null
    }

    @GetMapping("/{articleId}")
    internal fun article(@PathVariable articleId : Long) : ArticleDto? {
        return null
    }
}