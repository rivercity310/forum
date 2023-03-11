package com.example.forum.service;

import com.example.forum.domain.type.SearchType;
import com.example.forum.dto.ArticleDto;
import com.example.forum.dto.ArticleUpdateDto;
import com.example.forum.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType type, String searchKeyword) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(Long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto articleDto) {

    }

    public void updateArticle(Long articleId, ArticleUpdateDto articleUpdateDto) {

    }

    public void deleteArticle(Long articleId) {

    }
}
