package com.example.practice.dto.response

import com.example.practice.dto.ArticleDto
import java.time.LocalDateTime

data class ArticleResponse(
    val id : Long,
    val title : String,
    val content : String,
    val hashtag : String,
    val createdAt : LocalDateTime,
    val email : String,
    val nickname : String
) {
    companion object {
        internal fun from(dto : ArticleDto) : ArticleResponse {
            var nickname : String = dto.userAccountDto.nickname
            if (nickname.isBlank()) nickname = dto.userAccountDto.userId

            return ArticleResponse(
                id = dto.id,
                title = dto.title,
                content = dto.content,
                hashtag = dto.hashtag,
                createdAt = dto.createdAt,
                email = dto.userAccountDto.email,
                nickname = nickname
            )
        }
    }
}