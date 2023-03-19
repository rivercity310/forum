package com.example.practice.dto

import com.example.practice.domain.Article
import com.example.practice.domain.ArticleComment
import java.time.LocalDateTime

data class ArticleCommentDto(
    val id : Long,
    val articleId : Long,
    val userAccountDto: UserAccountDto,
    val content : String,
    val createdAt : LocalDateTime,
    val createdBy : String,
    val modifiedAt : LocalDateTime,
    val modifiedBy : String
) {
    companion object {
        internal fun from(entity : ArticleComment) : ArticleCommentDto {
            return ArticleCommentDto(
                id = entity.id,
                articleId = entity.article.id,
                userAccountDto = UserAccountDto.from(entity.userAccount),
                content = entity.content,
                createdAt = entity.createdAt!!,
                createdBy = entity.createdBy!!,
                modifiedAt = entity.modifiedAt!!,
                modifiedBy = entity.modifiedBy!!
            )
        }
    }

    internal fun toEntity(entity : Article) : ArticleComment {
        return ArticleComment(
            article = entity,
            userAccount = userAccountDto.toEntity(),
            content = content
        )
    }
}