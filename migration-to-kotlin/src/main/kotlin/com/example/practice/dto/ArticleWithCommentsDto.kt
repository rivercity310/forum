package com.example.practice.dto

import com.example.practice.domain.Article
import java.time.LocalDateTime

data class ArticleWithCommentsDto(
    val id : Long,
    val userAccountDto: UserAccountDto,
    val articleCommentDtos : Set<ArticleCommentDto> = mutableSetOf(),
    val title : String,
    val content : String,
    val hashtag : String,
    val createdAt : LocalDateTime,
    val createdBy : String,
    val modifiedAt : LocalDateTime,
    val modifiedBy : String
) {
    companion object {
        internal fun from(entity : Article) : ArticleWithCommentsDto {
            return ArticleWithCommentsDto(
                id = entity.id,
                userAccountDto = UserAccountDto.from(entity.userAccount),
                articleCommentDtos = entity.articleComments.map(ArticleCommentDto::from).toSet(),
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
}