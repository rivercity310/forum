package com.example.forum.dto;

import com.example.forum.domain.Article;
import com.example.forum.domain.ArticleComment;

import java.time.LocalDateTime;

public record ArticleCommentDto(
        Long id,
        Long articleId,
        UserAccountDto userAccountDto,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    // 기본 Factory Method 생성
    public static ArticleCommentDto of(
            Long id,
            Long articleId,
            UserAccountDto userAccountDto,
            String content,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy
    ) {
        return new ArticleCommentDto(
                id,
                articleId,
                userAccountDto,
                content,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }

    // Entity To Dto
    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    // Article to ArticleComment
    public ArticleComment toEntity(Article entity) {
        return ArticleComment.of(
                entity,
                userAccountDto.toEntity(),
                content
        );
    }
}
