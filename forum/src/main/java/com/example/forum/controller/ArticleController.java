package com.example.forum.controller;

import com.example.forum.domain.type.SearchType;
import com.example.forum.dto.response.ArticleResponse;
import com.example.forum.dto.response.ArticleWithCommentsResponse;
import com.example.forum.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "articles")
@Controller
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchKeyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        map.addAttribute("articles", articleService.searchArticles(searchType, searchKeyword, pageable).map(ArticleResponse::from));
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        ArticleWithCommentsResponse res = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", res);
        map.addAttribute("articleComments", res.articleCommentsResponse());
        return "articles/detail";
    }
}
