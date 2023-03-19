package com.example.practice.dto

import com.example.practice.domain.Article
import java.time.LocalDateTime

data class ArticleDto(
    val id : Long,
    val userAccountDto : UserAccountDto,
    val title : String,
    val content : String,
    val hashtag : String,
    val createdAt : LocalDateTime,
    val createdBy : String,
    val modifiedAt : LocalDateTime,
    val modifiedBy : String
) {
    companion object {
        internal fun from(entity: Article) : ArticleDto {
            return ArticleDto(
                id = entity.id,
                userAccountDto = UserAccountDto.from(entity.userAccount),
                title = entity.title,
                content = entity.content,
                hashtag = entity.hashtag,
                createdAt = entity.createdAt!!,
                createdBy = entity.createdBy!!,
                modifiedAt = entity.modifiedAt!!,
                modifiedBy = entity.modifiedBy!!
            )
        }
    }

    internal fun toEntity() : Article {
        return Article(
            userAccount = userAccountDto.toEntity(),
            title = title,
            content = content,
            hashtag = hashtag
        )
    }
}