package com.example.practice.controller

import com.example.practice.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = ["articles"])
@RestController
class ArticleController(private val articleService: ArticleService) {
    @GetMapping
    internal fun articles() : String {
        return "1"
    }

    @GetMapping("/{articleId}")
    internal fun article(@PathVariable articleId : Long) : String {
        return "1"
    }
}