package com.example.practice.dto.response

import com.example.practice.dto.ArticleWithCommentsDto
import java.time.LocalDateTime

data class ArticleWithCommentResponse(
    val id : Long,
    val title : String,
    val content : String,
    val hashtag : String,
    val createdAt : LocalDateTime,
    val email : String,
    val nickname : String,
    val articleCommentResponses : Set<ArticleCommentResponse> = mutableSetOf()
) {
    companion object {
        internal fun from(dto : ArticleWithCommentsDto) : ArticleWithCommentResponse {
            var nickname : String = dto.userAccountDto.nickname
            if (nickname.isBlank()) nickname = dto.userAccountDto.userId

            return ArticleWithCommentResponse(
                id = dto.id,
                title = dto.title,
                content = dto.content,
                hashtag = dto.hashtag,
                createdAt = dto.createdAt,
                email = dto.userAccountDto.email,
                nickname = nickname,
                articleCommentResponses = dto.articleCommentDtos.map(ArticleCommentResponse::from).toSet()
            )
        }
    }
}