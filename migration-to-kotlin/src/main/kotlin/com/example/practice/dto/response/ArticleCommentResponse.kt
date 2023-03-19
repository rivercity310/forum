package com.example.practice.dto.response

import com.example.practice.dto.ArticleCommentDto
import java.time.LocalDateTime

data class ArticleCommentResponse(
    val id : Long,
    val content : String,
    val createdAt : LocalDateTime,
    val email : String,
    val nickname : String
) {
    companion object {
        internal fun from(dto : ArticleCommentDto) : ArticleCommentResponse {
            var nickname : String = dto.userAccountDto.nickname
            if (nickname.isBlank()) nickname = dto.userAccountDto.userId

            return ArticleCommentResponse(
                id = dto.id,
                content = dto.content,
                createdAt = dto.createdAt,
                email = dto.userAccountDto.email,
                nickname = nickname
            )
        }
    }
}